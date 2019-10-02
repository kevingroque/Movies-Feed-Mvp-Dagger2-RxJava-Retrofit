package app.roque.moviesfeed.http;

import app.roque.moviesfeed.http.apiModel.OmdbApi;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesExtraInfoApiService {

    @GET("/")
    Observable<OmdbApi> getExtraInfoMovie(@Query("t") String title/*, @Query("apikey") String apikey*/);
}
