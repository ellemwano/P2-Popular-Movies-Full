package com.mwano.lauren.popular_movies.utils;

import android.util.Log;

import com.mwano.lauren.popular_movies.model.Movie;
import com.mwano.lauren.popular_movies.model.Review;
import com.mwano.lauren.popular_movies.model.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by ElleMwa on 24/02/2018.
 */

public class JsonUtils {

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String POSTER_PATH = "poster_path";
    private static final String BACKDROP_PATH = "backdrop_path";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String OVERVIEW = "overview";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String VIDEO_NAME = "name";
    private static final String VIDEO_KEY = "key";
    private static final String REVIEW_ID = "id";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_URL = "url";
    private static final String TAG = JsonUtils.class.getSimpleName();

    // Constructor
    private JsonUtils() {
    }

    /**
     * Parse the json from TMDB API and return a list of movies
     * @param json from The Movie DataBase API
     * @return An ArrayList of movie objects
     */
    public static ArrayList<Movie> parseMovieJson(String json) {
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(json);
            if (rootObject.has(RESULTS)) {
                JSONArray movieArray = rootObject.getJSONArray(RESULTS);
                for (int i = 0; i < movieArray.length(); i++) {
                    JSONObject currentMovie = movieArray.getJSONObject(i);
                    int id = currentMovie.optInt(ID);
                    String imagePath = currentMovie.optString(POSTER_PATH);
                    String backdropPath = currentMovie.optString(BACKDROP_PATH);
                    String originalTitle = currentMovie.optString(ORIGINAL_TITLE);
                    String synopsis = currentMovie.optString(OVERVIEW);
                    String originalReleaseDate = currentMovie.optString(RELEASE_DATE);
                    Double rating = currentMovie.optDouble(VOTE_AVERAGE);
                    String releaseDate = null;
                    try {
                        releaseDate = Movie.formatDate(originalReleaseDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Movie movie = new Movie(id, imagePath, backdropPath, originalTitle, synopsis, releaseDate, rating);
                    movies.add(movie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Problem parsing movies json");
            return null;
        }
        return movies;
    }


    /**
     * Parse the json from TMDB API and return a list of videos
     * @param json from The Movie DataBase API
     * @return An ArrayList of video objects
     */
    public static ArrayList<Video> parseVideoJson (String json) {
        ArrayList<Video> videos = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(json);
            if (rootObject.has(RESULTS)) {
                JSONArray videoArray = rootObject.getJSONArray(RESULTS);
                for (int i = 0; i < videoArray.length(); i++) {
                    JSONObject currentVideo = videoArray.getJSONObject(i);
                    String videoName = currentVideo.optString(VIDEO_NAME);
                    String videoKey = currentVideo.optString(VIDEO_KEY);
                    Video video = new Video(videoName, videoKey);
                    videos.add(video);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Problem parsing videos json");
            return null;
        }
        return videos;
    }

    /**
     * Parse the json from TMDB API and return a list of reviews
     * @param json from The Movie DataBase API
     * @return An ArrayList of review objects
     */
    public static ArrayList<Review> parseReviewJson (String json) {
        ArrayList<Review> reviews = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(json);
            if (rootObject.has(RESULTS)) {
                JSONArray reviewArray = rootObject.getJSONArray(RESULTS);
                for (int i = 0; i < reviewArray.length(); i++) {
                    JSONObject currentReview = reviewArray.getJSONObject(i);
                    String reviewId = currentReview.optString(REVIEW_ID);
                    String reviewAuthor = currentReview.optString(REVIEW_AUTHOR);
                    String reviewContent = currentReview.optString(REVIEW_CONTENT);
                    String reviewUrl = currentReview.optString(REVIEW_URL);
                    Review review = new Review(reviewId, reviewAuthor, reviewContent, reviewUrl);
                    reviews.add(review);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "Problem parsing reviews json");
            return null;
        }
        return reviews;
    }
}


