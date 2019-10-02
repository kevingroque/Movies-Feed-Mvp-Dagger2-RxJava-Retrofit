package app.roque.moviesfeed.movies;

import javax.inject.Singleton;

import app.roque.moviesfeed.http.MoviesApiService;
import app.roque.moviesfeed.http.MoviesExtraInfoApiService;
import dagger.Module;
import dagger.Provides;

@Module
public class MoviesModule {

    @Provides
    public MoviesMVP.Presenter providesMoviesPresenter(MoviesMVP.Model model){
        return new MoviesPresenter(model);
    }

    @Provides
    public MoviesMVP.Model providesMovieModel(Repository repository){
        return new MoviesModel(repository);
    }

    @Singleton
    @Provides
    public Repository provideMovieRepository(MoviesApiService moviesApiService, MoviesExtraInfoApiService extraInfoApiService){
        return new MoviesRepository(moviesApiService, extraInfoApiService);
    }
}
