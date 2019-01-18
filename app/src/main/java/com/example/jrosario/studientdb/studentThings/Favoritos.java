package com.example.jrosario.studientdb.studentThings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Favoritos {

    public List<Integer> ListFavorito = new ArrayList<Integer>();
    private Context context;
    
    public Favoritos(Context context)
	{
    	this.context = context;
    }
    
    public void loadFavoritos(String Colorid) {
		ListFavorito.clear();
	
	SharedPreferences prefs = context.getSharedPreferences("Favoritos", 0);
	int ctr = 0;
	String key = "favorito" + ctr;
	while (prefs.contains(key)) {
	    int verseId = prefs.getInt(key, 0);
	    if (verseId != 0) 
	    {	
	    	String valor = String.valueOf(verseId);
	    	String ValorColor=valor.substring(0, 2);
	    	if(ValorColor.equalsIgnoreCase(Colorid))
				ListFavorito.add(verseId);
	    }
	    
	    ctr++;
	    key = "favorito" + ctr;
	}
	
	Collections.sort(ListFavorito);
    }
    
    public void saveFavoritos() {
		SharedPreferences prefs = context.getSharedPreferences("Favoritos", 0);
		Editor editor = prefs.edit();
		editor.clear();

		for (int i=0; i<ListFavorito.size(); i++) {
			editor.putInt("favorito" + i, ListFavorito.get(i));
		}
		editor.commit();
    }
    
    public void addFavoritos(final Integer verseId) {
		loadFavoritos();

		if (!ListFavorito.contains(verseId)) {
			ListFavorito.add(verseId);
			Collections.sort(ListFavorito);
			saveFavoritos();
		}
    }
    
    public void removeFavoritos(final Integer IdFavorito) {
	loadFavoritos();

		if (ListFavorito.contains(IdFavorito)) {
			ListFavorito.remove(IdFavorito);
			Collections.sort(ListFavorito);
			saveFavoritos();
		}

    }

    public void loadFavoritos() {
    	// Remove old bookmarks previously loaded
		ListFavorito.clear();
    	
    	SharedPreferences prefs = context.getSharedPreferences("Favoritos", 0);
    	int ctr = 0;
    	String key = "favorito" + ctr;
    	while (prefs.contains(key)) {
    	    int favorito = prefs.getInt(key, 0);
    	    if (favorito != 0)
    	    {
				ListFavorito.add(favorito);
    	    }
    	    
    	    ctr++;
    	    key = "favorito" + ctr;
    	}
    	
    	Collections.sort(ListFavorito);
        }

    public int GetIDFavoritos(int idfavorito) {
    	// Remove old bookmarks previously loaded
		ListFavorito.clear();
    	int Valor =0;
    	
    	SharedPreferences prefs = context.getSharedPreferences("Favoritos", 0);
    	int ctr = 0;
    	String key = "favorito" + ctr;
    	while (prefs.contains(key)) {
    	    int favorito = prefs.getInt(key, 0);
    	    if (favorito != 0)
    	    {	

    	    	if(idfavorito == favorito )
    	    	{
    	    		Valor = favorito;
    	    	}
    	    }
    	    
    	    ctr++;
    	    key = "favorito" + ctr;
    	}
    	
    	Collections.sort(ListFavorito);
		return Valor;
        
    }
    
}
