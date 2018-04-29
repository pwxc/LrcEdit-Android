package com.lcz.lrcedit.lrcedit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lcz.lrcedit.lrcmoudle.LrcEdit;
import com.lcz.lrcedit.lrcmoudle.LrcString;
import com.lcz.lrcedit.lrcmoudle.LrcTimeEdit;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditTimeActivity extends AppCompatActivity {

    private LrcTimeEdit lrcEdit;
    private ArrayList<String> lrcEdits;
    private ImageButton imageButton;
    private String fileName = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittime);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar_Time);
        imageButton = (ImageButton)findViewById(R.id.imageButton);
        setSupportActionBar(toolbar);
        Intent intentGet = getIntent();
        lrcEdits = (ArrayList<String>) intentGet.getStringArrayListExtra("key");
        lrcEdit = (LrcTimeEdit) findViewById(R.id.lrctimeedit);
        lrcEdit.initStrings(lrcEdits);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lrcEdit.timeAddInit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:
                mySave();
                break;
            default:
        }
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbartime, menu);
        return true;
    }

    public void mySave(){
        View view = getLayoutInflater().inflate(R.layout.dialog, null);
        final EditText titleEditText = (EditText) view.findViewById(R.id.titleEdit);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_save)//设置标题的图片
                .setTitle("请输入歌曲名")//设置对话框的标题
                .setView(view)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fileName = titleEditText.getText().toString();
                        dialog.dismiss();
                        if(ContextCompat.checkSelfPermission(EditTimeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(EditTimeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }else if(ContextCompat.checkSelfPermission(EditTimeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(EditTimeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        }
                        else {
                            saveFile();

                        }
                    }
                }).create();
        dialog.show();
    }

    private void saveFile(){
        String fileType = ".lrc";
        List<LrcString> lrcList = lrcEdit.getLrcStrings();
        try{

            File file = new File(Environment.getExternalStorageDirectory()+"/lrcEdit");
            if (!file.exists()) {
                file.mkdirs();
            }
            file = new File(Environment.getExternalStorageDirectory()+"/lrcEdit", fileName+fileType);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file);

            for(LrcString s:lrcList){
                outStream.write((s.getSaveString()+'\n').getBytes());
            }
            Toast.makeText(this,"已保存为"+Environment.getExternalStorageDirectory()+"/lrcEdit/"+fileName+fileType,Toast.LENGTH_SHORT).show();
            outStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    saveFile();
                }else {
                    Toast.makeText(this, "You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

}
