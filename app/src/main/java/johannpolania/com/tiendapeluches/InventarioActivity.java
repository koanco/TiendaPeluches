package johannpolania.com.tiendapeluches;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class InventarioActivity extends AppCompatActivity {
private TextView tNombre,tCantidad,tPrecio,tIdentificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);
tNombre=(TextView)findViewById(R.id.tNombre);
        tCantidad=(TextView)findViewById(R.id.tCantidad);
        tPrecio=(TextView)findViewById(R.id.tPrecio);
        tIdentificacion=(TextView)findViewById(R.id.tIdentificacion);




        Bundle extras=getIntent().getExtras();
        tNombre.setText("Nombre: "+"\n"+extras.getString("nombre"));
        tIdentificacion.setText("Id:"+"\n"+extras.getString("id"));
        tCantidad.setText("Cantidad"+"\n"+extras.getString("cantidad"));
        tPrecio.setText("Precio"+"\n"+extras.getString("precio"));
    }

    public void regresar(View view)
    {
        finish();


    }
}
