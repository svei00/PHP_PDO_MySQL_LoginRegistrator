package com.example.php_pdo_mysql_loginregistrator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Establecemos los objetos que usaremos
    private EditText etUser, etEmail, etPwd1, etPwd2;

    // Declaramos las Variables a Utilizar
    String sUser, sEmail, sPwd;

    /*
    No Olvidar poner en build.gradle (app) la línea de código
    implementation 'com.android.volley:volley:1.2.1'
    previo a revisar la versión más reciente
    Así como inicializar la Sync (Sincronización en la parte superior derecha)

    También en el AndroidManifest poner el permiso:
    <uses-permission android:name="android.permission.INTERNET"/>
     */

    // Variable donde se guardará el URL de la página
    String url = "http://dpmo.excelsolutionsv.com/register2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Establecemos la conexión entre la parte lógica y la parte gráfica
        etUser = (EditText) findViewById(R.id.txtUser);
        etEmail = (EditText) findViewById(R.id.txtEmail);
        etPwd1 = (EditText) findViewById(R.id.txtPwd1);
        etPwd2 = (EditText) findViewById(R.id.txtPwd2);

    }

    // Damos la Funcionalidad al Botón btnRegister
    public void btnRegister (View view){
        // Progress Dialog para enviarle un mensaje al usuario que esperé en lo que se procesa la inforación
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please Wait...");

        // Estructura Condicional if - else
        if (etUser.getText().toString().equals("")) { // También se podría guardar en una variable lo obtenido del EditText
            // Si la estructura se cumple, es decir que el EditText este vacio:
            Toast.makeText(this, "Please enter Username", Toast.LENGTH_SHORT).show();

        } else if (etEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter Email Addreess", Toast.LENGTH_SHORT).show();

        } else if (etPwd1.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter your Password", Toast.LENGTH_SHORT).show();

        } else if (etPwd2.getText().toString().equals("")) {
            Toast.makeText(this,"Please confirm your Password", Toast.LENGTH_SHORT).show();

            // Mandamos al Usuario al EditText de Confirmación de Password
            etPwd2.requestFocus();

            // Mandamos llamar al Teclado
            InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(etPwd2, InputMethodManager.SHOW_IMPLICIT);

        } else if (!etPwd1.getText().toString().trim().equals(etPwd2.getText().toString().trim())){
            // Si el Password no coincide
            Toast.makeText(this, "Passwords does not Match", Toast.LENGTH_SHORT).show();

        } else {
            // Mostramos el Progress Dialog
            pd.show();

            // Caso Contrario (Si existan datos)
            // Con trim quitamos los espacios en Blanco
            sUser = etUser.getText().toString().trim();
            sEmail = etEmail.getText().toString().trim();
            sPwd = etPwd1.getText().toString().trim();

            // Utilizamos el Método String Request
            // Usamos POST para guardar los datos y escribimos la Variable del URL
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Cerramos le ProgressDialog
                    pd.dismiss();

                    // Limpiamos los EditText
                    etUser.setText(""); // 1er. Forma de Limpiarlo
                    etEmail.getText().clear(); // 2da. Forma de Limpiarlo
                    etPwd1.setText(null); // 3er. Forma de Limpiarlo
                    etPwd2.setText(null);

                    // Mostramos la respuesta
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Cerramos el ProgressDialog
                    pd.dismiss();

                    // Mostramos el Mensaje de Error
                    Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show(); // Otra forma de declarar el this
                }
            }
            ) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String, String > params = new HashMap<String, String>();

                    // Pasamos los Parametros a la Base de Datos
                    params.put("user", sUser);
                    params.put("email", sEmail);
                    params.put("pwd", sPwd);
                    return params;
                }
            };

            // Agregamos la Cola para mantener la llamada en el Background
            RequestQueue rq = Volley.newRequestQueue(this);
            rq.add(request);
        }

    }

    public void lblLogin(View view) {
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }

    /*
    Fuentes de Consulta:
    * https://www.youtube.com/watch?v=tzannwUfxW4&list=PLtZcxNkZv4_cd_3YiTV4j7pf0haWq_95Z&index=1
    * https://codeshack.io/crud-application-php-pdo-mysql/
    * https://phpgurukul.com/php-crud-operation-using-pdo-extension/
    * https://codewithawa.com/posts/how-to-create-a-blog-in-php-and-mysql-database---db-design
     */
}