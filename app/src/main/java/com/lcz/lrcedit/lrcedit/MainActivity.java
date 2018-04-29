package com.lcz.lrcedit.lrcedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button addTxt;
    private Button addLrc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar)findViewById(R.id.MainActivity_toolBar);
        setSupportActionBar(toolbar);
        addTxt = (Button) findViewById(R.id.MainActivity_addTxt);
        addLrc = (Button) findViewById(R.id.MainActivity_addLrc);
        addTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

        addLrc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"请先编辑TXT文件",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
