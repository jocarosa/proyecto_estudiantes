package com.example.jrosario.studientdb.studentThings;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jrosario.studientdb.R;
import com.example.jrosario.studientdb.databaseThings.Library;


import java.util.ArrayList;
import java.util.List;

public class listAdapter extends ArrayAdapter<Estudiante> {

    Context context;
    List<Estudiante> lista;
    ArrayList<Estudiante> newLista;
    LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;
    private Favoritos favoritos;
    Library l;
    private String textSize;

    public listAdapter(Context context, int resourceId, ArrayList<Estudiante> ListEstudiante) {
        super(context,resourceId,ListEstudiante);
        this.context  = context;
        this.lista    = ListEstudiante;
        this.newLista = new ArrayList<>();
        this.newLista.addAll(ListEstudiante);
        mSelectedItemsIds = new SparseBooleanArray();
        this.inflater = LayoutInflater.from(context);
        favoritos= new Favoritos(getContext());


    }

    private class ViewHolder {
        TextView Nombres;
        TextView Materia;
        TextView Calificacion;
        ImageView Imagen;
        ImageView ImagenFavorite;


    }

    public View getView(int posicion, View view, ViewGroup viewGroup) {

        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.listview_row, null);

            holder.Nombres          = (TextView) view.findViewById(R.id.textView);
            holder.Materia          = (TextView) view.findViewById(R.id.textView2);
            holder.Calificacion     = (TextView) view.findViewById(R.id.textView3);
            holder.Imagen           =(ImageView)view.findViewById(R.id.imageView);
            holder.ImagenFavorite   = (ImageView)view.findViewById(R.id.favoriteIcon);
            view.setTag(holder);

        }
        else{
            holder = (ViewHolder) view.getTag();
        }

        holder.Nombres.setText(this.lista.get(posicion).getNombres() + " " + this.lista.get(posicion).getApellidos());
        holder.Materia.setText(this.lista.get(posicion).getMateria());
        holder.Calificacion.setTextColor((this.lista.get(posicion).getCalificacion() < 70)?Color.RED:Color.BLACK);
        holder.Calificacion.setText(Integer.toString(this.lista.get(posicion).getCalificacion()));

        if(this.textSize!=null){
            holder.Nombres.setTextSize(Float.parseFloat(this.textSize));
            holder.Materia.setTextSize(Float.parseFloat(this.textSize));
            holder.Calificacion.setTextSize(Float.parseFloat(this.textSize));
        }

        //setting favorite
        final int position = posicion;
        holder.ImagenFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Estudiante favEst =  lista.get(position);
               favEst.setFavorite(!favEst.getFavorite());
               System.out.println(favEst.getId());
                l = new Library(getContext());
                l.updateSelectedStudent(favEst);
                notifyDataSetChanged();
            }
        });

        holder.ImagenFavorite.setImageResource(
                lista.get(posicion).getFavorite()? R.drawable.favorito_user:R.drawable.no_favorite
        );
        holder.Imagen.setImageResource(
               lista.get(posicion).getFavorite()? R.drawable.persona_start:R.drawable.persona2
        );

        return view;
    }

    @Override
    public void remove(Estudiante object) {
        lista.remove(object);
        notifyDataSetChanged();
    }
    public void setTextSize(String textSize){
        this.textSize = textSize;
        notifyDataSetChanged();
    }
    public void filter(String filter) {

        this.lista.clear();
        for (Estudiante element : this.newLista){
            if(element.getNombres().startsWith(filter)
                    || (""+element.getCalificacion()).startsWith(filter)
                    || element.getMateria().startsWith(filter)
            )this.lista.add(element);
        }
        super.notifyDataSetChanged();
    }

    @Override
    public void add(Estudiante object) {
        lista.add(object);
        notifyDataSetChanged();
    }

    public List<Estudiante> getWorldPopulation() {
        return lista;
    }

    public void RowSelecionado(int position) {
        selectView(position, !mSelectedItemsIds.get(position));

    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);

        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

}
