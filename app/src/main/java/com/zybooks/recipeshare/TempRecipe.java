package com.zybooks.recipeshare;

public class TempRecipe {

    private String Name;
    private String Description;
    private  String Steps;
    private int Id;

    public TempRecipe(){

    }

    public TempRecipe(int id, String name){
        Id = id;
        Name = name;
    }

    public TempRecipe(int id, String name, String description, String steps) {
        Id = id;
        Name = name;
        Description = description;
        Steps = steps;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setSteps(String steps) {
        Steps = steps;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName(){ return Name; }
    public String getSteps(){ return Steps; }
    public String getDescription(){ return Description; }

    public int getId(){ return Id; }
}
