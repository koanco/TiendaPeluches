package johannpolania.com.tiendapeluches;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GananciaActivity extends AppCompatActivity {
private TextView tGanancia, tVenta, tDescripcion,tFecha,tFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganancia);
        tGanancia=(TextView)findViewById(R.id.tGananciaTotal);
        tVenta=(TextView)findViewById(R.id.tVenta);
        tDescripcion=(TextView)findViewById(R.id.tDescripcion);
        tFecha=(TextView)findViewById(R.id.tFecha);
        tFinal=(TextView)findViewById(R.id.tGananciaFinal);
        Bundle extra=getIntent().getExtras();
        tGanancia.setText("GActual "+"\n"+extra.getString("total"));
        tDescripcion.setText("Descripcion"+"\n"+extra.getString("descripcion"));
        tVenta.setText("Valor venta"+"\n"+extra.getString("valor_venta"));
        tFecha.setText("Fecha"+"\n"+extra.getString("fecha"));
        tFinal.setText("La ganancia actual es de: "+extra.getString("gFinal"));


    }

    public void acabar(View view)

    {


        finish();

    }

}
