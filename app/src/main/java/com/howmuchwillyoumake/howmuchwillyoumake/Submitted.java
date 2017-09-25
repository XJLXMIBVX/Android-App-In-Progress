package com.howmuchwillyoumake.howmuchwillyoumake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Submitted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submitted);

        // VIEW ALL ENTRIES BUTTON
        Button viewAllEntriesButton = (Button) findViewById(R.id.view_all_entries_button_submitted);
        viewAllEntriesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Submitted.this, EntryList.class)); // REDIRECT TO BEGIN BUTTON
            }
        });

        // START OVER BUTTON
        Button startOverButton = (Button)findViewById(R.id.start_over_from_sub_button);
        startOverButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Input.hash.clear(); // CLEAR HASH hash = new HashMap<>();
                Input.resetValues(); // RESET VARIABLES (title, operation, amount per unit, etc.)
                startActivity(new Intent(Submitted.this, MainActivity.class)); // REDIRECT TO BEGIN BUTTON
            }
        });

        // ADD ANOTHER ENTRY BUTTON
        Button addButton = (Button)findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Input.resetValues(); // RESET VARIABLES (title, operation, amount per unit, etc.)
                startActivity(new Intent(Submitted.this, Input.class)); // REDIRECT TO THE FORM/INPUT
            }
        });

        // CONTINUE BUTTON
        Button continueButton = (Button)findViewById(R.id.continue_to_calculate_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // REDIRECT TO FINAL PROMPT
                startActivity(new Intent(Submitted.this, NumYearsPrompt.class));
            }
        });
    }
}
