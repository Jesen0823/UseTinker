package com.jesen.cod.usetinker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jesen.cod.usetinker.tinker.util.TinkerManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private Button loadPatchBtn;
    private static final String FILE_END = ".apk";
    private String mPatchDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        mPatchDir = getExternalCacheDir().getAbsolutePath() + "/patch/";
        File file = new File(mPatchDir);
        if (file == null || !file.exists()){
            file.mkdir();
        }


    }

    private void initView(){
        loadPatchBtn = findViewById(R.id.load_patch_btn);

        loadPatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPatch();
            }
        });
    }


    private void loadPatch(){
        TinkerManager.loadPatch(getPatchName());
    }

    private String getPatchName(){
        return mPatchDir.concat("demo").concat(FILE_END);
    }
}