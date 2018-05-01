package com.lcz.lrcedit.lrcedit;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }else if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }else {
                    if(!hasTxtFile()){
                        Toast.makeText(MainActivity.this,"请先编辑TXT文件",Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(MainActivity.this,ListActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    private boolean hasTxtFile(){
        File file = new File(Environment.getExternalStorageDirectory()+"/lrcEdit");
        File[] files = file.listFiles();
        for(File tempFile:files){
            String filename = tempFile.getName();
            int j = filename.lastIndexOf(".");
            String substring = filename.substring(j+1);
            if(substring.equalsIgnoreCase("txt")){
                return true;
            }
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "权限已获取",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }
}
