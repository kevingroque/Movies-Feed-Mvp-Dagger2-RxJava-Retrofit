package app.roque.moviesfeed.movies;

import app.roque.moviesfeed.http.apiModel.Result;
import io.reactivex.Observable;

public interface Repository {

    Observable<Result> getResultData();
    Observable<Result> getResultFromNetwork();
    Observable<Result> getResultFromCache();

    Observable<String> getCountryData();
    Observable<String> getCountryFromNetwork();
    Observable<String> getCountryFromCache();

}
