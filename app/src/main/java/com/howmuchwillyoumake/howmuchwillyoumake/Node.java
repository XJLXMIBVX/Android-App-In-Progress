package com.howmuchwillyoumake.howmuchwillyoumake;

import static android.R.attr.id;

/**
 * Created by OJ on 8/30/17.
 */


public class Node {
    public String title_input = "";
    private boolean add = false;
    private boolean sub = false;
    private int num_of_units = 0;
    private double amount_per_unit = 0.0;
    private String unit = "";
    private double year_duration = 0;
//    private int id = 1;

    public Node() {
        title_input = "";
        add = false;
        sub = false;
        amount_per_unit = 0.0;
        unit = "";
        year_duration = 0;
//        id = 1;
    }

    public Node(String title, boolean a, boolean s, double amount, int num_units, String the_unit, double num_years) {
        title_input = title;
        add = a;
        sub = s;
        amount_per_unit = amount;
        num_of_units = num_units;
        unit = the_unit;
        year_duration = num_years;
//        id = id_num;
    }

    public String getTitleInput() {
        return title_input;
    }

    public boolean getAddCond() {
        return add;
    }

    public boolean getSubCond() {
        return sub;
    }

    public String getOperation() {
        if(add && !sub) {
            return "+";
        }
        else if(sub && !add) {
            return "-";
        }
        return "";
    }

    public double getAmountPerUnit() {
        return amount_per_unit;
    }

    public int getNumOfUnits() {
        return num_of_units;
    }

    public String getUnit() {
        return unit;
    }

    public double getNumYears() {
        return year_duration;
    }

    public double getId() {
        return id;
    }

    public void setNumOfYears(double num) {
        year_duration = num;
    }

    public String toString() {
        return title_input + "\n" + add  + "\n" + sub  + "\n" + amount_per_unit + "\n" + num_of_units + "\n" +unit + "\n" + year_duration + "\n";
    }



    public boolean compareTo(Object obj) {

        if(!(obj instanceof Node)) {
            return false;
        }

        Node node = (Node) obj;

        // NO DUPLICATES CATEGORIES !!
        if( (this.getTitleInput()).equals(node.getTitleInput()) ) {
            return true;
        }
        return false;
    }
}
