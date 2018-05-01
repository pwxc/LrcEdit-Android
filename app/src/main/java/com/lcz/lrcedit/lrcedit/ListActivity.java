package com.lcz.lrcedit.lrcedit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lcz.lrcedit.lrcedit.other.RecycleViewListener;
import com.lcz.lrcedit.lrcedit.other.TitleAdapter;
import com.lcz.lrcedit.lrcmoudle.LrcString;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TitleAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<String> fileNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar)findViewById(R.id.ListActivity_toolBar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.ListActivity_RecyclerView);
        init();
    }

    private void init(){
        fileNameList = new ArrayList<>();
        File file = new File(Environment.getExternalStorageDirectory()+"/lrcEdit");
        File[] files = file.listFiles();
        for(File tempFile:files){
            String filename = tempFile.getName();
            int j = filename.lastIndexOf(".");
            String substring = filename.substring(j+1);
            if(substring.equalsIgnoreCase("txt")){
                fileNameList.add(filename);
            }
        }
        adapter = new TitleAdapter(fileNameList);
        adapter.setRecycleViewListener(new RecycleViewListener(){
            public void OnItemClick(View view, int position){
                try{
                    ArrayList<String> list = new ArrayList<>();
                    File file = new File(Environment.getExternalStorageDirectory()+"/lrcEdit", fileNameList.get(position));
                    FileInputStream inputStream = new FileInputStream(file);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String str = null;
                    while (true) {
                        str = reader.readLine();
                        if(str!=null)
                            list.add(str);
                        else
                            break;
                    }
                    Intent intent = new Intent(ListActivity.this, EditTimeActivity.class);
                    intent.putStringArrayListExtra("key",list);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);


    }

}
