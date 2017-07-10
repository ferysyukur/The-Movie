package com.ferysyukur.daggerretrofit.data.remote;

import com.ferysyukur.daggerretrofit.data.model.Movie;
import com.ferysyukur.daggerretrofit.data.model.MovieDetail;
import com.ferysyukur.daggerretrofit.data.model.MovieResponse;
import com.ferysyukur.daggerretrofit.data.model.TrailerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by ferysyukur on 5/26/17.
 */

public interface ApiService {

    @GET("discover/movie")
    Observable<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Observable<MovieDetail> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Observable<TrailerResponse> getMovieTrailers(@Path("id") int id, @Query("api_key") String apiKey);

}
