package a.buitress.overall;

import android.location.Location;
import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class UpdateLocationTask extends AsyncTask<Double, Integer, String> {


    private String _correo;
    private pantallamapa _pantalla;
    private HashMap<String, LatLng> locsList;
    private HashMap<String, String> usersList;
    private String _message;
    public UpdateLocationTask(String correo, pantallamapa pantalla) {
        super();
        _correo = correo;
        _pantalla = pantalla;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            if(locsList != null) {
                _pantalla.updateMarkers(locsList, usersList);
            }
        } catch (Exception e) {
            _pantalla.toastMessage(e.getMessage());
        }
    }

    @Override
    protected String doInBackground(Double[] args) {
        try {
            HttpURLConnection UrlConnection = (HttpURLConnection) new URL("http://overall.esy.es/overall/location.php").openConnection();

            String urlParameters  = "correo="+_correo + "&" +
                    "lat="+args[0] + "&" +
                    "long="+args[1];


            UrlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter( UrlConnection.getOutputStream());
            wr.write(urlParameters);
            wr.flush();


            InputStreamReader reader = new InputStreamReader(UrlConnection.getInputStream());
            BufferedReader buffer = new BufferedReader(reader);
            StringBuilder sbuffer= new StringBuilder();
            String s;

            while((s = buffer.readLine()) != null) {
                sbuffer.append(s);
            }

            JSONObject obj = new JSONObject(sbuffer.toString());
            JSONArray locationArray = obj.getJSONArray("results");
            JSONArray usersArray = obj.getJSONArray("users");

            locsList = new HashMap<>();
            usersList = new HashMap<>();

            int len = locationArray.length();

            for(int i =0; i< len; i++) {
                JSONObject jsonLoc = locationArray.getJSONObject(i);

                locsList.put(jsonLoc.getString("ID"), new LatLng(jsonLoc.getDouble("LATITUD"), jsonLoc.getDouble("LONGITUD")));
            }

            len = usersArray.length();

            for(int i = 0; i < len; i++) {
                JSONObject user = usersArray.getJSONObject(i);
                usersList.put(user.getString("id"), "nombre: " + user.getString("nombre") + "\ncorreo: "+ user.getString("correo"));
            }

            return sbuffer.toString();


        } catch (IOException e) {
            _message = e.getMessage();
        } catch (JSONException e) {
            _message = e.getMessage();
        }

        return null;
    }
}
