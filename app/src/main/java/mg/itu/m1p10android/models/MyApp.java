package mg.itu.m1p10android.models;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import mg.itu.m1p10android.data.ArticleRepository;
import mg.itu.m1p10android.data.http.ArticleHttp;
import mg.itu.m1p10android.data.local.ArticleDao;
import mg.itu.m1p10android.data.local.LocalDb;

public class MyApp extends Application {
    private static final String BASE_URL = "https://0794-154-126-56-74.ngrok-free.app/"; // Remplacez par l'URL de base de votre API

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static ArticleRepository getRepo(Context context, ArticleHttp api, ArticleDao dao){
        boolean isConnected = isConnected(context);
        if(isConnected) {
            return api;
        }
        else{
            Toast.makeText(context, "Pas de connexion Internet!", Toast.LENGTH_SHORT).show();
            return dao;
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
}
