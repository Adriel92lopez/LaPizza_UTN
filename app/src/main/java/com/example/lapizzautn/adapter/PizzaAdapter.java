package com.example.lapizzautn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lapizzautn.R;
import com.example.lapizzautn.entidad.Productos;

import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.PizzaViewHolder> {

    private Context context;
    private List<Productos> listaPizzas;

    // Constructor
    public PizzaAdapter(Context context, List<Productos> listaPizzas) {
        this.context = context;
        this.listaPizzas = listaPizzas;
    }

    @NonNull
    @Override
    public PizzaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_item_pizza, parent, false);
        return new PizzaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PizzaViewHolder holder, int position) {
        Productos pizza = listaPizzas.get(position);
        holder.txtNombre.setText(pizza.getNombre());
        holder.txtPrecio.setText("Precio: $" + pizza.getPrecio());
        holder.txtDescripcion.setText(pizza.getDescripcion());

        // Aquí puedes asignar una imagen si la tienes o cargarla desde la base de datos.
        holder.imgPizza.setImageResource(R.drawable.pizza);
    }

    @Override
    public int getItemCount() {
        return listaPizzas.size();
    }

    public static class PizzaViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombre, txtPrecio, txtDescripcion;
        ImageView imgPizza;

        public PizzaViewHolder(View itemView) {
            super(itemView);
            txtNombre = itemView.findViewById(R.id.txtNombre);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            imgPizza = itemView.findViewById(R.id.imgPizza);
        }
    }
}

