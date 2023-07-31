package mg.itu.m1p10android.models;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mg.itu.m1p10android.InscriptionActivity;
import mg.itu.m1p10android.LoginActivity;
import mg.itu.m1p10android.MainActivity;


public class User {
    int id;
    String nom;
    String prenom;
    String password;
    String email;
    String telephone;
    String adresse;
    String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public User() {}

    public User(int id, String nom, String prenom, String password, String email, String telephone, String adresse, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.role = role;
    }

    public User(String nom, String prenom, String password, String email, String telephone, String adresse, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.role = role;
    }

    private Context context;

    public User(Context context) {
        this.context = context;
    }


    public void callApi(String url, JSONObject requestBody,ApiCallback callback) {
        try {
            if (context != null) {

                // Créez une instance de RequestQueue
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                // Créez une requête POST pour l'API spécifiée par l'URL
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Gérer la réponse de l'API ici
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    JSONObject jsonObject = new JSONObject(jsonResponse.toString());
                                    String token = jsonObject.getString("token");
                                    saveToken(token);
                                    callback.onSuccess(token);
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    callback.onError("Error parsing response");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Gérer les erreurs de la requête ici
                                saveToken(null);
                                Log.e("ApiCaller", "Error: " + error.getMessage());
                                Toast.makeText(context, "Veillez à vérifier vos informations.", Toast.LENGTH_SHORT).show();

                                callback.onError("API Error: " + error.getMessage());
                            }
                        }) {
                    @Override
                    public byte[] getBody() {
                        // Définir le corps de la requête POST en tant que JSONObject
                        return requestBody.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        // Définir le type de contenu de la requête POST
                        return "application/json";
                    }
                };

                // Ajoutez la requête à la file d'attente de Volley pour l'exécuter
                requestQueue.add(stringRequest);

            }
            else {
                Log.e("ApiCaller", "Context is null, unable to create RequestQueue.");

                callback.onError("Context is null, unable to create RequestQueue.");
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("api", "error -- : "+ e.getLocalizedMessage());

            callback.onError(e.getLocalizedMessage());
        }
    }

    public void saveToken(String token){
    // Obtenez une instance de SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    // Éditez les préférences pour enregistrer le token
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public String getToken(){
        // Obtenez une instance de SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    // Récupérez le token enregistré en utilisant la clé "token"
        String token = sharedPreferences.getString("token", "");
        return token;
    }


    public void callInscriptionApi(String url, JSONObject requestBody, ApiCallback callback) {
        try {
            if (context != null) {
                // Créez une instance de RequestQueue
                RequestQueue requestQueue = Volley.newRequestQueue(context);

                // Créez une requête POST pour l'API spécifiée par l'URL
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Gérer la réponse de l'API ici
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    // Appel de la méthode onSuccess du callback avec la réponse JSON
                                    callback.onSuccess(jsonResponse.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    // En cas d'erreur lors de l'analyse de la réponse JSON
                                    callback.onError("Error parsing response");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Gérer les erreurs de la requête ici
                                Log.e("ApiCaller", "Error: " + error.getMessage());
                                Toast.makeText(context, "Veillez à vérifier vos informations.", Toast.LENGTH_SHORT).show();
                                // Appel de la méthode onError du callback avec le message d'erreur
                                callback.onError("API Error: " + error.getMessage());
                            }
                        }) {
                    @Override
                    public byte[] getBody() {
                        // Définir le corps de la requête POST en tant que JSONObject
                        return requestBody.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        // Définir le type de contenu de la requête POST
                        return "application/json";
                    }
                };

                // Ajoutez la requête à la file d'attente de Volley pour l'exécuter
                requestQueue.add(stringRequest);
            } else {
                Log.e("ApiCaller", "Context is null, unable to create RequestQueue.");
                // Appel de la méthode onError du callback avec le message d'erreur
                callback.onError("Context is null, unable to create RequestQueue.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("api", "error -- : " + e.getLocalizedMessage());
            // Appel de la méthode onError du callback avec le message d'erreur
            callback.onError(e.getLocalizedMessage());
        }
    }


    public void getAllUsers(String url, String token, ApiCallback callback) {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Appel de la méthode onSuccess du callback avec la réponse JSON
//                        Log.e("AllUsers------",response.toString());
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gérer l'erreur de l'appel API ici
                        callback.onError(error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        // Ajoutez la requête à la RequestQueue pour l'exécuter
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }


    public interface ApiCallback {
        void onSuccess(String token);
        void onError(String errorMessage);
    }

}
