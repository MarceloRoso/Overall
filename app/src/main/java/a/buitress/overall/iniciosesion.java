package a.buitress.overall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class iniciosesion extends AppCompatActivity implements LoginListener{

    public static String _correoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciosesion);
        findViewById(R.id.button6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(iniciosesion.this, registro.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iniciosesion, menu);
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


    public void iniciarSesion(View v) throws ExecutionException, InterruptedException {
        EditText correo = (EditText)findViewById(R.id.correo2);
        EditText contraseña = (EditText)findViewById(R.id.contraseña2);

        LoginTask inicio = new LoginTask(this);

        inicio.execute(new LoginInfo(correo.getText().toString(), contraseña.getText().toString()));
    }

    @Override
    public void onLoginSuccesful() {
        startActivity(new Intent(iniciosesion.this, nextlogin.class));
        EditText correo = (EditText)findViewById(R.id.correo2);
        _correoLogin = correo.getText().toString();
    }

    public void toastMessage(String message) {
        Toast.makeText(iniciosesion.this, message, Toast.LENGTH_LONG).show();
    }
}
