package me.iacn.editcolorhelpersample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import me.iacn.editcolorhelper.EditColorHelper;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etTest = (EditText) findViewById(R.id.et_test);
        EditColorHelper.setColor(etTest, Color.parseColor("#03A9F4"));
    }
}