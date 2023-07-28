package mg.itu.m1p10android.data.http;

import static mg.itu.m1p10android.data.http.ApiURL.ARTICLE_URL;

import android.content.Context;
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

import mg.itu.m1p10android.data.models.Article;


public class ArticleHttp {

    Context context;


    public ArticleHttp(Context context) {
        this.context = context;
    }

    public Request<JSONArray> fetchAll(OnResponseArrayAction onResponseArrayAction) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, ARTICLE_URL.value, null, getArray(onResponseArrayAction), getErrorListener());
        RequestQueue requestQueue = ApplicationRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        return requestQueue.add(jsonArrayRequest);
    }

    public Request<JSONObject> fetchById(int id, OnResponseObjectAction onResponseObjectAction) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, String.format(ARTICLE_URL.value + "/%d", id), null, getObject(onResponseObjectAction), getErrorListener());
        RequestQueue requestQueue = ApplicationRequestQueue.getInstance(context.getApplicationContext()).getRequestQueue();
        return requestQueue.add(jsonObjectRequest);
    }

    private Response.Listener<JSONObject> getObject(OnResponseObjectAction onResponseObjectAction) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Article a = gson.fromJson(response.toString(), Article.class);
                onResponseObjectAction.doAction(a);
                Log.d("ArticleHttp", "fetchById: id = " + a.getId());
            }
        };
    }

    @NonNull
    private static Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ArticleHttp", error.getLocalizedMessage());
                Log.e("ArticleHttp", Log.getStackTraceString(error));
            }

        };
    }

    @NonNull
    private static Response.Listener<JSONArray> getArray(OnResponseArrayAction onResponseArrayAction) {
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response == null)
                    Log.d("ArticleHttp", "fetchAll: Response is null");
                else {
                    Gson gson = new Gson();
                    Article[] r = gson.fromJson(response.toString(), Article[].class);
                    onResponseArrayAction.doAction(r);
                    Log.d("ArticleHttp", "fetchAll: " + r.length + " result(s)");
                }
            }
        };
    }

    @FunctionalInterface
    public interface OnResponseArrayAction {
        void doAction(Article[] response);
    }

    @FunctionalInterface
    public interface OnResponseObjectAction {
        void doAction(Article response);
    }
}
