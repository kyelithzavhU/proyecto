package com.example.proyecto;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class Registro extends AppCompatActivity {
    EditText t_usuario;
    EditText t_password;
    EditText t_rpassword;
    EditText t_email;
    Button b_insertar;

       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        t_usuario=findViewById(R.id.txtusuario);
        t_password=findViewById(R.id.txtpassword);
        t_rpassword=findViewById(R.id.txtpassword2);
        t_email=findViewById(R.id.txtemail);
        b_insertar=findViewById(R.id.b_registrar);

        b_insertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDatos();
            }
        });
    }

    private void insertarDatos(){
           final String usuario=t_usuario.getText().toString().trim();
           final String password=t_password.getText().toString().trim();
           final String repassword=t_rpassword.getText().toString().trim();
           final String email=t_email.getText().toString().trim();

           final ProgressDialog progressDialog = new ProgressDialog( this);
           progressDialog.setMessage("cargando");

           if(usuario.isEmpty()){
               t_usuario.setError("Complete los campos");
           } else if (email.isEmpty()) {
               t_email.setError("Complete los campos");
               return;
           }else{
               progressDialog.show();
               StringRequest request = new StringRequest(Request.Method.POST, "http://localhost/proyecto/login/insertar.php", new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       if (response.equalsIgnoreCase("registrado correctamente")) {
                           Toast.makeText(Registro.this, "datos insertados", Toast.LENGTH_SHORT).show();
                           progressDialog.dismiss();

                           Intent intent = new Intent(Registro.this, Login.class);
                           startActivity(intent);
                       } else {
                           Toast.makeText(Registro.this, response, Toast.LENGTH_SHORT).show();
                           progressDialog.dismiss();
                           Toast.makeText(Registro.this, "No se pudo insertar", Toast.LENGTH_SHORT).show();
                       }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(Registro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                       progressDialog.dismiss();
                   }
               }){
                   @Nullable
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError{
                       Map<String,String>params= new HashMap<>();
                       params.put("usuario",usuario);
                       params.put("password",password);
                       params.put("email",email);

                       return params;
                   }
               };

               RequestQueue requestQueue= Volley.newRequestQueue(Registro.this);
               requestQueue.add(request);
           }
    }
    public void onBackPressed(){
           super.onBackPressed();
           finish();
    }

    public void login(View view){
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}