package mg.itu.m1p10android.models;

import android.app.Application;

public class MyApp extends Application {
    private static final String BASE_URL = "https://0794-154-126-56-74.ngrok-free.app/"; // Remplacez par l'URL de base de votre API

    public static String getBaseUrl() {
        return BASE_URL;
    }
}
