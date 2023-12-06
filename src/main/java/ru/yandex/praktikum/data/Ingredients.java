package ru.yandex.praktikum.data;

import java.util.ArrayList;

public class Ingredients {

    private ArrayList<String> ingredients;

    public Ingredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public Ingredients() {
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void clearIngredients(){
        ingredients.clear();

    }
    public void addIngredients(String ingredient){
        ingredients.add(ingredient);

    }
}
