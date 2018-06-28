package com.adrian_971029.ichef.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recetas implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private ArrayList<Ingredientes> ingredients;
    @SerializedName("steps")
    private ArrayList<Passos> steps;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String image;

    public Recetas() {
    }

    protected Recetas(Parcel in) {
        id = in.readInt();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
    }

    public static final Creator<Recetas> CREATOR = new Creator<Recetas>() {
        @Override
        public Recetas createFromParcel(Parcel in) {
            return new Recetas(in);
        }

        @Override
        public Recetas[] newArray(int size) {
            return new Recetas[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredientes> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredientes> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Passos> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Passos> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
    }
}
