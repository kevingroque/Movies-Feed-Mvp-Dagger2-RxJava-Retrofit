package app.roque.moviesfeed.movies;

import io.reactivex.Observable;

public interface MoviesMVP {

    interface View {
        void updateData(Movies model);
        void showSnackBar(String s);

    }

    interface Presenter {
        void loadData();
        void rxJavaUnsuscribe();
        void setView(MoviesMVP.View view);
    }

    interface Model{
        Observable<Movies> result();
    }
}
