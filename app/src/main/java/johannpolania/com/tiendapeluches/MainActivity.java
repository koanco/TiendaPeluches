package johannpolania.com.tiendapeluches;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.opengl.Visibility;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int id=0;
    private boolean bandera=false;
    private Cursor c;

    private String nombre,cantidad,precio,identificacion;

private EditText eCantidad,eIdentificacion,ePrecio;
    private Button bRealizar;
    private AutoCompleteTextView eNombre;
    private RadioButton rActualizar,rEliminar,rAgregar;
    private RadioGroup grupo,grupo2;
private TextView tResultado;
    private ArrayList<String> listaArticulos;
    private ArrayAdapter<String> adaptador;
private TextView tMensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       rAgregar=(RadioButton)findViewById(R.id.rAgregar);
        eNombre=(AutoCompleteTextView)findViewById(R.id.eNombre);
        eCantidad=(EditText)findViewById(R.id.eCantidad);
        eIdentificacion=(EditText)findViewById(R.id.eIdentificacion);
       grupo=(RadioGroup)findViewById(R.id.rGrupo);
        grupo2=(RadioGroup)findViewById(R.id.rGrupo2);
        rActualizar=(RadioButton)findViewById(R.id.rActualizar);
        rEliminar=(RadioButton)findViewById(R.id.rEliminar);
        tMensaje=(TextView)findViewById(R.id.tMensaje);

        ePrecio=(EditText)findViewById(R.id.eValor);
        bRealizar=(Button)findViewById(R.id.bRealizar);



        cargarInventario(1, "IRON MAN", "10", "15000");
        cargarInventario(2, "VIUDA NEGRA", "10", "12000");
        cargarInventario(3, "CAPITAN AMERICA", "10", "15000");
        cargarInventario(4, "HULK", "10", "12000");
        cargarInventario(5, "BRUJA ESCARLATA", "10", "15000");
        cargarInventario(6, "SPIDERMAN", "10", "10000");

        bRealizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (grupo.getCheckedRadioButtonId()) {
                    case R.id.rAgregar:
                        guardar();
                        break;

                    case R.id.rConsultar:
                        buscarPeluche();
                        break;
                    case R.id.rVender:
                        venderPeluche();
                        break;


                }
                switch (grupo2.getCheckedRadioButtonId()) {
                    case R.id.rActualizar:
                        actualizar();
                        break;
                    case R.id.rEliminar:
                        Eliminar();
                        break;


                }


            }
        });
cargarListado();

    }


    public void cargarListado()

    {  listaArticulos=new ArrayList<String>();
        Conexion co=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase bd=co.getWritableDatabase();
        c=bd.rawQuery("select nombre from peluches",null);
        while(c.moveToNext())
        {
            listaArticulos.add(c.getString(0));


        }


        adaptador=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,listaArticulos);
        eNombre.setAdapter(adaptador);




    }


    public void activarGuardar(View view)

    {
        eNombre.setText("");
        eCantidad.setText("");
        ePrecio.setText("");
       eNombre.requestFocusFromTouch();
        eNombre.setVisibility(View.VISIBLE);
        eNombre.setEnabled(true);
        eNombre.setHint("Ingrese el nombre");
        eCantidad.setVisibility(View.VISIBLE);
        eCantidad.setEnabled(true);
        eCantidad.setHint("Ingrese la cantidad");
        ePrecio.setVisibility(View.VISIBLE);
        ePrecio.setEnabled(true);
        ePrecio.setHint("Ingrese el precio");
        bRealizar.setVisibility(View.VISIBLE);
        bRealizar.setText("Guardar");
        eIdentificacion.setVisibility(View.INVISIBLE);
        rEliminar.setVisibility(View.INVISIBLE);
        rActualizar.setVisibility(View.INVISIBLE);




    }



    public void guardar( )
    {
        Conexion peluche=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase bd=peluche.getWritableDatabase();
        String nombre=(eNombre.getText().toString()).toUpperCase();
        String cantidad=eCantidad.getText().toString();
        String precio=ePrecio.getText().toString();
  if(verificarDuplicado(nombre)!=true) {
      int id=consultarUltimoId();
         ContentValues registro = new ContentValues(); //Para guardar)

      registro.put("nombre", nombre);
      registro.put("cantidad", cantidad);
      registro.put("precio", precio);
      bd.insert("peluches", null, registro);
      bd.close();
      eNombre.setText("");
      eCantidad.setText("");
      ePrecio.setText("");
      Toast.makeText(this, "Se guardó el artículo en la bd", Toast.LENGTH_SHORT).show();
      reestablecer();
      cargarListado();
  }
        else
  {

      Toast.makeText(this, "Ya existe este artículo registrado en la base de datos", Toast.LENGTH_SHORT).show();

  }


    }


    public int consultarUltimoId()
    {
         int id=0;
        Conexion peluche=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase  bd=peluche.getWritableDatabase();


        c=bd.rawQuery("select id from peluches",null);
        if(c.moveToLast()==true)
        {

             id=c.getInt(0);

        }




        bd.close();


return id;
    }

    public boolean verificarDuplicado(String nombre)

    {
        boolean bandera=false;

        Conexion peluche=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase  bd=peluche.getWritableDatabase();


        c=bd.rawQuery("select *  from peluches  where nombre='"+nombre+"'",null);
        if(c.moveToFirst()==true)
        {
            bandera=true;


        }




        bd.close();

return bandera;

    }


