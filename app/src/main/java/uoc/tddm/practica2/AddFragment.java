package uoc.tddm.practica2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View mView;
    EditText metNombre;
    EditText metDescrip;
    EditText metPrecio;
    ImageView mimageFoto;
    EditText metCoordenadas;
    Button mbtnFoto;
    Button mbtnAdd;

    //cliente de servicios de ubicacion
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;


    private OnFragmentInteractionListener mListener;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add, container, false);

        //obtenenos referencias
        metNombre = mView.findViewById(R.id.nameProd);
        metDescrip = mView.findViewById(R.id.descProd);
        metPrecio = mView.findViewById(R.id.precProd);
        mimageFoto = mView.findViewById(R.id.imageProd);
        metCoordenadas = mView.findViewById(R.id.coordProd);

        //obtenemos localización
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext() );

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000);
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setNumUpdates(1);

        //gestionamos permisos
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());

        /******* FALTA CODIGO GESTIONAR PERMISOS */


        //* codigo hacer foto
        mbtnFoto = mView.findViewById(R.id.btnFoto);
        mbtnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //activar camara para tomar foto
                dispatchTakePictureIntent();
            }
        });

        //* codigo añadir producto
        mbtnAdd = mView.findViewById(R.id.btnAdd);
        mbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //obtenemos los valores introducidos por el usuario
                String nombre = metNombre.getText().toString();
                String precio = metPrecio.getText().toString();
                String descrip = metDescrip.getText().toString();
                Bitmap image = ((BitmapDrawable)mimageFoto.getDrawable()).getBitmap();
                //obtenemos las coordenadas
                //pasamos latitud y longitud al Producto a añadir


                //creamos el producto y se lo pasamos al metodo onButtonPressed
                onButtonPressed(new Producto(nombre,precio,descrip,image,0.0, 0.0));
            }
        });


        return mView;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mimageFoto.setImageBitmap(imageBitmap);
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Producto item) {
        if (mListener != null) {
            mListener.onFragmentInteraction(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Producto item);
    }
}
