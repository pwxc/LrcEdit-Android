package com.lcz.lrcedit.lrcedit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lcz.lrcedit.lrcmoudle.LrcView;

public class EditActivity extends AppCompatActivity {

    private LrcView mLrcView;// 我们的自定义View
    private int radiu = 0;// 半径值


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mLrcView = findViewById(R.id.lrc_view);
        mLrcView.loadLrc("chengdu.lrc", this);
        mLrcView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLrcView.nextLine();
            }
        });
    }

}
