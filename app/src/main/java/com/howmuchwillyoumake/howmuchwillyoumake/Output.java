package com.howmuchwillyoumake.howmuchwillyoumake;

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.howmuchwillyoumake.howmuchwillyoumake.Input.hash;

public class Output extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);


        // DISPLAY TOTAL
        TextView total = (TextView) findViewById(R.id.total);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String formatted_total = formatter.format(NumYearsPrompt.total - 1);
        if(formatted_total.length() > 14) {
          total.setTextSize(20);
        }
        if(formatted_total.length() > 30) {
            total.setTextSize(15);
        }
        total.setText(formatted_total);


        // DISPLAY NUMBER OF YEARS
        TextView num_of_years_final_tv = (TextView) findViewById(R.id.num_of_years_final_textview);
        String in_blank_years = num_of_years_final_tv.getText().toString();
        String numYears = NumYearsPrompt.num_of_years_final;
        in_blank_years = in_blank_years.substring(0, 3) + "<b>" + numYears + "</b>" + " " + in_blank_years.substring(3, in_blank_years.length());
        num_of_years_final_tv.setText(Html.fromHtml(in_blank_years, 0));

        // VIEW ALL ENTRIES BUTTON
        Button viewAllEntriesButton = (Button) findViewById(R.id.view_all_entries_button_output);
        viewAllEntriesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Output.this, EntryList.class)); // REDIRECT TO BEGIN BUTTON
            }
        });

        // START OVER BUTTON
        Button startOverButton = (Button) findViewById(R.id.start_over_button_output);
        startOverButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hash.clear(); // CLEAR HASH hash = new HashMap<>();
                Input.resetValues(); // RESET VARIABLES (title, operation, amount per unit, etc.)
                startActivity(new Intent(Output.this, MainActivity.class)); // REDIRECT TO BEGIN BUTTON
            }
        });

        // ADD NEW ENTRY BUTTON
        Button addNewEntryButton = (Button) findViewById(R.id.add_button_output);
        addNewEntryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Input.resetValues(); // RESET VARIABLES (title, operation, amount per unit, etc.)
                startActivity(new Intent(Output.this, Input.class)); // REDIRECT TO THE FORM/INPUT
            }
        });

        // CHANGE TOTAL YEARS BUTTON
        Button changeTotalYearsButton = (Button) findViewById(R.id.change_total_years_button_output);
        changeTotalYearsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Output.this, NumYearsPrompt.class)); // REDIRECT TO THE FORM/INPUT
            }
        });
    }
}
