package mg.itu.m1p10android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import mg.itu.m1p10android.models.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_connexion = findViewById(R.id.btn_connexion);

        btn_connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(getApplicationContext());
                String email = "Vonjy@gmail.com";
                String password = "123456";
//                user.verifyConnexion(email,password);
//                Toast.makeText(getApplicationContext(),"Test button connexion",Toast.LENGTH_LONG).show();

                JSONObject requestBody = new JSONObject();
                try {
                    requestBody.put("email", email);
                    requestBody.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Appelez la méthode callApi avec l'URL de l'API et le requestBody contenant les paramètres
                user.callApi("http://192.168.1.110:3000/api/auth/login", requestBody);

            }
        });
    }
}