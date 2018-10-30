package com.example.dell.myfilmapps.Activities.Activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.dell.filmapps.Activities.Adapters.RvAdapter;
import com.example.dell.filmapps.Activities.Model.Movie;
import com.example.dell.filmapps.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private final String url = "https://api.themoviedb.org/3/movie/550?api_key=fdea261940175606e118510338085e18";
    private final String img = "http://image.tmdb.org/t/p/w185/";
    private JsonArrayRequest ArrayRequest;
    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private ArrayList<Movie> lstMovie = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerviewId);
        jsoncall();
    }

    private void jsoncall() {
        ArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setName(jsonObject.getString("title"));
                        movie.setPopularity(jsonObject.getString("popularity"));
                        movie.setRating(jsonObject.getString("vote_average"));
                        movie.setSinopsis(jsonObject.getString("overview"));
                        movie.setImage_url(jsonObject.getString(img+"poster_path"));
                        lstMovie.add(movie);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(MainActivity.this, "Size of LISTE" + String.valueOf(lstMovie.size()), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, lstMovie.get(1).toString(), Toast.LENGTH_SHORT).show();

                setRvadapter(lstMovie);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(ArrayRequest);
    }

    public void setRvadapter(ArrayList<Movie> lst) {
        RvAdapter myAdapter = new RvAdapter(this, lst);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
