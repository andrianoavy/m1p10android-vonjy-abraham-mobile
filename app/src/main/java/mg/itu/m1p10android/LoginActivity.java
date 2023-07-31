package mg.itu.m1p10android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import mg.itu.m1p10android.models.MyApp;
import mg.itu.m1p10android.models.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_connexion = findViewById(R.id.btn_inscription);
        EditText champ_nom = findViewById(R.id.champ_nom);
        EditText champ_pwd = findViewById(R.id.champ_pwd);

        String baseUrl = BuildConfig.ApiUrl;
        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    User user = new User(getApplicationContext());
                    String email = champ_nom.getText().toString();
                    String password = champ_pwd.getText().toString();
                    JSONObject requestBody = new JSONObject();
                    try {
                        requestBody.put("email", email);
                        requestBody.put("password", password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Appelez la méthode callApi avec l'URL de l'API et le requestBody contenant les paramètres
                    user.callApi(baseUrl + "/auth/login", requestBody, new User.ApiCallback() {
                        @Override
                        public void onSuccess(String token) {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onError(String errorMessage) {
//                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                            startActivity(intent);
//                            finish();
//                            Log.e("ApiCaller", "Error: " + errorMessage);
                        }
                    });
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        TextView inscription = findViewById(R.id.inscription);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InscriptionActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}