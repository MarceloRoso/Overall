package a.buitress.overall;
import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.*;
import android.database.Cursor;

/**
 * Created by Marce on 29/11/2015.
 */
public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "overall.db";
    public static final String TABLE_NAME  = "usuarios_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "CORREO";
    public static final String COL_3 = "NOMBRE";
    public static final String COL_4 = "TELEFONO";
    public static final String COL_5 = "CONTRASEÑA";
    public static final String COL_6 = "NUM_TARJETA";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Cursor resultado = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME+"'", null);

        if(!resultado.moveToFirst()) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_2 + " TEXT UNIQUE NOT NULL, " +
                    COL_3 + " TEXT NOT NULL, " +
                    COL_4 + " TEXT NOT NULL, " +
                    COL_5 + " TEXT NOT NULL, " +
                    COL_6 + " TEXT NOT NULL)");
        }
        resultado.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean registrarUsuario(String correo, String nombre, String telefono, String contraseña, String numTarjeta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues registroUsuario = new ContentValues();

        registroUsuario.put(COL_2, correo);
        registroUsuario.put(COL_3, nombre);
        registroUsuario.put(COL_4, telefono);
        registroUsuario.put(COL_5, contraseña);
        registroUsuario.put(COL_6, numTarjeta);

        if (db.insert(TABLE_NAME, null, registroUsuario) != -1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean inicioSesion(String correo, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COL_2, COL_5};
        String selection = COL_2 + "= ? AND "+ COL_5 + " = ?";
        String[] arguments = {correo, contraseña};

        Cursor resultado = db.query(TABLE_NAME, columns, selection, arguments, null, null, null);

        return resultado.moveToFirst();
    }
}
