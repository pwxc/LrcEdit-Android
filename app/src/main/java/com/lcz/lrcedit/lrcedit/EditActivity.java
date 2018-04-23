package com.lcz.lrcedit.lrcedit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.lcz.lrcedit.lrcmoudle.LrcEdit;
import com.lcz.lrcedit.lrcmoudle.LrcString;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private LrcEdit lrcEdit;
    private Button addButton;
    private EditText editText;
    private String fileName = "test";

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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
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
                        if(ContextCompat.checkSelfPermission(EditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(EditActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }else if(ContextCompat.checkSelfPermission(EditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(EditActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                        }
                        else {
                            saveFile();

                        }
                    }
                }).create();
        dialog.show();
    }




    //保存文件函数
    private void saveFile(){
        String fileType = ".txt";
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
                outStream.write((s.getText()+'\n').getBytes());
            }
            Toast.makeText(this,"saved",Toast.LENGTH_SHORT).show();
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
