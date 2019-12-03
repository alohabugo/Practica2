package uoc.tddm.practica2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductDetailActivity extends AppCompatActivity {
    Producto producto;
    TextView nombre, precio, desc;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        setTitle("Detalle de producto");

        //referencias a los componentes
        nombre=(TextView)findViewById(R.id.nombreProd);
        precio=(TextView)findViewById(R.id.precioProd);
        desc=(TextView)findViewById(R.id.descriProd);
        imagen=(ImageView) findViewById(R.id.imagenProd);

        //obtenemos el producto seleccionado
        producto = getIntent().getParcelableExtra("producto");

        if(producto!= null) {
            //mostramos el articulo seleccionado
            nombre.setText(producto.getNombre());
            precio.setText(producto.getPrecio());
            desc.setText(producto.getDescripcion());
            imagen.setImageBitmap(producto.getImagen());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finalizamos esta actividad
        this.finish();
    }
}
