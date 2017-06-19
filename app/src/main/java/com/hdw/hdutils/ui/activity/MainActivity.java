package com.hdw.hdutils.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hdw.hdutils.R;
import com.hdw.hdutils.util.RxjavaDemo;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

//    @BindView(R.id.button1)
    Button button1;
//    @BindView(R.id.button2)
    Button button2;
//    @BindView(R.id.button3)
    Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ToAntherAppActivity.class));
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RxjavaDemo.request();
//                RxjavaDemo.observeRequest();
//                RxjavaDemo.createMap();
//                RxjavaDemo.creatFlatMap();
//                RxjavaDemo.concatMap();
                RxjavaDemo.createZip();
            }
        });
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RxjavaDemo.req();
            }
        });
    }

//    @Override
//    public void onClick(View view) {
//
//    }


    @OnClick({R.id.button1,R.id.button2,R.id.button3})
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button1:
//                startActivity(new Intent(MainActivity.this, ToAntherAppActivity.class));
                break;
            case R.id.button2:
//                RxjavaDemo.request();
                break;
            case R.id.button3:
//                RxjavaDemo.req();
                break;
        }
    }

}
