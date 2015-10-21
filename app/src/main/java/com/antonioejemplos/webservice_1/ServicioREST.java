package com.antonioejemplos.webservice_1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class ServicioREST extends AppCompatActivity {

    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_rest);
        lista = (ListView) findViewById(R.id.list1);
        MostrarDatosWS mostrarDatosWS = new MostrarDatosWS();
        mostrarDatosWS.execute();


    }

    private class MostrarDatosWS extends AsyncTask<String, Integer, Boolean> {

        private String[] clientes;//Para guardar los datos recogidos

        @Override
        protected Boolean doInBackground(String... params) {

            int id_Servidor;
            String nombre;
            String apellidos;
            String direccion;
            String telefono;
            String email;
            String observaciones;

            Boolean result = true;
            HttpClient httpClient = new DefaultHttpClient();


////"http://localhost:8080/WebServicesRESTGlassFishJEE7/webresources/contactos" + id Para buscar por id


            try {
                HttpGet httpGet = new HttpGet("http://192.168.0.154:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");
                httpGet.setHeader("content-type", "application-json");//Método Post:obtenr los datos

                JSONObject dato = new JSONObject();

                HttpResponse httpResponse = httpClient.execute(httpGet);

                String respStr = EntityUtils.toString(httpResponse.getEntity());

                JSONArray respJson = new JSONArray(respStr);

                clientes = new String[respJson.length()];

                for (int i = 0; 1 < clientes.length; i++) {

                    JSONObject resp = new JSONObject();
                    id_Servidor = resp.getInt("id_Servidor");
                    nombre = resp.getString("nombre");
                    //apellidos=resp.getString("apellidos");
                    //direccion=resp.getString("direccion");
                    //telefono=resp.getString("telefono");
                    //email=resp.getString("email");
                    //clientes[i]=id_Servidor+nombre+apellidos+direccion+telefono+email;

                    clientes[i] = id_Servidor + nombre;
                }


                if (!respStr.equals("true")) {
                    result = false;
                }



            } catch (Exception e) {
                Log.e("ServicioREst", "Error", e);
                e.printStackTrace();
            }


            return result;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(ServicioREST.this, android.R.layout.simple_list_item_1, clientes);
                lista.setAdapter(adaptador);

            }

            super.onPostExecute(result);
        }
    }


    private class InsertarDatosWS extends AsyncTask<String, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {
            Boolean result = true;
            HttpClient httpClient = new DefaultHttpClient();


            HttpPost httpPost = null;
            try {
                httpPost = new HttpPost("http://localhost:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");
            } catch (Exception e) {
                e.printStackTrace();
            }
            httpPost.setHeader("content-type", "application-json");//Método Post:insertar

            JSONObject dato = new JSONObject();
            try {
                dato.put("nombre", params[0]);
                dato.put("apellidos", params[1]);
                dato.put("direccion", params[2]);
                dato.put("telefono", params[3]);
                dato.put("email", params[4]);
                dato.put("id_categoria", params[5]);
                dato.put("Observaciones", params[6]);
                dato.put("android_id", Integer.parseInt(params[7]));


                StringEntity entity = new StringEntity(dato.toString());
                httpPost.setEntity(entity);

                HttpResponse httpResponse = httpClient.execute(httpPost);

                String respStr = EntityUtils.toString(httpResponse.getEntity());

                if (!respStr.equals("true")) {
                    result = false;
                }

/////////////////////LLAMDA A ESTA CLASE PASÁNDOLE LOS PARÁMETROS PARA SE EJECUTE EL SERVICIO E INSERTE LOS DATOS....
                //LLAMADA HIPOTÉTICA A LA CLASE PASÁNDOLE DATOS
                //InsertarDatosWS insertarDatosWS=new InsertarDatosWS();
                //insertarDatosWS.execute(txtNombre.setText.toString(),txtApellido.setText.toString(), txtDireccion.setTexT.toString()....);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            } catch (Exception e) {
                Log.e("ServicioREst", "Error", e);
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                //Sacar mensaje confirmando el alta de usuario en la agenda....
            }

            super.onPostExecute(result);
        }
    }

    private class BorrarDatosWS extends AsyncTask<String, Integer, Boolean> {


        @Override
        protected Boolean doInBackground(String... params) {
           /* Boolean result=true;
            String id=params[0];
            HttpClient httpClient=new DefaultHttpClient();
            HttpDelete httpDelete=new HttpDelete("http://localhost:8080/WebServicesRESTGlassFishJEE7/webresources/contactos" +id);
            httpDelete.setHeader("content-type","application-json");//Método Put:editar

            try {


                HttpResponse httpResponse=httpClient.execute(httpDelete );

                String respStr= EntityUtils.toString(httpResponse.getEntity());

                if(!respStr.equals("true")){
                    result=false;
                }


            } catch (Exception e) {
                Log.e("ServicioREst","Error",e);
                e.printStackTrace();
            }



        }*/
            return null;
        }


        private class ModificarDatosWS extends AsyncTask<String, Integer, Boolean> {

            @Override
            protected Boolean doInBackground(String... params) {
                Boolean result = true;
                HttpClient httpClient = new DefaultHttpClient();


                HttpPut httpPut = null;
                try {
                    httpPut = new HttpPut("http://localhost:8080/WebServicesRESTGlassFishJEE7/webresources/contactos");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                httpPut.setHeader("content-type", "application-json");//Método Put:editar

                JSONObject dato = new JSONObject();
                try {

                    dato.put("nombre", params[0]);
                    dato.put("apellidos", params[1]);
                    dato.put("direccion", params[2]);
                    dato.put("telefono", params[3]);
                    dato.put("email", params[4]);
                    dato.put("id_categoria", params[5]);
                    dato.put("Observaciones", params[6]);
                    dato.put("android_id", Integer.parseInt(params[7]));
                    dato.put("Id", Integer.parseInt(params[8]));//NECESARIO ENVIAR SIEMPRE EL ID DEL REGISTRO A MODIFICAR

                    StringEntity entity = new StringEntity(dato.toString());
                    httpPut.setEntity(entity);

                    HttpResponse httpResponse = httpClient.execute(httpPut);

                    String respStr = EntityUtils.toString(httpResponse.getEntity());

                    if (!respStr.equals("true")) {
                        result = false;
                    }


                } catch (Exception e) {
                    Log.e("ServicioREst", "Error", e);
                    e.printStackTrace();
                }


                return null;
            }


            @Override
            protected void onPostExecute(Boolean result) {
                if (result) {
                    //Sacar mensaje confirmando la edición del usuario en la agenda en la agenda....
                }

                super.onPostExecute(result);
            }


        }
    }
}

