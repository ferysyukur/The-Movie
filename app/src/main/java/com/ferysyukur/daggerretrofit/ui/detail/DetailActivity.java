package com.ferysyukur.daggerretrofit.ui.detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ferysyukur.daggerretrofit.App;
import com.ferysyukur.daggerretrofit.BuildConfig;
import com.ferysyukur.daggerretrofit.R;
import com.ferysyukur.daggerretrofit.data.model.Movie;
import com.ferysyukur.daggerretrofit.data.model.MovieDetail;
import com.ferysyukur.daggerretrofit.data.model.MovieResponse;
import com.ferysyukur.daggerretrofit.data.model.Trailer;
import com.ferysyukur.daggerretrofit.data.model.TrailerResponse;
import com.ferysyukur.daggerretrofit.data.remote.ApiService;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    private final String TAG = DetailActivity.class.getSimpleName();

    @Inject
    Retrofit retrofit;

    private List<Movie> movies;

    private static final int RECOVERY_REQUEST = 1;

    private YouTubePlayerView youTubeView;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ((App) getApplication()).getAppCommponent().inject(this);

        id = getIntent().getExtras().getInt("id");
        final TextView idmovie = (TextView) findViewById(R.id.titleMovie);
//        final ImageView imgBackdrop = (ImageView) findViewById(R.id.imgBackdrop);
        final ImageView imgPoster = (ImageView) findViewById(R.id.imgPoster);
        final TextView descMovie = (TextView) findViewById(R.id.descMovie);
        final TextView taglineMovie = (TextView) findViewById(R.id.taglineMovie);
        final TextView statusMovie = (TextView) findViewById(R.id.statusMovie);
        final TextView releaseMovie = (TextView) findViewById(R.id.releaseMovie);
        final TextView rateMovie = (TextView) findViewById(R.id.rateMovie);

        youTubeView = (YouTubePlayerView) findViewById(R.id.trailer);
        youTubeView.initialize(BuildConfig.BASE_API_YUOTUBE, this);

        final ApiService apiService = retrofit.create(ApiService.class);

        final Observable<MovieDetail> movies = apiService.getMovieDetails(id, BuildConfig.BASE_KEY);

        movies.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MovieDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieDetail movieDetail) {
                        idmovie.setText(movieDetail.getOriginalTitle());
                        getUrlImg(movieDetail.getPosterPath(), imgPoster);
                        descMovie.setText(movieDetail.getOverview());
                        taglineMovie.setText(movieDetail.getTagline());
                        statusMovie.setText(movieDetail.getStatus());
                        releaseMovie.setText(movieDetail.getReleaseDate());
                        rateMovie.setText(movieDetail.getVoteAverage().toString()+" ");
//                        getUrlImg(movieDetail.getBackdropPath(), imgBackdrop);
                    }
                });

    }

    private void getUrlImg(String url, ImageView img){
        Glide.with(DetailActivity.this).load(BuildConfig.BASE_URL_IMG+url)
                .thumbnail(0.5f)
                .crossFade()
                .into(img);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
        final Observable<TrailerResponse> trailer = retrofit.create(ApiService.class).getMovieTrailers(id, BuildConfig.BASE_KEY);

        trailer.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<TrailerResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TrailerResponse trailerResponse) {
                        List<Trailer> trailers = trailerResponse.getResults();
                        Toast.makeText(DetailActivity.this, trailers.get(0).getKey(), Toast.LENGTH_SHORT).show();
                        youTubePlayer.cueVideo(trailers.get(0).getKey());
                    }
                });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}