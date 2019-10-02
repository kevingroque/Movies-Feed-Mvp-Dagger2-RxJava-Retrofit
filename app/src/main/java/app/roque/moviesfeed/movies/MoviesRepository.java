package app.roque.moviesfeed.movies;

import java.util.ArrayList;
import java.util.List;

import app.roque.moviesfeed.http.MoviesApiService;
import app.roque.moviesfeed.http.MoviesExtraInfoApiService;
import app.roque.moviesfeed.http.apiModel.OmdbApi;
import app.roque.moviesfeed.http.apiModel.Result;
import app.roque.moviesfeed.http.apiModel.TopMoviesRated;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MoviesRepository implements Repository {

    private MoviesApiService moviesApiService;
    private MoviesExtraInfoApiService extraInfoApiService;

    //Caché de datos
    private List<String> countries;
    private List<Result> results;
    private long lastTimestamp;

    private static final long CAHCE_LIFETIME = 20 * 1000; //Caché con duración de 20 segundos

    public MoviesRepository(MoviesApiService moviesApiService, MoviesExtraInfoApiService extraInfoApiService) {
        this.moviesApiService = moviesApiService;
        this.extraInfoApiService = extraInfoApiService;
        this.lastTimestamp = System.currentTimeMillis();
        this.countries = new ArrayList<>();
        this.results = new ArrayList<>();
    }

    private boolean isUpdated() {
        return (System.currentTimeMillis() - lastTimestamp) < CAHCE_LIFETIME;
    }

    @Override
    public Observable<Result> getResultData() {
        return getResultFromCache().switchIfEmpty(getResultFromNetwork());
    }

    @Override
    public Observable<Result> getResultFromNetwork() {
        Observable<TopMoviesRated> topMoviesRatedObservable = moviesApiService.getTopMoviesRated(1);
                /*.concatWith(moviesApiService.getTopMoviesRated(2))
                .concatWith(moviesApiService.getTopMoviesRated(3));*/

        return topMoviesRatedObservable
                .concatMap((Function<TopMoviesRated, Observable<Result>>)
                        topMoviesRated -> Observable.fromIterable(topMoviesRated.getResults()))
                .doOnNext(result -> results.add(result));
    }

    @Override
    public Observable<Result> getResultFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(results);
        } else {
            lastTimestamp = System.currentTimeMillis();
            results.clear();
            return Observable.empty();
        }
    }

    @Override
    public Observable<String> getCountryData() {
        return getCountryFromCache().switchIfEmpty(getCountryFromNetwork());
    }

    @Override
    public Observable<String> getCountryFromNetwork() {
        return getResultFromNetwork()
                .concatMap((Function<Result, Observable<OmdbApi>>)
                        result -> extraInfoApiService.getExtraInfoMovie(result.getTitle()))
                .concatMap((Function<OmdbApi, Observable<String>>)
                        omdbApi -> {
                            if (omdbApi == null || omdbApi.getCountry() == null)
                                return Observable.just("Desconocido");
                            else
                                return Observable.just(omdbApi.getCountry());
                        })
                .doOnNext(country -> countries.add(country));
    }

    @Override
    public Observable<String> getCountryFromCache() {
        if (isUpdated()) {
            return Observable.fromIterable(countries);
        } else {
            lastTimestamp = System.currentTimeMillis();
            countries.clear();
            return Observable.empty();
        }
    }
}
