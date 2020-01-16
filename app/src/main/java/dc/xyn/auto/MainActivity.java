package dc.xyn.auto;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dc.xyn.auto.service.AccessibilityServiceForBDD;
import dc.xyn.auto.util.AccessibilitUtil;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        boolean isNeedRequest = false;
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissionRequestList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
            int check = checkSelfPermission(permissionRequestList[0]);
            if (check != PackageManager.PERMISSION_GRANTED)
                isNeedRequest = true;
            if (isNeedRequest) {
                requestPermissions(permissionRequestList, 10998);
            }
        }
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
                finish();
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
