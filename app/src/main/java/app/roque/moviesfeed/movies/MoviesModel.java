package app.roque.moviesfeed.movies;

import app.roque.moviesfeed.http.apiModel.Result;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;

public class MoviesModel implements MoviesMVP.Model {

    private Repository repository;

    public MoviesModel(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Observable<Movies> result() {
        return Observable.zip(repository.getResultData(), repository.getCountryData(), (result, s) -> new Movies(result.getTitle(), s));
    }
}
