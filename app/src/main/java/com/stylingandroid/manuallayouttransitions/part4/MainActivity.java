package com.stylingandroid.manuallayouttransitions.part4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.stylingandroid.manuallayouttransitions.R;
import com.stylingandroid.manuallayouttransitions.TransitionController;

public class MainActivity extends AppCompatActivity {

    private View input;
    private TransitionController focusChangeListener;
    private View.OnClickListener onClickListener;
    private View focusHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        focusChangeListener = Part4TransitionController.newInstance(this);
        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {
                focusHolder.requestFocus();
            }
        };

        setContentView(R.layout.activity_part4);
    }

    @Override
    public void setContentView(int layoutResID) {
        if (input != null) {
            input.setOnFocusChangeListener(null);
        }
        super.setContentView(layoutResID);
        input = findViewById(R.id.input);
        View inputDone = findViewById(R.id.input_done);
        focusHolder = findViewById(R.id.focus_holder);
        input.setOnFocusChangeListener(focusChangeListener);
        if (inputDone != null) {
            inputDone.setOnClickListener(onClickListener);
        }
    }
}
