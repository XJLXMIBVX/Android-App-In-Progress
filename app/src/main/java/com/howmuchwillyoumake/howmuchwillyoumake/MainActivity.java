package com.howmuchwillyoumake.howmuchwillyoumake;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.howmuchwillyoumake.howmuchwillyoumake.Input.hash;


public class MainActivity extends AppCompatActivity {

    public static boolean isSubmitted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Node n1 = new Node("ching", true, false, 1, 1, "Hour(s)/Day (5 days per week)", 1);
//        Node n2 = new Node("chong", false, true, 2, 2, "Hour(s)/Week", 2);
//        Node n3 = new Node("ling", false, true, 3, 3, "Day(s)/Year", 3);
//        Node n4 = new Node("long", true, false, 4, 4, "Week(s)/Month", 4);
//        Node n5 = new Node("bong", true, false, 5, 5, "Hour(s)/Year", 5);
//        Node n6 = new Node("tong", true, false, 6, 6, "Month(s)/Year", 6);
//        Node n7 = new Node("wing", true, false, 7, 7, "Hour(s)/Day (7 days per week)", 7);
//        Node n8 = new Node("wong", true, false, 8, 8, "Hour(s)/Week", 8);

//        hash.put(1, n1);
//        hash.put(2, n2);
//        hash.put(3, n3);
//        hash.put(4, n4);
//        hash.put(5, n5);
//        hash.put(6, n6);
//        hash.put(7, n7);
//        hash.put(8, n8);

        Log.i("CHECK", "supposed to see this twice");
        // IF THERE'S AT LEAST ONE ACTIVITY (in the hash)
        if(!hash.isEmpty()) {
            Button addNewActivityButton = (Button) findViewById(R.id.add_new_activity_button_main);
            addNewActivityButton.setText("Add New Activity");
        }

        if(isSubmitted) {
//            displayToastMessageInCenter("Activity submitted. Thank you!", getApplicationContext());
            isSubmitted = false;
//            startActivity(getIntent());
        }

        // ADD NEW ACTIVITY BUTTON
        Button addNewActivityButton = (Button) findViewById(R.id.add_new_activity_button_main);
        addNewActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // REDIRECTS TO INPUT ACTIVITY USING startActivityForResult (resultCode = 1)
                Intent intent = new Intent(MainActivity.this, Input.class);
                (MainActivity.this).startActivityForResult(intent, 1);
            }
        });

        // VIEW ALL ENTRIES BUTTON
        Button viewAllEntriesButton = (Button) findViewById(R.id.view_all_entries_button_main);
        viewAllEntriesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // IF THERE ARE NO ENTRIES YET, DISPLAY ERROR MESSAGE
                if(hash.isEmpty()) {
                    displayErrorAlert("No Activities Yet:", "Please add at least one activity before viewing.", "", MainActivity.this);
                }
                else {
                    startActivity(new Intent(MainActivity.this, EntryList.class));
                }
            }
        });


        // CLEAR ALL ENTRIES BUTTON
        Button clearAllEntriesButton = (Button) findViewById(R.id.clear_all_entries_button_main);
        clearAllEntriesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // IF THERE ARE NO ACTIVITIES YET
                if(hash.isEmpty()) {
                    displayErrorAlert("No Activities Yet", "Please add at least one activity.", "", MainActivity.this);
                }
                // IF THERE ARE ANY ACTIVITIES THAT HAVE BEEN SUBMITTED
                else {

                    // WARNING
                    clearAllTwoOptionsAlert("WARNING!", "Are you sure you want to delete all activities?",
                            "", "All activities have been cleared.", "No", "Yes", MainActivity.this);
                }
            }
        });


        // CALCULATE BUTTON
        Button calculateButton = (Button) findViewById(R.id.calculate_button_main);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // IF THERE ARE NO ENTRIES YET, DISPLAY ERROR MESSAGE
                if(hash.isEmpty()) {
                    displayErrorAlert("No Activities Yet:", "Please add at least one activity before calculating.", "", MainActivity.this);
                }
                else {
                    startActivity(new Intent(MainActivity.this, Output.class));
                }
            }
        });


        // INSTRUCTIONS BUTTON
        Button instructionsButton = (Button) findViewById(R.id.instructions_button_main);
        instructionsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Instructions.class));
            }
        });
    }


    public void displayErrorAlert(String title, String err_msg, final String msg_after_clicked, Context c) {
        AlertDialog alertDialog = new AlertDialog.Builder(c).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(err_msg);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.warning_triangle_icon);

        // Setting OK Button
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

            // AFTER OK BUTTON IS CLICKED
            public void onClick(DialogInterface dialog, int which) {

                if(msg_after_clicked == "") {
                    // DO NOTHING
                } else {
                    displayToastMessageInCenter(msg_after_clicked, getApplicationContext());
                }
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public boolean clearAllTwoOptionsAlert(String title, String err_msg,
                                final String msg_after_clicked_neg, final String msg_after_clicked_pos,
                                String negOption, String posOption, Context c) {

        AlertDialog alertDialog = new AlertDialog.Builder(c).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(err_msg);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.warning_triangle_icon);

        // Setting NEGATIVE Button
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, negOption, new DialogInterface.OnClickListener() {

            // AFTER NEGATIVE BUTTON IS CLICKED
            public void onClick(DialogInterface dialog, int which) {

                // CHECKS IF TOAST NEEDED
                if (msg_after_clicked_neg == "") {
                    // DO NOTHING
                } else {
                    displayToastMessageInCenter(msg_after_clicked_neg, getApplicationContext());
                }
            }
        });

        // Setting POSITIVE Button
        alertDialog.setButton(Dialog.BUTTON_POSITIVE, posOption, new DialogInterface.OnClickListener() {

            // AFTER POSITIVE BUTTON IS CLICKED
            public void onClick(DialogInterface dialog, int which) {

                // CHECKS IF TOAST NEEDED
                if(msg_after_clicked_pos == "") {
                    // DO NOTHING
                }
                else {
                    displayToastMessageInCenter(msg_after_clicked_pos, getApplicationContext());
                }
                Input.resetValues();
                hash.clear();
                Input.idCnt = 1;
            }
        });

        // Showing Alert Message
        alertDialog.show();
        return true;
    }


    public void displayToastMessageInCenter(String toastMessage, Context c) {
        Toast toast = Toast.makeText(c, toastMessage, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 150);
        toast.show();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == Activity.RESULT_OK) {
                // WRITE CODE HERE IF YOU HAVE A RESULT
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // WRITE CODE HERE IF YOU DON'T HAVE RESULT

                // REFRESH ACTIVITY
                finish();
                startActivity(getIntent());
            }
        }
    }
}


