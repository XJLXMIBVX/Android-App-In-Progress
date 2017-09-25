package com.howmuchwillyoumake.howmuchwillyoumake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

import static com.howmuchwillyoumake.howmuchwillyoumake.Input.hash;

public class NumYearsPrompt extends AppCompatActivity {

    public static double total;
    public static String num_of_years_final;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_years_prompt);


        // VIEW ALL ENTRIES BUTTON
        Button viewAllEntriesButton = (Button)findViewById(R.id.view_all_entries_button_numyearsprompt);
        viewAllEntriesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(NumYearsPrompt.this, EntryList.class)); // REDIRECT TO BEGIN BUTTON
            }
        });

        // START OVER BUTTON
        Button start_over_button = (Button) findViewById(R.id.start_over_from_button_numyearsprompt);
        start_over_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hash.clear(); // CLEAR HASH hash = new HashMap<>();
                Input.resetValues(); // RESET VARIABLES (title, operation, amount per unit, etc.)
                startActivity(new Intent(NumYearsPrompt.this, MainActivity.class)); // REDIRECT TO BEGIN BUTTON
            }
        });

        // ADD ANOTHER ENTRY BUTTON
        Button add_entry_button = (Button) findViewById(R.id.add_entry_button_numyearsprompt);
        add_entry_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Input.resetValues();
                startActivity(new Intent(NumYearsPrompt.this, Input.class));
            }
        });

        // CALCULATE BUTTON
        Button calculate_button = (Button) findViewById(R.id.calculate_button_numyearsprompt);
        calculate_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                total = 1;
                EditText total_years_ed = (EditText) findViewById(R.id.num_of_years_ed);
                String total_years_str = total_years_ed.getText().toString().trim();

                // IF THE EDITTEXT FIELD IS EMPTY
                if(total_years_str.isEmpty() || total_years_str.length() == 0 || total_years_str.equals("") || total_years_str == null) {

                    // DISPLAY ERROR MESSAGE
                    Input.setErrorMessage("Please fill in the blank!");
                    startActivity(new Intent(NumYearsPrompt.this, Pop.class));
                }

                // IF THE EDITTEXT FIELD IS NOT EMPTY
                else {
                    double total_years = Double.parseDouble(total_years_ed.getText().toString());
                    double curr_entry_year;

                    // GO THROUGH ALL ENTRIES IN HASH
                    for(Map.Entry<Integer, Node> entry : hash.entrySet()) {

                        Node currNode = entry.getValue();
                        curr_entry_year = currNode.getNumYears();

                        // IF TOTAL YEARS < CURRENT ENTRY NUM OF YEARS
                        if(total_years < curr_entry_year) {
                            // Set the current Node's number of years to the total_years
                            currNode.setNumOfYears(total_years);
                        }

                        // FOR each entry, call calculateOne(), & add that to accumulating total
                        total += calculateOne(currNode);
                    }

                    // Set output text to the total
                    startActivity(new Intent(NumYearsPrompt.this, Output.class)); // REDIRECT TO THE RESULTS/OUTPUT
                }
            }
        });

    }


    public double calculateOne(Node node) {
        EditText num_years_ed = (EditText) findViewById(R.id.num_of_years_ed);
        num_of_years_final = num_years_ed.getText().toString();
        total = 1;

        // ADD OR SUB (1 or -1)
        // DEFAULT IS ADD, IF NOT, MULTIPLY BY -1
        if(!node.getAddCond()) {
            total *= (-1);
        }
        Log.i("After OPERATION:", Double.toString(total));

        // * AMOUNT
        // total *= Input.amount_per_unit;
        total *= node.getAmountPerUnit();
        Log.i("After AMOUNT PER UNIT:", Double.toString(total));

        // * NUM OF UNITS
        // total *= Input.num_of_units;
        total *= node.getNumOfUnits();
        Log.i("After NUM OF UNITS:", Double.toString(total));

        // IF UNIT == "Hours/Day (5 days per week)"
        // String unit = Input.unit.replaceAll(".*/", "");
        String unit = node.getUnit().replaceAll(".*/", "");
        Log.i("UNIT:", unit);

        switch(unit) {
            case "Day (5 days per week)":
                total *= 261; // 261 weekdays
                break;
            case "Day (7 days per week)":
                total *= 365; // 365 days in a year
                break;
            case "Week":
                total *= 52;
                break;
            case "Month":
                total *= 12;
                break;
            case "Year":
                total *= 1;
                break;
        }
        Log.i("After UNIT:", Double.toString(total));

        // * NUM OF YEARS
        total *= Double.parseDouble(Double.toString(node.getNumYears()));

        Log.i("After NUM OF YEARS:", Double.toString(total));
        return total;

//        // ROUND TO TWO DECIMAL PLACES
//        String totalRounded = String.format("%.2f", total);
//        return Double.parseDouble(totalRounded);
    }
}
