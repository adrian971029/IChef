package com.adrian_971029.ichef.io.response;

import com.adrian_971029.ichef.model.Recetas;

import java.util.ArrayList;

public class RecetasResponse {
    private ArrayList<Recetas> recetas;

    public ArrayList<Recetas> getRecetas() {
        return recetas;
    }

    public void setRecetas(ArrayList<Recetas> recetas) {
        this.recetas = recetas;
    }
}
