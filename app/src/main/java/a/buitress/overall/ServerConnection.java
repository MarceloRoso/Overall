package a.buitress.overall;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Marce on 29/12/2015.
 */
public class ServerConnection {

    public static String sendHttpRequest(String url, String requestParams) throws IOException {
        HttpURLConnection UrlConnection = (HttpURLConnection) new URL(url).openConnection();

        UrlConnection.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter( UrlConnection.getOutputStream());
        wr.write(requestParams);
        wr.flush();


        InputStreamReader isr = new InputStreamReader(UrlConnection.getInputStream());
        BufferedReader buffReader = new BufferedReader(isr);
        StringBuilder sBuilder= new StringBuilder();
        String s;
        while((s = buffReader.readLine()) != null) {
            sBuilder.append(s);
        }

        return sBuilder.toString();
    }
}
