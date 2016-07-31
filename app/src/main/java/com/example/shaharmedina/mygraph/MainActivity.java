package com.example.shaharmedina.mygraph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity.class";

    //insert here the data that you eant to show
    private float[] data = new float[] {10, 15 ,12, 30, 40, 5, 20, 15, 10, 25, 8, 17 };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Line Graph view initialize
        LineGraphView lg = (LineGraphView) findViewById(R.id.linechart);
        lg.setGraphData(getRandomData());

    }

    //retutn the data for the graph
    private float[] getRandomData() {
        return data;
    }


}