public void cargarInventario(int id,String nombre,String  cantidad, String precio)
{

    Conexion peluche=new Conexion(this,"bdTienda",null,2);
    SQLiteDatabase bd=peluche.getWritableDatabase();


    ContentValues registro=new ContentValues(); //Para guardar)
    if(!verificarDuplicado(nombre)) {

        registro.put("id", id);
        registro.put("nombre", nombre);
        registro.put("cantidad", cantidad);
        registro.put("precio", precio);
        bd.insert("peluches", null, registro);
        bd.close();
    }
        //Toast.makeText(this, "Se guardaron los datos del peluche", Toast.LENGTH_SHORT).show();





}





    public void consultarInventario(View view)

    {
        String cadenaId="",cadenaNombre="",cadenaPrecio="",cadenaCantidad="";

        int posicion=0;
        Conexion peluche=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase  bd=peluche.getWritableDatabase();


        c=bd.rawQuery("select * from peluches",null);

        while(c.moveToNext())

        {
             cadenaId+=c.getString(0)+"\n";
            cadenaNombre+=c.getString(1)+"\n";
            cadenaCantidad+=c.getString(2)+"\n";
            cadenaPrecio+=c.getString(3)+"\n";

        }


         Intent i=new Intent(this,InventarioActivity.class);
        i.putExtra("id", cadenaId);
        i.putExtra("nombre", cadenaNombre);
        i.putExtra("cantidad", cadenaCantidad);
        i.putExtra("precio", cadenaPrecio);
        startActivity(i);





        bd.close();


reestablecer();


    }

    public void consultarGanancias(View view)
    {   String gananciaTotal="",descripcion="",valor_venta="",fecha="";
        String gFinal="";

        Conexion co=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase bd=co.getWritableDatabase();
       // co.onUpgrade(bd,1,1);
        c=bd.rawQuery("select ganancia_total,descripcion,valor_venta,fecha from ventas",null);
        while(c.moveToNext())
        {
        gananciaTotal+="\n"+c.getString(0);
        descripcion+="\n"+c.getString(1);
            valor_venta+="\n"+c.getString(2);
            fecha+="\n"+c.getString(3);


        }
        if(c.moveToLast())
        {

            gFinal=c.getString(0);

        }




        bd.close();
         Intent i= new Intent(this, GananciaActivity.class);
        i.putExtra("total",gananciaTotal);
        i.putExtra("descripcion",descripcion);
        i.putExtra("valor_venta",valor_venta);
        i.putExtra("fecha", fecha);
       // Toast.makeText(this, fecha, Toast.LENGTH_SHORT).show();
        i.putExtra("gFinal", gFinal);
        startActivity(i);
        reestablecer();

    }


    public void activarConsultar(View view)

    {   limpiarTexto();
        eNombre.setVisibility(View.VISIBLE);
        eNombre.setEnabled(true);

        eCantidad.setVisibility(View.INVISIBLE);
        ePrecio.setVisibility(View.INVISIBLE);
        eIdentificacion.setVisibility(View.INVISIBLE);
        bRealizar.setText("Buscar");
        bRealizar.setEnabled(true);
        bRealizar.setVisibility(View.VISIBLE);

        eNombre.requestFocusFromTouch();

    }



    public void buscarPeluche()

    {
        Conexion peluche=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase  bd=peluche.getWritableDatabase();

        nombre=eNombre.getText().toString().toUpperCase();
        c=bd.rawQuery("select * from peluches where nombre='"+nombre+"'",null);
        if(c.moveToFirst()==true)
        {

            eIdentificacion.setText("Id: "+c.getString(0));
            eNombre.setText(" Nombre: " + c.getString(1));
            eCantidad.setText("Cantidad: " + c.getString(2));
            ePrecio.setText("Precio: " + c.getString(3));
            eIdentificacion.setEnabled(false);
            eIdentificacion.setVisibility(View.VISIBLE);
            eNombre.setEnabled(false);
            eCantidad.setEnabled(false);
            eCantidad.setVisibility(View.VISIBLE);
            ePrecio.setEnabled(false);
            ePrecio.setVisibility(View.VISIBLE);
            rActualizar.setVisibility(View.VISIBLE);
            rEliminar.setVisibility(View.VISIBLE);
            this.identificacion=c.getString(0);
        //    this.nombre=c.getString(1).toUpperCase();
            this.cantidad=c.getString(2);
            this.precio=c.getString(3);
            grupo.clearCheck();
            grupo2.clearCheck();
            bRealizar.setVisibility(View.INVISIBLE);
            Toast.makeText(this,"El artículo se encuentra en la BD",Toast.LENGTH_SHORT).show();
        }
        else
        {

            Toast.makeText(this,"No existe en la BD :(",Toast.LENGTH_SHORT).show();

        }



        bd.close();




    }


    public void activarEliminacion(View view)
    {bRealizar.setVisibility(View.VISIBLE);
        bRealizar.requestFocusFromTouch();
        bRealizar.setText("Confirmar Eliminacion");


    }


    public void Eliminar()

    {

        Conexion peluches=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase  bd=peluches.getWritableDatabase();
        int cant = bd.delete("peluches", "nombre='" + this.nombre + "'", null);
        bd.close();
       reestablecer();

        if(cant==1)
        {
            Toast.makeText(this,"Artículo eliminado de la BD",Toast.LENGTH_SHORT).show();
            cargarListado();

        }
        else
        {
            Toast.makeText(this,"No se pudo eliminar el artículo de la BD",Toast.LENGTH_SHORT).show();

        }




    }


    public void activarActualizar(View view)
    {
        eCantidad.setEnabled(true);
        eCantidad.setText(this.cantidad);
        eCantidad.requestFocusFromTouch();

        eCantidad.setCursorVisible(true);
        //eCantidad.setActivated(true);
        eCantidad.setPressed(true);
        //eCantidad.setClickable(true);

        bRealizar.setVisibility(View.VISIBLE);
        bRealizar.setText("Confirmar modificación");
        Toast.makeText(this,"Modifique la cantidad del artículo",Toast.LENGTH_SHORT).show();


    }


    public void actualizar()
    {
        String cantidad;
        cantidad=eCantidad.getText().toString();
        Conexion peluches=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase  bd=peluches.getWritableDatabase();

        ContentValues registro=new ContentValues();
        registro.put("cantidad", cantidad);


        int cant=bd.update("peluches",registro,"nombre='"+this.nombre+"'",null);

        bd.close();
        reestablecer();
        if(cant==1)
        {
            Toast.makeText(this,"Se modificó la cantidad del artículo",Toast.LENGTH_SHORT).show();

        }
        else
        {

            Toast.makeText(this,"No fue posible modificar la cantidad del artículo ",Toast.LENGTH_SHORT).show();

        }





    }


    public void reestablecer()
    {   limpiarTexto();
        eIdentificacion.setVisibility(View.INVISIBLE);
        eNombre.setVisibility(View.INVISIBLE);
        eCantidad.setVisibility(View.INVISIBLE);
        ePrecio.setVisibility(View.INVISIBLE);
        rEliminar.setVisibility(View.INVISIBLE);
        rActualizar.setVisibility(View.INVISIBLE);
        bRealizar.setVisibility(View.INVISIBLE);
        grupo.clearCheck();
        grupo2.clearCheck();
        tMensaje.didTouchFocusSelect();
        tMensaje.setFocusableInTouchMode(true);
        tMensaje.requestFocusFromTouch();







    }

    public void limpiarTexto()
    {

        eIdentificacion.setText("");
        eNombre.setText("");
        ePrecio.setText("");
        eCantidad.setText("");

    }

    public void activarVender(View view)
    {
        eNombre.setVisibility(View.VISIBLE);
        eNombre.setEnabled(true);
        eNombre.requestFocusFromTouch();
        eNombre.setHint("Ingrese el nombre del artículo");
        eCantidad.setVisibility(View.VISIBLE);
        eCantidad.setEnabled(true);
        eCantidad.setHint("Cantidad a vender");
        bRealizar.setVisibility(View.VISIBLE);
        bRealizar.setText("Confirmar venta");
        limpiarTexto();
        eIdentificacion.setVisibility(View.INVISIBLE);
        ePrecio.setVisibility(View.INVISIBLE);
         rActualizar.setVisibility(View.INVISIBLE);
        rEliminar.setVisibility(View.INVISIBLE);


    }


    public void venderPeluche()

    {
        int gananciaVenta=0;
        int restantes=0;
        String descripcion="";
        Conexion peluche=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase  bd=peluche.getWritableDatabase();

        String nombre=eNombre.getText().toString().toUpperCase();
        String cantidad=eCantidad.getText().toString();
        c=bd.rawQuery("select cantidad, precio from peluches where nombre='" + nombre + "'", null);
        if(c.moveToFirst()==true)
        {
            if(c.getInt(0)>=Integer.parseInt(cantidad))
            {   descripcion=cantidad+" "+nombre;
                restantes=c.getInt(0)-Integer.parseInt(cantidad);
                if(restantes<=5)
                {
                    notificar(restantes,nombre);


                }
                ContentValues registro=new ContentValues();
                registro.put("cantidad", restantes);
                int cant=bd.update("peluches",registro,"nombre='"+nombre+"'",null);
                gananciaVenta=(c.getInt(1))*Integer.parseInt(cantidad);
                insertarGananacia(gananciaVenta,descripcion);
                Toast.makeText(this,"Se ha vendido "+cantidad+" " +nombre ,Toast.LENGTH_SHORT).show();
                Toast.makeText(this,"La venta total es: "+String.valueOf(gananciaVenta),Toast.LENGTH_LONG).show();

                reestablecer();
            }
            else
            {
                Toast.makeText(this,"No se puede vender esta cantidad, solo quedan: "+ String.valueOf(c.getInt(0)) +" " +nombre,Toast.LENGTH_LONG).show();


            }


        }
        else
        {

            Toast.makeText(this,"No existe  el artículo en la bd",Toast.LENGTH_SHORT).show();

        }


        bd.close();







    }


    public void insertarGananacia(int gananciaVenta,String descripcion)
    {    int gananciaAcumulada=0;
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Conexion co=new Conexion(this,"bdTienda",null,2);
        SQLiteDatabase bd=co.getWritableDatabase();
        c=bd.rawQuery("select ganancia_total from ventas", null);





        if(c.moveToLast())
        {
            gananciaAcumulada=c.getInt(0);



        }
        gananciaAcumulada+=gananciaVenta;
        ContentValues registro=new ContentValues();
        registro.put("ganancia_total",gananciaAcumulada);
        registro.put("fecha",mydate);
        registro.put("descripcion", descripcion);
        registro.put("valor_venta", gananciaVenta);
        bd.insert("ventas", null, registro);
     //   Toast.makeText(this, "Ganancia acumulada"+String.valueOf(gananciaAcumulada), Toast.LENGTH_SHORT).show();




        bd.close();





    }

    public void notificar(int restante,String articulo)
    {

        NotificationManager nManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentTitle("¡Atención:Quedan pocas unidades del producto!");
        builder.setContentText("Solo quedan: " + String.valueOf(restante) + " " + articulo);
        builder.setSmallIcon(R.drawable.warning);
Intent i=new Intent(MainActivity.this,Notificacion.class);
        i.putExtra("articulo",articulo);
        i.putExtra("cantidad",String.valueOf(restante));
        PendingIntent conIntent=PendingIntent.getActivity(MainActivity.this,0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(conIntent);


        nManager.notify(1234,builder.build());







    }




}


