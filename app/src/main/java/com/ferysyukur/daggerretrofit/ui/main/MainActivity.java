package com.ferysyukur.daggerretrofit.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.ferysyukur.daggerretrofit.App;
import com.ferysyukur.daggerretrofit.BuildConfig;
import com.ferysyukur.daggerretrofit.R;
import com.ferysyukur.daggerretrofit.data.model.Movie;
import com.ferysyukur.daggerretrofit.data.model.MovieResponse;
import com.ferysyukur.daggerretrofit.data.remote.ApiService;
import com.ferysyukur.daggerretrofit.ui.detail.DetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ItemClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private List<Movie> movies;

    @Inject
    Retrofit retrofit;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((App) getApplication()).getAppCommponent().inject(this);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        final ApiService apiService = retrofit.create(ApiService.class);

        final Observable<MovieResponse> movie = apiService.getTopRatedMovies(BuildConfig.BASE_KEY);

        movie.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieResponse>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieResponse movieResponse) {
                        progressBar.setVisibility(View.VISIBLE);
                        movies = movieResponse.getResults();
                        adapter = new MovieAdapter(movies, R.layout.list_item_movies, MainActivity.this);
                        adapter.setClickListener(MainActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void OnItemClick(View view, int position) {
        Log.d(TAG, "click: "+movies.get(position).getTitle());
        Intent detail = new Intent(this, DetailActivity.class);
        detail.putExtra("id", movies.get(position).getId());
        startActivity(detail);
    }
}