package uoc.tddm.practica2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity
        implements NavigationView.OnNavigationItemSelectedListener,  ArticleFragment.OnListFragmentInteractionListener, AddFragment.OnFragmentInteractionListener{

    private ArrayList<Producto> listaProductos;
    public static final List<Producto> ITEMS = new ArrayList<Producto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //obtenemos el Arraylist de productos
        listaProductos = inicializarProductos();

        //Añadimos los productos a la lista de productos
        for(int p=0;p<listaProductos.size();p++) {
            ITEMS.add(listaProductos.get(p));
        }

        setContentView(R.layout.activity_main);

        //set title: Productos disponibles
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("APP Compra/Venta de Productos");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;


        if (id == R.id.nav_nuevo_producto) {
            //instanciamos el fragment para añadir producto
            fragment = new AddFragment();

        } else if (id == R.id.nav_productos) {
            //instanciamos el fragment para el listado de productos
            fragment = new ArticleFragment();

        } else if (id == R.id.nav_maps) {


        } else if (id == R.id.nav_log_out) {


        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ArrayList<Producto> inicializarProductos() {
        //accedemos al fichero json para obtener la lista de productos
        ArrayList<Producto> JsonProductos=new ArrayList<Producto>();
        String json = null;

        try {
            InputStream is = getAssets().open("listaproductos.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jProductos = jsonObject.getJSONArray("productos");
            for (int i = 0; i < jProductos.length(); i++) {
                JSONObject jProducto = jProductos.getJSONObject(i);
                String jnombre = jProducto.getString("nombre");
                String jprecio = jProducto.getString("precio");
                String jdescripcion=jProducto.getString("descripcion");
                String jimagen=jProducto.getString("imagen");
                Bitmap imagen= BitmapFactory.decodeStream(getAssets().open("images/"+jimagen));

                JsonProductos.add(new Producto(jnombre, jprecio, jdescripcion, imagen));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace(); }
        return JsonProductos;
        //devolvemos un arrayList de Productos
    }

    @Override
    public void onListFragmentInteraction(Producto item) {
        //Manejamos el evento onclick del fragment
        //mostramos el articulo seleccionado
        Toast.makeText(this,"Selecciona: "+item.getNombre(),Toast.LENGTH_SHORT).show();

        // Al hacer clic en el producto mostramos los detalles en otra actividad
        Intent intent = new Intent(this, ProductDetailActivity.class);
        //pasamos el objeto parcelable
        intent.putExtra("producto", item);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
