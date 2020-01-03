package com.fadi.forestautoget;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fadi.forestautoget.service.AccessibilityServiceForBDD;
import com.fadi.forestautoget.util.AccessibilitUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void initView() {
        btnSettings = findViewById(R.id.btn_settings);
        findViewById(R.id.btn_bdd).setOnClickListener(this);
    }

    private void initListener() {
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_settings:
                AccessibilitUtil.showSettingsUI(this);
                break;
            case R.id.btn_bdd:
                startService();
                break;
        }
    }

    private void updateUI() {
        if (AccessibilitUtil.isAccessibilitySettingsOn(this, AccessibilityServiceForBDD.class.getCanonicalName())) {
            btnSettings.setEnabled(false);
        } else {
            btnSettings.setEnabled(true);
        }

    }

    private void startService() {
        Intent mIntent = new Intent(this, AccessibilityServiceForBDD.class);
        startService(mIntent);
    }

}
