package app.roque.moviesfeed.root;

import javax.inject.Singleton;

import app.roque.moviesfeed.MainActivity;
import app.roque.moviesfeed.http.MovieTitleApiModule;
import app.roque.moviesfeed.http.MoviesExtraInfoApiModule;
import app.roque.moviesfeed.movies.MoviesModule;
import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, MoviesModule.class ,MovieTitleApiModule.class, MoviesExtraInfoApiModule.class})
public interface ApplicationComponent {
    void inject(MainActivity target);
}
