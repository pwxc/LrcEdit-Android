package com.lcz.lrcedit.lrcedit;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
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


import com.lcz.lrcedit.lrcedit.other.MyToast;
import com.lcz.lrcedit.lrcmoudle.TxtEditor;
import com.lcz.lrcedit.lrcmoudle.LrcString;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditTxtActivity extends AppCompatActivity {

    private TxtEditor txtEditor;
    private Button addButton;
    private EditText editText;
    private String fileName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edittxt_activity);
        Toolbar toolbar = (Toolbar)findViewById(R.id.editTxt_toolBar);
        setSupportActionBar(toolbar);
        txtEditor = (TxtEditor)findViewById(R.id.editTxt_txtEditor);
        addButton = (Button)findViewById(R.id.editTxt_button_add);
        editText = (EditText) findViewById(R.id.editTxt_editText);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = editText.getText().toString();
                if(!string.isEmpty()){
                    txtEditor.addLrcString(string);
                    editText.getText().clear();
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.edittxt_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.editTxt_toolBar_save:
                mySave();
                break;
            case R.id.editTxt_toolBar_addTime:
                if(!txtEditor.isSaved()){
                    MyToast.showToast(this,"请先保存");
                }
                if(!txtEditor.getLrcStrings().isEmpty()){
                    ArrayList<String> tempArraylist = new ArrayList<>();
                    Intent intent = new Intent(EditTxtActivity.this, EditLrcActivity.class);
                    for(LrcString lrcString: txtEditor.getLrcStrings()){
                        tempArraylist.add(lrcString.getText());
                    }
                    intent.putStringArrayListExtra("key",tempArraylist);
                    startActivity(intent);
                }
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
                        if(ContextCompat.checkSelfPermission(EditTxtActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(EditTxtActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }else if(ContextCompat.checkSelfPermission(EditTxtActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED){
                            ActivityCompat.requestPermissions(EditTxtActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
        List<LrcString> lrcList = txtEditor.getLrcStrings();
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
            MyToast.showToast(this,"已保存为"+Environment.getExternalStorageDirectory()+"/lrcEdit/"+fileName+fileType);
            txtEditor.setSaved(true);
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
                    MyToast.showToast(this,"You denied the permission");
                }
                break;
            default:
        }
    }

}
