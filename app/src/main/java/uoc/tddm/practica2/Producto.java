package uoc.tddm.practica2;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;


public class Producto implements Parcelable {

    private String nombre;
    private String precio;
    private String descripcion;
    private Bitmap imagen;

    /* No args constructor for use in serialization */
    public Producto(){
    }

    /*
     * @param nombre
     * @param imagen
     * @param precio
     * @param descripcion
     */

    public Producto (String nombre, String precio, String descripcion, Bitmap imagen) {
        super();
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    //constructor parcelable
    protected Producto(Parcel in) {
        nombre = in.readString();
        precio = in.readString();
        descripcion = in.readString();
        imagen = in.readParcelable(Bitmap.class.getClassLoader());
    }

    //crea el objeto parcelable y lo pasa al constructor
    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(precio);
        dest.writeString(descripcion);
        dest.writeParcelable(imagen, flags);
    }
}
