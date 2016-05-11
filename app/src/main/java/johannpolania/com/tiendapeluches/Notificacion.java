package johannpolania.com.tiendapeluches;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Notificacion extends AppCompatActivity {

    private TextView tNotificacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        Bundle extras=getIntent().getExtras();
        tNotificacion=(TextView)findViewById(R.id.tNotificacion);
        tNotificacion.setText("Solo queda:"+extras.getString("cantidad")+" "+extras.getString("articulo"));
    }


    public void regresar(View view)
    {
        //Intent i=new Intent(this,MainActivity.class);
        //startActivity(i);

        finish();


    }
}
