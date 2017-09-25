package com.howmuchwillyoumake.howmuchwillyoumake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Instructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        Button backButton = (Button) findViewById(R.id.back_button_instructions);
        Button beginButton = (Button) findViewById(R.id.begin_button_instructions);

        // BACK BUTTON
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Instructions.this, MainActivity.class)); // REDIRECT TO BEGIN BUTTON
            }
        });

        // BEGIN BUTTON
        beginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Instructions.this, Input.class)); // REDIRECT TO BEGIN BUTTON
            }
        });
    }
}
