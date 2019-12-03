package uoc.tddm.practica2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import uoc.tddm.practica2.ArticleFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Producto} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyArticleRecyclerViewAdapter extends RecyclerView.Adapter<MyArticleRecyclerViewAdapter.ViewHolder> {

    private final List<Producto> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyArticleRecyclerViewAdapter(List<Producto> items, OnListFragmentInteractionListener listener) { //
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_article, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mImageView.setImageBitmap(mValues.get(position).getImagen());
        holder.mNombreView.setText(mValues.get(position).getNombre());
        holder.mPrecioView.setText(mValues.get(position).getPrecio());
        //mostramos los valores del producto en sus componetes visuales

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                    //al hacer clic pasamos el Producto seleccionado
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mNombreView;
        public final TextView mPrecioView;
        public Producto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.item_image);
            mNombreView = (TextView) view.findViewById(R.id.item_nombre);
            mPrecioView = (TextView) view.findViewById(R.id.item_precio);
            //obtenemos las referencias de los componentes
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNombreView.getText() + "'";
        }
    }
}
