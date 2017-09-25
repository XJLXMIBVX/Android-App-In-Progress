package com.howmuchwillyoumake.howmuchwillyoumake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.howmuchwillyoumake.howmuchwillyoumake.Input.currentNode;
import static com.howmuchwillyoumake.howmuchwillyoumake.Input.editActivityId;
import static com.howmuchwillyoumake.howmuchwillyoumake.Input.hash;
import static com.howmuchwillyoumake.howmuchwillyoumake.Input.idCnt;

public class Confirmation extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        // IF SUCCEEDED ==> DISPLAY TITLE & DETAILS
        TextView title_tv = (TextView) findViewById(R.id.title_confirmation);
        TextView details_tv = (TextView) findViewById(R.id.details_confirmation);

        // SETS/DISPLAYS THE TITLE TEXT ON CONFIRMATION PAGE
        title_tv.setText(currentNode.getTitleInput());

        // SETS/DISPLAYS THE DETAILS TEXT ON CONFIRMATION PAGE
        String op = "";
        if(currentNode.getAddCond()) {
            op = "Add ";
        } else if(currentNode.getSubCond()) {
            op = "Subtract ";
        }

        double amount = currentNode.getAmountPerUnit();
        String fixed_amount = String.format("%.2f", amount);
        int num_units = currentNode.getNumOfUnits();
        String unit = currentNode.getUnit();
        double num_years = currentNode.getNumYears();

        String details = op + "$" + fixed_amount + " " + num_units + " " + unit + " for " + num_years + " year(s).";
        details_tv.setText(details);


        // GO BACK BUTTON
        final Button goBackButton = (Button)findViewById(R.id.go_back_button);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Input.goBackHasBeenClicked = true;
                startActivity(new Intent(Confirmation.this, Input.class)); // GO BACK TO INPUT ACTIVITY
            }
        });


        // YES, SUBMIT BUTTON
        Button submitButton = (Button)findViewById(R.id.yes_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if( EntryList.editHasBeenClicked ) {
                    hash.put(editActivityId, currentNode);
                    EntryList.editHasBeenClicked = false;
                }
                else {
                    hash.put(idCnt, currentNode);
                }
                MainActivity.isSubmitted = true;

                startActivity(new Intent(Confirmation.this, MainActivity.class));
            }
        });
    }
}
