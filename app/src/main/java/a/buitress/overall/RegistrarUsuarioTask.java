package a.buitress.overall;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;


/**
 * Created by Marce on 11/12/2015.
 */
public class RegistrarUsuarioTask extends AsyncTask<String, Integer, String> {


    @Override
    protected String doInBackground(String[]  args) {
        try {
            HttpURLConnection UrlConnection = (HttpURLConnection) new URL("http://overall.esy.es/overall/registro.php").openConnection();

            String urlParameters  = "correo="+args[0] + "&" +
                                    "nombre="+args[1] + "&" +
                                    "password="+args[2] + "&" +
                                    "telefono="+args[3];

            UrlConnection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter( UrlConnection.getOutputStream());
            wr.write(urlParameters);
            wr.flush();


            InputStreamReader reader = new InputStreamReader(UrlConnection.getInputStream());
            BufferedReader buffer = new BufferedReader(reader);
            StringBuffer sbuffer= new StringBuffer();
            String s;
            while((s = buffer.readLine()) != null) {
                sbuffer.append(s);
            }

            return sbuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }

    }
}
