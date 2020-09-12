package com.meolunr.edittextcolorhelper.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.meolunr.edittextcolorhelper.EditTextColorHelper;

/**
 * Created by Meolunr on 2017/2/12
 * Email meolunr@gmail.com
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private EditText etTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTest = findViewById(R.id.et_test);

        findViewById(R.id.btn_blue).setOnClickListener(this);
        findViewById(R.id.btn_red).setOnClickListener(this);
        findViewById(R.id.btn_orange).setOnClickListener(this);
        findViewById(R.id.btn_gray).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_blue:
                EditTextColorHelper.setColor(etTest, Color.parseColor("#03A9F4"));
                break;
            case R.id.btn_red:
                EditTextColorHelper.setColor(etTest, Color.parseColor("#E91E63"));
                break;
            case R.id.btn_orange:
                EditTextColorHelper.setColor(etTest, Color.parseColor("#FF9800"));
                break;
            case R.id.btn_gray:
                EditTextColorHelper.setColor(etTest, Color.parseColor("#607D8B"));
                break;
        }
    }
}