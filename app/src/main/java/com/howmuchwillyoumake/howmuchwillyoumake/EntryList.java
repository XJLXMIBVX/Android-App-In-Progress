package com.howmuchwillyoumake.howmuchwillyoumake;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.howmuchwillyoumake.howmuchwillyoumake.Constants.FIRST_COLUMN;
import static com.howmuchwillyoumake.howmuchwillyoumake.Constants.SECOND_COLUMN;
import static com.howmuchwillyoumake.howmuchwillyoumake.Constants.THIRD_COLUMN;
import static com.howmuchwillyoumake.howmuchwillyoumake.Input.hash;


public class EntryList extends Activity {

    private ArrayList<HashMap<String, String>> list;
    public static boolean editHasBeenClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        ListView lv = (ListView)findViewById(R.id.entry_list_listview);
        list = new ArrayList<HashMap<String,String>>();

        setAddNewEntryButton(); // ADD NEW ENTRY BUTTON

        // CREATES LIST OF ACTIVITIES
        list = setLabelsAndCreateListOfActivities();

        // DISPLAYS LIST OF ACTIVITIES
        displayListOfActivities(lv);

        // ENABLES ELEMENTS IN LIST TO BE CLICKABLE
        enableClickableList(lv);

        if(editHasBeenClicked) {
//            MainActivity ma = new MainActivity();
//            ma.displayToastMessageInCenter("Activity has been edited.", getApplicationContext());
            editHasBeenClicked = false;
//            startActivity(getIntent());
        }
    }



    private void displayListOfActivities(ListView listView) {
        ListViewAdapter adapter = new ListViewAdapter(this, list);
        listView.setAdapter(adapter);
    }

    private void enableClickableList(ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                // IF THE LABELS ROW WAS CLICKED
                if(position == 0) {
                    // DO NOTHING
                }
                else {
                    editOrDeleteAlert("Edit or Delete?", "Would you like to edit or delete this entry?",
                            "", "Activity deleted.",
                            "Edit", "Delete", position, EntryList.this); // OG: position-1
                }
            }
        });
    }

    private void setAddNewEntryButton() {
        Button add_entry_button = (Button) findViewById(R.id.add_entry_button_entrylist);
        add_entry_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // REDIRECTS TO INPUT ACTIVITY USING startActivityForResult (resultCode = 1)
                Intent intent = new Intent(EntryList.this, Input.class);
                (EntryList.this).startActivityForResult(intent, 1);

//                Input.resetValues();
//                startActivity(new Intent(EntryList.this, Input.class));
            }
        });
    }

    /**
     *
     * @param title
     * @param err_msg
     * @param msgAfterNegClick Message after edit is clicked
     * @param msgAfterPosClick Message after delete is clicked
     * @param negOptionText "Edit"
     * @param posOptionText "Delete"
     * @param c
     * @return
     */
    private boolean editOrDeleteAlert(String title, String err_msg,
                                           final String msgAfterNegClick, final String msgAfterPosClick,
                                           String negOptionText, String posOptionText, final int activityId, Context c) {

        AlertDialog alertDialog = new AlertDialog.Builder(c).create();

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(err_msg);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.question_mark_icon);

        // Setting NEGATIVE (EDIT) Button
        setEditButtonNegative(alertDialog, negOptionText, msgAfterNegClick, activityId);

        // Setting POSITIVE (DELETE) Button
        setEditButtonPositive(alertDialog, posOptionText, msgAfterPosClick);

        // Showing Alert Message
        alertDialog.show();
        return true;
    }

    private void setEditButtonPositive(AlertDialog ad, String posOptionText, final String msgAfterPosClick) {

        ad.setButton(Dialog.BUTTON_POSITIVE, posOptionText, new DialogInterface.OnClickListener() {

            // AFTER DELETE BUTTON IS CLICKED
            public void onClick(DialogInterface dialog, int which) {

                // CHECKS IF TOAST NEEDED
                if(msgAfterPosClick == "") {
                    // DO NOTHING
                }
                else {
                    MainActivity ma = new MainActivity();
                    ma.displayToastMessageInCenter(msgAfterPosClick, getApplicationContext());
                }
            }
        });
    }

    private void setEditButtonNegative(AlertDialog ad, String negOptionText, final String msgAfterNegClick, final int actId) {

        ad.setButton(Dialog.BUTTON_NEGATIVE, negOptionText, new DialogInterface.OnClickListener() {

            // AFTER EDIT BUTTON IS CLICKED
            public void onClick(DialogInterface dialog, int which) {

                // CHECKS IF TOAST NEEDED
                if (msgAfterNegClick == "") {
                    // DO NOTHING
                } else {
                    MainActivity ma = new MainActivity();
                    ma.displayToastMessageInCenter(msgAfterNegClick, getApplicationContext());
                }
                editHasBeenClicked = true;
                Input.editActivityId = actId;

                // REDIRECTS TO INPUT ACTIVITY USING startActivityForResult (resultCode = 1)
                Intent intent = new Intent(EntryList.this, Input.class);
                (EntryList.this).startActivityForResult(intent, 1);
            }
        });
    }

    private ArrayList<HashMap<String,String>> setLabelsAndCreateListOfActivities() {

        // SET UP LABELS
        setLabels();

        // CREATES LIST
        createListOfActivities();

        return list;
    }

    private void createListOfActivities() {

        // GO THROUGH HASH MAP (for each key)
        for(Map.Entry<Integer, Node> entry : hash.entrySet()) {

            // GET THE NODE OF THE CURRENT KEY
            Node currNode = entry.getValue();

            // INSERTS CURRENT NODE INTO LIST
            insertCurrentNodeIntoList(currNode);
        }
    }

    private void insertCurrentNodeIntoList(Node currNode) {

        // CREATES ANOTHER HASH OF ACTIVITIES (keys separated into columns)
        HashMap<String,String> oneRow = new HashMap<String, String>();

        // PREPARES COLUMNS
        prepareColumns(oneRow, currNode);

        // ADDS 1ST, 2ND, & 3RD COLUMNS INTO THE LIST
//        list.add(oneRow);
        list.add(1, oneRow); // add to beginning of list
    }

    private void prepareColumns(HashMap<String,String> oneRow, Node currNode) {

        // PREPARES FIRST COLUMN
        prepareTitle(oneRow, currNode);

        // PREPARES SECOND COLUMN
        prepareOpAmountAndUnits(oneRow, currNode);

        // PREPARES THIRD COLUMN
        prepareNumOfYears(oneRow, currNode);
    }

    private void prepareNumOfYears(HashMap<String,String> oneRow, Node currNode) {
        oneRow.put(THIRD_COLUMN, "for " + Double.toString(currNode.getNumYears()) + " yr(s)");
    }

    private void prepareOpAmountAndUnits(HashMap<String,String> oneRow, Node currNode) {
        // GET OPERATION
        String op = currNode.getOperation();

        // FIX MONEY AMOUNT SYNTAX ($__.__)
        double amount = currNode.getAmountPerUnit();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String fixed_amount = formatter.format(amount);

        // GET NUM UNITS
        int num_units = currNode.getNumOfUnits();

        // GET UNIT
        String tempUnit = currNode.getUnit();

        Log.i("UNIT: ", tempUnit + " inside prepareOpAmountAndUnits");


        // FIXES UNIT DISPLAY (if unit text is too long, split into two lines)
        String unit_display = fixUnitDisplay(tempUnit, currNode);

        // PREPARES OPERATION, AMOUNT, NUM UNITS, & UNIT
        oneRow.put(SECOND_COLUMN, op + fixed_amount + "\n" + num_units + " " + unit_display);
    }

    /**
     * FIXES UNIT DISPLAY (if unit text is too long, split into two lines)
     * @param currUnit current unit
     * @return one or two lines of text to display the unit
     */
    private String fixUnitDisplay(String currUnit, Node currNode) {

        String unit_display = "";

        // IF THE UNIT STRING INCLUDES (5 DAYS/WEEK or 7 DAYS PER/WEEK)
        if( currUnit.length() > 15 ) {

            Log.i("UNIT LENGTH: ", Integer.toString(currUnit.length()));

            // SEPARATE INTO TWO DIFFERENT LINES (FOR MORE SPACE IN THE LIST OF ENTRIES)
            String secondLine = currUnit.substring(currUnit.indexOf("(", 10), currUnit.length());
//            secondLine += ")";
            String firstLine = currUnit.split(" ")[0];

            unit_display = firstLine + "\n" + secondLine;
        }
        else {

            unit_display = currNode.getUnit();
        }

        return unit_display;
    }

    private void prepareTitle(HashMap<String,String> oneRow, Node currNode) {
        oneRow.put(FIRST_COLUMN, currNode.getTitleInput());
    }

    private void setLabels() {
        HashMap<String,String> labels = new HashMap<String, String>();
        labels.put(FIRST_COLUMN, "Title");
        labels.put(SECOND_COLUMN, "$__.__ # (units)");
        labels.put(THIRD_COLUMN, "Year Duration");
        list.add(0, labels);
    }


    @Override
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







