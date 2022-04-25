package com.example.php_pdo_mysql_loginregistrator;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    // Definimos los objetos a utilizar
    private EditText etUser, etPwd;
    private TextView tv1;

    // Definimos las variables
    String sUser, sPwd;
    String url = "http://dpmo.excelsolutionsv.com/login2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Establecemos la Comunicación de la Parte Lógica con la Partte Gráfica
        etUser = (EditText) findViewById(R.id.etUser);
        etPwd = (EditText) findViewById(R.id.etPwd);
        tv1 = (TextView) findViewById(R.id.tv1);
    }

    // Funciones del Sistema
    public void btnLogin (View view) {
        if (etUser.getText().toString().isEmpty()) { // Otra forma de hacerlo es .equals("")
            Toast.makeText(this, "Please Enter your Username or Email", Toast.LENGTH_SHORT).show();
        } else  if (etPwd.getText().toString().equals("")) { // Otra forma de hacerlo es con is.Empty()
            Toast.makeText(this, "Password is Required", Toast.LENGTH_SHORT).show();
        } else {
            // Si los datos no estan vacios
            // Mostramos un Progress Dialog
            final ProgressDialog pd = new ProgressDialog(this);
            pd.setTitle("Please wait...");
            pd.show();

            // Asignamos los valores
            sUser = etUser.getText().toString().trim(); // Con trim limpiamos los espacios en blanco
            sPwd = etPwd.getText().toString().trim();
            tv1.setText(sUser + sPwd);

            // Enviamos la consulta con ayuda de Volley
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Cerramos el Progress Dialog
                    pd.dismiss();

                    // Estructura Condicional
                    if (response.equalsIgnoreCase("Logged in Successfully!!")) {
                        // Limpiamos los Campos
                        etUser.getText().clear();
                        etPwd.setText("");

                        // Mandamos al Usuario al Activity Main
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pd.dismiss();
                    Toast.makeText(LoginActivity.this, error.getMessage().toString(),Toast.LENGTH_SHORT).show();
                }

            }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user", sUser);
                    params.put("pwd", sPwd);
                    return params;
                }
            };

            // Mandamos la Información a PHP
            RequestQueue rq = Volley.newRequestQueue(LoginActivity.this);
            rq.add(request);
        }
    }

    // Mandamos al Usuario al Registro
    public void lblSignUp(View view) {
        startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
    }
}