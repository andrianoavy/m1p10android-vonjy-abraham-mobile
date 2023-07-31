package mg.itu.m1p10android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import mg.itu.m1p10android.models.MyApp;
import mg.itu.m1p10android.models.User;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button inscription = findViewById(R.id.btn_inscription);
        EditText champ_nom = findViewById(R.id.champ_nom);
        EditText champ_prenom = findViewById(R.id.champ_prenom);
        EditText champ_email = findViewById(R.id.champ_email);
        EditText champ_pwd = findViewById(R.id.champ_pwd);

        String baseUrl = MyApp.getBaseUrl();

        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    User user = new User(getApplicationContext());
                    String nom = champ_nom.getText().toString();
                    String prenom = champ_prenom.getText().toString();
                    String email = champ_email.getText().toString();
                    String password = champ_pwd.getText().toString();
                    JSONObject requestBody = new JSONObject();
                    try {
                        requestBody.put("nom", nom);
                        requestBody.put("prenom", prenom);
                        requestBody.put("email", email);
                        requestBody.put("password", password);
                        requestBody.put("adresse", "");
                        requestBody.put("telephone", "");
                        requestBody.put("role", "Client");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Appelez la méthode callApi avec l'URL de l'API et le requestBody contenant les paramètres
                    user.callInscriptionApi(baseUrl + "api/auth/inscription", requestBody, new User.ApiCallback() {
                        @Override
                        public void onSuccess(String token) {
                            JSONObject requestBodyLogin = new JSONObject();
                            try {
                                requestBodyLogin.put("email", email);
                                requestBodyLogin.put("password", password);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            user.callApi(baseUrl + "api/auth/login", requestBodyLogin, new User.ApiCallback() {
                                @Override
                                public void onSuccess(String token) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onError(String errorMessage) {
//                                    Intent intent = new Intent(getApplicationContext(), InscriptionActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onError(String errorMessage) {
//                            Intent intent = new Intent(getApplicationContext(), InscriptionActivity.class);
//                            startActivity(intent);
//                            finish();
                        }
                    });



                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        TextView login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}