package com.lcz.lrcedit.lrcedit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.lcz.lrcedit.lrcmoudle.LrcEdit;

import java.util.ArrayList;

public class EditTimeActivity extends AppCompatActivity {

    private LrcEdit lrcEdit;
    private ArrayList<String> lrcEdits;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittime);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar_Time);
        setSupportActionBar(toolbar);
        Intent intentGet = getIntent();
        lrcEdits = (ArrayList<String>) intentGet.getStringArrayListExtra("key");
        lrcEdit = (LrcEdit) findViewById(R.id.lrctimeedit);


    }

    @Override
    protected void onResume() {
        super.onResume();
        for (String string:lrcEdits){
            lrcEdit.addLrcString(string);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbartime, menu);
        return true;
    }
}
