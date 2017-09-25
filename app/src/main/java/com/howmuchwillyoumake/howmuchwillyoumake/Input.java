package com.howmuchwillyoumake.howmuchwillyoumake;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.LinkedHashMap;

import static com.howmuchwillyoumake.howmuchwillyoumake.EntryList.editHasBeenClicked;

public class Input extends AppCompatActivity {


    public static String title_input = "";
    public static boolean add = false;
    public static boolean sub = false;
//    public static double amount_per_unit = 0.0;
//    public static int num_of_units = 0;
//    public static String unit = "";
//    public static double year_duration = 0;
    public static String error_message = "";
    public static LinkedHashMap<Integer, Node> hash = new LinkedHashMap<>(); // ID, Node
    public static int idCnt = 1;
    public static int editActivityId = 0;
    public static Node currentNode;
    public static boolean goBackHasBeenClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);


        // IF YOU ARE EDITING AN ACTIVITY
        if(editHasBeenClicked) {

            // GET KEY (ID) OF THE NODE CLICKED
            int currNodeId = 1 + (hash.size() - (editActivityId)); // editActivityId + 1

            Log.i("POSITION CLICKED", Integer.toString(editActivityId));
            Log.i("HASH SIZE", Integer.toString(hash.size()));
            Log.i("PREPOPULATE THIS", Integer.toString(currNodeId));
            Log.i("KEYS", hash.keySet().toString());

            editActivityId = currNodeId;
            // PRE-POPULATE FORM WITH THE INFO OF THE ID CLICKED
            prePopulateForm(editActivityId);
        }

        // IF ADDING A NEW ACTIVTIY
        else {

            // IF IT'S THE USER'S FIRST TIME FILLING OUT THIS ACTIVITY (NOT EDITING IT), SET TO PROMPT
            setUnitSpinnerPrompt();
        }

        // PREPARES THE ADD, SUB, CLEAR, AND SUBMIT BUTTONS
        setAllButtonsToDefault();
    }


    /**
     * PREPARES THE ADD, SUB, CLEAR, AND SUBMIT BUTTONS
     */
    public void setAllButtonsToDefault() {

        // PREPARES THE ADD & SUB BUTTONS
        setAddAndSubButtons();

        // PREPARES THE NORMAL CLEAR & SUBMIT BUTTONS
        setNormalClearAndSubmitButtons();
    }

    private void setNormalClearAndSubmitButtons() {

        // PREPARES REGULAR CLEAR AND SUBMIT BUTTONS
        setClearButton();
        setSubmitButton();
    }


    private void setSubmitButton(){
        // SUBMIT BUTTON
        Button submitButton = (Button)findViewById(R.id.submit_button_input);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // GOES THROUGH EMPTY INPUTS AND CREATES AN ERROR MESSAGE POP UP IF ANY ERRORS
                accumulatingErrorMessage();

                // IF THERE'S AN ERROR (if error_message has something), DISPLAY ERROR MESSAGE
                // IF NOT, GO TO CONFIRMATION PAGE
                errorOrSuccess();
            }
        });
    }

    private void setClearButton() {
        // CLEAR ALL BUTTON
        Button clearAllButton = (Button)findViewById(R.id.clear_all_button_input);
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                clearForm((ViewGroup) findViewById(R.id.activity_input));
            }
        });
    }

    private void setUnitSpinnerPrompt() {
        if(!editHasBeenClicked) {
            // SETS DROPDOWN DEFAULT SELECTION
            Spinner unit = (Spinner) findViewById(R.id.units_dropdown);
            unit.setSelection(0);
        }
    }

    private void setAddAndSubButtons() {
        Button addButton = (Button) findViewById(R.id.add_button);
        Button subButton = (Button) findViewById(R.id.sub_button);

        // ADD BUTTON
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                add = true;
                sub = false;
                changeOperationButtonColor();
            }
        });

        // SUBTRACT BUTTON
        subButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                add = false;
                sub = true;
                changeOperationButtonColor();
            }
        });
    }


    /**
     * PREPOPULATES THE FORM IF THE USER IS EDITING AN ACTIVITY
     */
    private void prePopulateForm(int id){
        // USE COUNT FOR BRAND NEW ACT, OR USE ID FROM NODE OR ID THAT WAS CLICKED (from editing)
        Node currNode = hash.get(id);

        // PRE-FILLS THE CATEGORY BLANK
        EditText category = (EditText) findViewById(R.id.title_input);
        category.setText(currNode.getTitleInput());

        // PRE-SELECTS THE OPERATION BUTTON
        if(currNode.getAddCond()) {
            changeOperationButtonColor();
        } else {
            changeOperationButtonColor();
        }

        // PRE-FILLS THE AMOUNT BLANK
        EditText amount = (EditText) findViewById(R.id.amount_per_unit);
        amount.setText(Double.toString(currNode.getAmountPerUnit()));

        // PRE-FILLS THE NUM OF UNITS BLANK
        EditText numOfUnits = (EditText) findViewById(R.id.num_of_units);
        numOfUnits.setText(Integer.toString(currNode.getNumOfUnits()));

        // PRE-SELECTS UNIT
        Spinner unit = (Spinner) findViewById(R.id.units_dropdown);
        String compareValue = currNode.getUnit();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.unit_arrays, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(adapter);
        if (!compareValue.equals(null)) {
            int spinnerPosition = adapter.getPosition(compareValue);
            unit.setSelection(spinnerPosition);
        }

        // PRE-FILLS YEAR
        EditText num_years = (EditText) findViewById(R.id.num_years);
        num_years.setText(Double.toString(currNode.getNumYears()));
    }


    private void changeOperationButtonColor() {

        Button addButton = (Button) findViewById(R.id.add_button);
        Button subButton = (Button) findViewById(R.id.sub_button);

        int default_blue = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        String hexColor = String.format("#%06X", (default_blue));

        Drawable a = findViewById(R.id.add_button).getBackground();
        Drawable s = findViewById(R.id.sub_button).getBackground();
        PorterDuffColorFilter filter_default_blue = new PorterDuffColorFilter(Color.parseColor(hexColor), PorterDuff.Mode.SRC_ATOP);


        if(add && !sub) {
            // ADD BUTTON TURNS BLUE & ADD TEXT TURNS WHITE WHEN CLICKED
            a.setColorFilter(filter_default_blue);
            addButton.setTextColor(Color.WHITE);

            // SUB BUTTON BECOMES DEFAULT GREY
            findViewById(R.id.sub_button).invalidateDrawable(s);
            s.clearColorFilter();
            subButton.setTextColor(Color.BLACK);

        }
        if (!add && sub) {
            // SUB BUTTON TURNS BLUE & SUB TEXT TURNS WHITE WHEN CLICKED
            s.setColorFilter(filter_default_blue);
            subButton.setTextColor(Color.WHITE);

            // ADD BUTTON BECOMES DEFAULT GREY
            findViewById(R.id.add_button).invalidateDrawable(a);
            a.clearColorFilter();
            addButton.setTextColor(Color.BLACK);
        }
    }


    private void errorOrSuccess() {

        // ERROR
        if( !error_message.isEmpty() ) {
            MainActivity mActivity = new MainActivity();
            mActivity.displayErrorAlert("Please check the following area(s) for errors:", error_message, "", Input.this);
        }
        // SUCCESS
        else {

            // SAVES CURRENT INFO INTO A GLOBAL NODE
            currentNode = getNodeOfInput();

            // ALERT FOR INPUT CONFIRMATION
            confirmInputAlert("Cancel", "Submit", Input.this);
        }
    }


    private void confirmInputAlert(String negOptionText, String posOptionText, Context c) {

        AlertDialog alertDialog = new AlertDialog.Builder(c).create();

        // Setting Dialog Title
        alertDialog.setTitle("Please confirm the following information:");

        // Setting Dialog Message (confirmation method)
        alertDialog.setMessage(getConfirmationMessage());

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.question_mark_icon);

        // After NEGATIVE (CANCEL) Button is clicked
        setCancelAlertButton(alertDialog, negOptionText);

        // After POSITIVE (SUBMIT) Button is clicked
        setSubmitAlertButton(alertDialog, posOptionText);

        // Showing Alert Message
        alertDialog.show();
    }

    private void setSubmitAlertButton(AlertDialog alertDialog, String posOptionText) {

        alertDialog.setButton(Dialog.BUTTON_POSITIVE, posOptionText, new DialogInterface.OnClickListener() {

            // AFTER POSITIVE (SUBMIT) BUTTON IS CLICKED
            public void onClick(DialogInterface dialog, int which) {

                // SUBMITS THE EDITED OR NORMAL ACTIVITY
                submitEditedOrNormal();

                // RETURNS (NO DATA) AFTER FINISHING (CLOSING THE CURRENT ACTIVITY)
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    private void submitEditedOrNormal() {

        MainActivity ma = new MainActivity();

        // REPLACES OLD NODE WITH NEW EDITED NODE
        if(EntryList.editHasBeenClicked) {

            hash.put(editActivityId, currentNode);
            editHasBeenClicked = false;
            ma.displayToastMessageInCenter("Activity has been edited.", getApplicationContext());
        }

        // SAVES/ADDS A NEW ENTRY/NODE
        else {

            hash.put(idCnt++, currentNode);
            ma.isSubmitted = true;
            ma.displayToastMessageInCenter("Activity submitted. Thank you!", getApplicationContext());
//            ma.isSubmitted = false;
        }
    }


    private void setCancelAlertButton(AlertDialog alertDialog, String negOptionText) {

        alertDialog.setButton(Dialog.BUTTON_NEGATIVE, negOptionText, new DialogInterface.OnClickListener() {
            // AFTER NEGATIVE (CANCEL) BUTTON IS CLICKED
            public void onClick(DialogInterface dialog, int which) {
                // DO NOTHING
            }
        });
    }


    private String getConfirmationMessage() {
        // SETS/DISPLAYS THE TITLE TEXT ON CONFIRMATION PAGE
        String title = currentNode.getTitleInput();

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

        return title + ": " + op + "$" + fixed_amount + " " + num_units + " " + unit + " for " + num_years + " year(s).";
    }


    private Node getNodeOfInput() {
        double temp_amount_per_unit, temp_year_duration;
        int temp_num_of_units;

        EditText title = (EditText) findViewById(R.id.title_input);
        EditText amount = (EditText) findViewById(R.id.amount_per_unit);
        EditText num_units = (EditText) findViewById(R.id.num_of_units);
        Spinner units_dropdown = (Spinner) findViewById(R.id.units_dropdown);
        EditText num_years = (EditText) findViewById(R.id.num_years);

        // TITLE
        String temp_title = title.getText().toString();

        // AMOUNT PER UNIT
        if(amount.getText().toString().isEmpty()) {
            temp_amount_per_unit = 0;
        } else {
            temp_amount_per_unit = Double.parseDouble(amount.getText().toString());
        }

        // NUM OF UNITS
        if(num_units.getText().toString().isEmpty()) {
            temp_num_of_units = 0;
        } else {
            temp_num_of_units = Integer.parseInt(num_units.getText().toString());
        }

        // UNIT
        String temp_unit = units_dropdown.getSelectedItem().toString();

        // NUM YEARS
        if(num_years.getText().toString().isEmpty()) {
            temp_year_duration = 0;
        } else {
            temp_year_duration = Double.parseDouble(num_years.getText().toString());
        }

        // SAVE INFO INTO NODE
        Node node = new Node(temp_title, Input.add, Input.sub, temp_amount_per_unit, temp_num_of_units, temp_unit, temp_year_duration);
        return node;
    }


    private void accumulatingErrorMessage() {

        // RESETS ERROR MESSAGE
        error_message = "";

        // KEEPS COUNT OF ERRORS
        int count = 1;

        String temp_title, temp_amount_per_unit, temp_num_of_units, temp_unit, temp_year_duration;

        // SAVES INPUT INTO VARIABLES
        EditText title = (EditText) findViewById(R.id.title_input);
        EditText amount = (EditText) findViewById(R.id.amount_per_unit);
        EditText num_units = (EditText) findViewById(R.id.num_of_units);
        Spinner units_dropdown = (Spinner) findViewById(R.id.units_dropdown);
        EditText num_years = (EditText) findViewById(R.id.num_years);

        temp_title = title.getText().toString();
        temp_amount_per_unit = amount.getText().toString();
        temp_num_of_units = num_units.getText().toString();
        temp_unit = units_dropdown.getSelectedItem().toString();
        temp_year_duration = num_years.getText().toString();

        // CHECKS IF ANY INPUT IS EMPTY
            // AND CONCATINATES MISSING INPUT INTO ONE ERROR MESSAGE
        if(temp_title.isEmpty()) {
            error_message += count++ + ": Title\n";
        }
        if(!add && !sub) {
            error_message += count++ + ": Operation\n";
        }
        if(temp_amount_per_unit.isEmpty()) {
            error_message += count++ + ": Amount per Unit\n";
        }

        if(temp_num_of_units.isEmpty()) {
            error_message += count++ + ": Number of Units\n";
        }
        else {
            // CHECKS IF NUMBER OF UNITS ARE REASONABLE COMPARED TO THE UNIT CHOSEN
            String unitError = checkNumUnits(temp_num_of_units, temp_unit);

            if(!unitError.isEmpty()) {
                error_message += count++ + ": Number of Units" + unitError +"\n";
            }
        }

        if(temp_unit.equals("Please choose a Unit...")) {
            error_message += count++ + ": Unit\n";
        }
        if(temp_year_duration.isEmpty()) {
            error_message += count + ": Number of years";
        }
    }


    private String checkNumUnits(String numberOfUnits, String unit) {

        int numUnits = Integer.parseInt(numberOfUnits);

        if(unit.equals("Hour(s)/Day (5 days per week)") && numUnits > 24 ) {
            return " - There are only 24 hours in a day.";
        }
        else if(unit.equals("Hour(s)/Day (7 days per week)") && numUnits > 24 ) {
            return " - There are only 24 hours in a day.";
        }
        else if(unit.equals("Hour(s)/Week") && numUnits > 168 ) {
            return " - There are only 168 hours in a week.";
        }
        // ": For a more accurate calculation, please use (an amount?) less than 672 Hours/Month.\n"
        else if(unit.equals("Hour(s)/Month") && numUnits > 672 ) {
            return " - For a more accurate calculation, please use a number less than 673 Hours/Month.";
        }
        else if(unit.equals("Hour(s)/Year") && numUnits > 8760) {
            return " - For a more accurate calculation, please use a number less than 8761 Hours/Year.";
        }
        else if(unit.equals("Day(s)/Week") && numUnits > 7) {
            return " - There are only 7 days in a week.";
        }
        else if(unit.equals("Day(s)/Month") && numUnits > 30) {
            return " - For a more accurate calculation, please use a number less than 31 Hours/Year.";
        }
        else if(unit.equals("Day(s)/Year") && numUnits > 365) {
            return " - For a more accurate calculation, please use a number less than 366 Days/Year.";
        }
        else if(unit.equals("Week(s)/Month") && numUnits > 4) {
            return " - For a more accurate calculation, please use a number less than 5 Weeks/Month.";
        }
        else if(unit.equals("Week(s)/Year") && numUnits > 52) {
            return " - For a more accurate calculation, please use a number less than 53 Weeks/Year.";
        }
        else if(unit.equals("Month(s)/Year") && numUnits > 12) {
            return " - There are only 12 months in a year.";
        }
        return "";
    }


    protected String getErrorMessage() {
        return error_message;
    }


    public void
    clearForm(ViewGroup group) {

        // RESETS ADD AND SUB BUTTONS TO DEFAULT
        Button addButton = (Button) findViewById(R.id.add_button);
        Button subButton = (Button) findViewById(R.id.sub_button);
        Drawable a = addButton.getBackground();
        Drawable s = subButton.getBackground();
        findViewById(R.id.add_button).invalidateDrawable(a);
        findViewById(R.id.sub_button).invalidateDrawable(s);
        a.clearColorFilter();
        s.clearColorFilter();
        addButton.setTextColor(Color.BLACK);
        subButton.setTextColor(Color.BLACK);
        add = sub = false;

        // RESETS THE REST OF THE INPUT VALUES
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText)view).setText("");
            }
            if (view instanceof Spinner) {
                Spinner units_dropdown;
                units_dropdown = (Spinner) findViewById(R.id.units_dropdown); // Ignore this if you already did that in onCreateView
                units_dropdown.setSelection(0);
            }
            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0)) {
                clearForm((ViewGroup) view);
            }
        }
    }


    public static void resetValues() {

        add = false;
        sub = false;

        error_message = "";
        editActivityId = 0;
        editHasBeenClicked = false;
    }


    protected static void setErrorMessage(String s) {
        error_message = s;
    }
}




