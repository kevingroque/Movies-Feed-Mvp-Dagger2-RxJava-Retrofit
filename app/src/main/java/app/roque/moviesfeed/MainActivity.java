package app.roque.moviesfeed;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import app.roque.moviesfeed.movies.MoviesAdapter;
import app.roque.moviesfeed.movies.MoviesMVP;
import app.roque.moviesfeed.movies.Movies;
import app.roque.moviesfeed.root.MoviesFeed;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesMVP.View {

    private final String TAG = MainActivity.class.getName();

    @BindView(R.id.ll_main_root)
    LinearLayout mRoot;
    @BindView(R.id.rv_main_movies)
    RecyclerView mMovies;

    @Inject
    MoviesMVP.Presenter presenter;

    private MoviesAdapter mAdapter;
    private List<Movies> moviesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MoviesFeed) getApplication()).getComponent().inject(this);

        mAdapter = new MoviesAdapter(moviesList);
        mMovies.setAdapter(mAdapter);
        mMovies.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mMovies.setItemAnimator(new DefaultItemAnimator());
        mMovies.setHasFixedSize(true);
        mMovies.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.loadData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.rxJavaUnsuscribe();
        moviesList.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.rxJavaUnsuscribe();
        moviesList.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateData(Movies model) {
        moviesList.add(model);
        mAdapter.notifyItemInserted(moviesList.size()-1);
        Log.d(TAG, "New Info: " + model.getTitle());
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar.make(mRoot, message, Snackbar.LENGTH_SHORT).show();
    }
}
