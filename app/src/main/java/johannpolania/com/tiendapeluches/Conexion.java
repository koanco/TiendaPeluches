package johannpolania.com.tiendapeluches;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by koanco on 4/05/2016.
 */
public class Conexion extends SQLiteOpenHelper {
    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override


    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table peluches(id integer primary key , nombre text,cantidad integer,precio integer)");
        db.execSQL("create table ventas(ganancia_total integer, descripcion text,valor_venta integer,fecha text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists peluches");
        db.execSQL("drop table if exists ventas");
        db.execSQL("create table peluches(id integer primary key , nombre text,cantidad integer,precio integer)");
        db.execSQL("create table ventas(ganancia_total integer, descripcion text,valor_venta integer,fecha text)");
    }
}
