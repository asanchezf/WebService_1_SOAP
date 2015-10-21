package com.antonioejemplos.webservice_1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {


    //Constantes para la invocacion del web service
/*    private static final String NAMESPACE = "http://tempuri.org/";
    private static String URL="http://192.168.0.231/EjemploWS/Service.asmx";
    private static final String METHOD_NAME = "getAllAndroidOS";
    private static final String SOAP_ACTION ="http://tempuri.org/getAllAndroidOS";*/


    //Constantes para la invocacion del web service. Están puestas de prueba. Más abajo se utilizan en un método... y se vuvlven a definir

    private static final String NAMESPACE = "http://WebServices/";
    private static String URL="http://192.168.0.154:8080/WebServices_Conversion_Unidades_Medida/ConversionMedidas?xsd ";
    private static final String METHOD_NAME = "pulgadasACentimetros";
    private static final String SOAP_ACTION ="http://WebServices/pulgadasACentimetros";




    //Hay qye importar una libreria ksoap2 y meterla en libs. hacer también referencia a ella ella en el gradle.

    //Variables para consumir el servicio
    private SoapObject request=null;
    private SoapSerializationEnvelope envelope=null;
    private SoapPrimitive resultsRequestSOAP=null;


    //

    EditText txtEntrada;
    EditText txtResultado;
    Button btnResultado;
    Button btnServicioRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        txtEntrada=(EditText)findViewById(R.id.txtentrada);
        txtResultado=(EditText)findViewById(R.id.resultado);
        btnResultado=(Button)findViewById(R.id.calcular);

        btnServicioRest=(Button)findViewById(R.id.btnservicioRest);

        btnResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*request = new SoapObject(NAMESPACE,METHOD_NAME);
                request.addProperty("pulgadasACentimetros",Double.parseDouble(txtEntrada.getText().toString()));
                envelope=new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE transporte=new HttpTransportSE(URL);
                try {

                    transporte.call(SOAP_ACTION,envelope);
                    resultsRequestSOAP=(SoapPrimitive) envelope.getResponse();


                } catch (IOException e) {
                    e.printStackTrace();
                    txtResultado.setText("Error printStackTrace");

                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    txtResultado.setText("Error XmlPullParserException");
                }

                String resultado=resultsRequestSOAP.toString();
                txtResultado.setText(resultado);*/

                //request.addProperty("n1",Double.parseDouble(txtEntrada.getText().toString()));
               new consumirWS().execute(txtEntrada.getText().toString());
               //
                //new consumirWS().execute(Double.parseDouble(txtEntrada.getText().toString()));
            }
        });



        btnServicioRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,ServicioREST.class);
                //intent(MainActivity.this,ServicioREST.class);

                startActivity(intent);

            }
        });
    }




    private class consumirWS extends AsyncTask<String, Void, String> {

        //PARÁMETROS: parámetros para el método doInBackground,unidades del progreso, resultado

        final String SOAP_ACTION = "http://WebServices/pulgadasACentimetros";//Es el NAMESPACE + METHOD...
        final String METHOD = "pulgadasACentimetros";//Es el operation name. El del método al que queramos llamar...Aquí hay dos: pulgadasACentimetros y centimetrosAPulgadas
        final String NAMESPACE = "http://WebServices/";//Es el namespace del WSDL
        final String ENDPOINTWS = "http://192.168.0.154:8080/WebServices_Conversion_Unidades_Medida/ConversionMedidas?xsd ";//Es el schemaLocation. Se sustituye el localhost por la IP de la máquina donde esté alojado el WebService

        //    private static final String NAMESPACE = "http://WebServices/";
//    private static String URL="http://192.168.0.154:8080/WebServices_Conversion_Unidades_Medida/ConversionMedidas?xsd ";
//    private static final String METHOD_NAME = "ConversionMedidas";
//    private static final String SOAP_ACTION ="http://WebServices/pulgadasACentimetros";


        String respuesta = null;
        //Double respuesta = null;


      // protected String doInBackground(String... args) {

        @Override
        protected String doInBackground(String... args) {
            SoapObject userRequest = new SoapObject(NAMESPACE, METHOD);

            userRequest.addProperty("pulgadas", args[0]);//pulgadas es el nombre del parámetro  que recibe el método pulgadasACentimetros

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(userRequest);

            try{
                HttpTransportSE androidHttpTransport = new HttpTransportSE(ENDPOINTWS);
                androidHttpTransport.debug = true;
                androidHttpTransport.call(SOAP_ACTION, envelope);




                //respuesta = Double.valueOf(envelope.getResponse().toString());

                respuesta = envelope.getResponse().toString();
            }
            catch (Exception e){
                e.printStackTrace();
               // txtResultado.setText("Error printStackTrace");
            }

            return respuesta;

        }


        protected void onPostExecute(String result)
        {
            txtResultado.setText(result);

            super.onPostExecute(String.valueOf(result));
        }
    }


    //<editor-fold desc="CREACIÓN DE LOS MENÚS">
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //</editor-fold>
}
