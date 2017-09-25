package com.howmuchwillyoumake.howmuchwillyoumake;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by OJ on 8/29/17.
 */
public class Pop extends Activity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        // CHANGES POPUP TEXT INTO THE ERROR MESSAGE
        Input inputClass = new Input();
        TextView e_message_textview = (TextView) findViewById(R.id.popup_message);
        e_message_textview.setText(inputClass.getErrorMessage());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8), (int)(height*0.35));


    }
}
