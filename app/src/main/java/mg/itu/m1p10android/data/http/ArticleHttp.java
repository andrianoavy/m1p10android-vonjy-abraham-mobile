package mg.itu.m1p10android.data.http;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import mg.itu.m1p10android.BuildConfig;
import mg.itu.m1p10android.data.ArticleRepository;
import mg.itu.m1p10android.data.models.Article;


public class ArticleHttp implements ArticleRepository{

    Context context;

    private final String ARTICLE_URL = BuildConfig.ApiUrl+"/article";
    private final String ARTICLE_AUTHORITY = BuildConfig.ApiAuthority+"/article";

    public ArticleHttp(Context context) {
        this.context = context;
    }

    @Override
    public void fetchAll(ArticleRepository.OnArrayAction onArrayAction) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ARTICLE_URL, null, getArray(onArrayAction), getErrorListener());
        RequestQueue requestQueue = ApplicationRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void fetchAllFull(ArticleRepository.OnArrayAction onArrayAction) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ARTICLE_URL + "/full", null, getArray(onArrayAction), getErrorListener());
        RequestQueue requestQueue = ApplicationRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void fetchById(int id, ArticleRepository.OnObjectAction onObjectAction) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, String.format(ARTICLE_URL + "/%d", id), null, getObject(onObjectAction), getErrorListener());
        RequestQueue requestQueue = ApplicationRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        requestQueue.add(jsonObjectRequest);
    }


    private Response.Listener<JSONObject> getObject(ArticleRepository.OnObjectAction onObjectAction) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                final Article a = gson.fromJson(response.toString(), Article.class);
                onObjectAction.doAction(a);
                Log.d("ArticleHttp", "fetchById: id = " + a.getId());
            }
        };
    }

    @NonNull
    private Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ArticleHttp", Log.getStackTraceString(error));
            }

        };
    }

    @NonNull
    private Response.Listener<JSONArray> getArray(ArticleRepository.OnArrayAction onArrayAction) {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response == null)
                    Log.d("ArticleHttp", "Get: Response is null");
                else {
                    Gson gson = new Gson();
                    final Article[] r = gson.fromJson(response.toString(), Article[].class);
                    onArrayAction.doAction(r);
                    Log.d("ArticleHttp", "Get Array: " + r.length + " result(s)");
                }
            }
        };
    }

    @Override
    public void findByText(String searchStr, ArticleRepository.OnArrayAction action) {

        Uri.Builder builder = new Uri.Builder();
        Uri uri = builder
                .scheme(BuildConfig.Scheme)
                .encodedAuthority(ARTICLE_AUTHORITY)
                .appendQueryParameter("s", searchStr)
                .build();
        Log.d("ArticleHttp", "FindByText: " + uri.toString());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, uri.toString(), null, getArray(action), getErrorListener());

        RequestQueue requestQueue = ApplicationRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        requestQueue.add(jsonArrayRequest);

    }


    public void saveTitre(String titre){
        // Obtenez une instance de SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Éditez les préférences pour enregistrer le token
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("titre", titre);
        editor.apply();
    }

    public String getTitre(){
        // Obtenez une instance de SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        // Récupérez le token enregistré en utilisant la clé "token"
        String titre = sharedPreferences.getString("titre", "");
        return titre;
    }

}
