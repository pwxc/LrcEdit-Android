package com.lcz.lrcedit.lrcedit;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.lcz.lrcedit.lrcmoudle.LrcEdit;

public class EditActivity extends AppCompatActivity {

    private LrcEdit lrcEdit;
    private Button addButton;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        lrcEdit = (LrcEdit)findViewById(R.id.lrc_edit);
        addButton = (Button)findViewById(R.id.addButton);
        editText = (EditText) findViewById(R.id.editText);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = editText.getText().toString();
                if(!string.isEmpty()){
                    lrcEdit.addLrcString(string);
                    editText.getText().clear();
                }
            }
        });



    }

}
