package com.stylingandroid.manuallayouttransitions;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.stylingandroid.manuallayouttransitions.part1.Part1TransitionController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.sample_language);

        View input = findViewById(R.id.input);
        View inputDone = findViewById(R.id.input_done);
        final View focusHolder = findViewById(R.id.focus_holder);

        input.setOnFocusChangeListener(Part1TransitionController.newInstance(this));
        inputDone.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(@NonNull View v) {
                        focusHolder.requestFocus();
                    }
                });
    }
}
