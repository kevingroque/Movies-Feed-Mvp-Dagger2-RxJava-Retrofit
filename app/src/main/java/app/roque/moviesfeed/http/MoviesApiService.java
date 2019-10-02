package app.roque.moviesfeed.http;

import app.roque.moviesfeed.http.apiModel.TopMoviesRated;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApiService {

    @GET("top_rated")
    Observable<TopMoviesRated> getTopMoviesRated(/*@Query("api_key") String api_key,*/ @Query("page") Integer page);

}
