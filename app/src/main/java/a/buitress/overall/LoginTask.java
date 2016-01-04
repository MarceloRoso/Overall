package a.buitress.overall;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Marce on 12/12/2015.
 */
public class LoginTask extends AsyncTask<LoginInfo, Integer, String> {

    private LoginListener _listener;

    public LoginTask (LoginListener listener) {
        super();
        _listener = listener;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.equals("1")) {
            _listener.onLoginSuccesful();
        }
        else {
            _listener.toastMessage(result);
        }

    }

    @Override
    protected String doInBackground(LoginInfo... args) {
        try {
            LoginInfo userInfo = args[0];

            String url = "http://overall.esy.es/overall/login.php";

            String urlParameters  = "correo="+userInfo.getCorreo();
            urlParameters += "&";
            urlParameters += "password="+userInfo.getPassword();

            String result = ServerConnection.sendHttpRequest(url, urlParameters);

            return result;

        } catch (IOException e) {
            return e.getMessage();
        }
    }
}
