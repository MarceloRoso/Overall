package a.buitress.overall;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class registro extends AppCompatActivity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        findViewById(R.id.buttonGuardar).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registro.this, iniciosesion.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.buttonGuardar) {
            TextView mensaje = (TextView) findViewById(R.id.viewMensaje);
            EditText nombre = (EditText) findViewById(R.id.nombre_campo);
            EditText correo = (EditText) findViewById(R.id.correo_campo);
            EditText contraseña = (EditText) findViewById(R.id.contraseña_campo);
            EditText telefono = (EditText) findViewById(R.id.telefono_campo);

            RegistrarUsuarioTask registro = new RegistrarUsuarioTask();
            registro.execute(correo.getText().toString(), nombre.getText().toString(), contraseña.getText().toString(), telefono.getText().toString());
            try {
                mensaje.setText(registro.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
