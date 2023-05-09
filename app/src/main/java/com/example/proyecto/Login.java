package com.example.proyecto;

import static com.google.android.material.color.utilities.MaterialDynamicColors.error;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {
    EditText t_usuario;
    EditText t_password;
    String str_usuario, str_password;
    String url ="http://localhost/proyecto/login/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t_usuario =findViewById(R.id.txt_usuario);
        t_password=findViewById(R.id.txt_password);
    }
    public void login(View view){
        if(t_usuario.getText().toString().equals("")){
            Toast.makeText(this, "Ingrese el usuario", Toast.LENGTH_SHORT).show();
        } else if (t_password.getText().toString().equals("")) {
            Toast.makeText(this, "Ingrese la contraseña", Toast.LENGTH_SHORT).show();
        }else{
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("por favor espere");
            progressDialog.show();
            
            str_usuario=t_usuario.getText().toString().trim();
            str_password=t_password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();

                    if (response.equalsIgnoreCase("ingreso correctamente")) {
                        t_usuario.setText("");
                        t_password.setText("");
                        startActivity(new Intent(getApplicationContext(), Home.class));
                    } else {
                        Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Login.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError{
                    Map<String, String> params = new HashMap<>();
                    params.put("email",str_usuario);
                    params.put("password",str_password);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Login.this);
            requestQueue.add(request);
        }
    }
    public void registro(View view){
        startActivity(new Intent(getApplicationContext(), Registro.class));
        finish();
    }
}