package com.example.demogson;

import java.util.ArrayList;

class Person {
    String name;
    int age;
    boolean male;
    Data data;
    ArrayList<String> favorites;

    public Person(String name, int age, boolean male, Data data, ArrayList<String> favorites) {
        this.name = name;
        this.age = age;
        this.male = male;
        this.data = data;
        this.favorites = favorites;

    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getAge(){
        return age;
    }
    public void setAge(int age){
        this.age = age;
    }

    public boolean isMale(){
        return male;
    }
    public void setMale(boolean male){
        this.male = male;
    }

    public Data getData(){
        return data;
    }
    public void setData(Data data){
        this.data = data;
    }

    public ArrayList<String> getFavorites(){
        return favorites;
    }
    public void setFavorites(ArrayList<String> favorites){
       this.favorites = favorites;
    }
}
