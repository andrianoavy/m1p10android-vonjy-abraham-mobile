package mg.itu.m1p10android.models;

import android.content.Context;
import android.content.Intent;
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

    public void verifyConnexion(String email, String password) {
        String url = "http://localhost:3000/api/auth/login";
        JSONObject requestBody = new JSONObject();
        User user = new User();
        try {
            requestBody.put("email", email);
            requestBody.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<User> allUser = new ArrayList<User>();
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject  response) {
                        try{
                            Gson gson = new Gson();

                            User user = gson.fromJson(response.toString(), User.class);
                            Log.e("api", "Response -- : "+ user.getId());
                            Toast.makeText(context,"Test button connexion -- "+user.getId(),Toast.LENGTH_LONG).show();
//                            if(user.getId() == null){
//                                Toast.makeText(LoginActivity.this,"Error! please verify your email or password", Toast.LENGTH_SHORT).show();
//
//                            }else{
//                                Intent intent = new Intent(context, MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }

                        }catch (Exception e){
                            e.printStackTrace();
                            Log.e("api", "error -- : "+ e.getLocalizedMessage());

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("api", "onErrorResponse: "+ error.getLocalizedMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }



    public void callApi(String url, JSONObject requestBody) {
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
                                // Vous pouvez maintenant traiter la réponse JSON de l'API
                                // par exemple, extraire des données spécifiques du JSON
                                // et les afficher à l'utilisateur
                                Log.e("json",jsonResponse.toString());
                                String data = jsonResponse.getString("data");
                                Toast.makeText(context, "API Response: " + data, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Gérer les erreurs de la requête ici
                            Log.e("ApiCaller", "Error: " + error.getMessage());
                            Toast.makeText(context, "API Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        }
    }
}
