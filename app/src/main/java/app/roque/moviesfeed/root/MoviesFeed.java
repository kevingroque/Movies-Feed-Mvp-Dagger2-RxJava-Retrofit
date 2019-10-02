package app.roque.moviesfeed.root;

import android.app.Application;

import app.roque.moviesfeed.http.MovieTitleApiModule;
import app.roque.moviesfeed.http.MoviesExtraInfoApiModule;
import app.roque.moviesfeed.movies.MoviesModule;

public class MoviesFeed extends Application{

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .moviesModule(new MoviesModule())
                .movieTitleApiModule(new MovieTitleApiModule())
                .moviesExtraInfoApiModule(new MoviesExtraInfoApiModule())
                .build();
    }

    public ApplicationComponent getComponent(){
        return component;
    }
}
