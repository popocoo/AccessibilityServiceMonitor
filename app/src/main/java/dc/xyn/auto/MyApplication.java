package dc.xyn.auto;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initValue();
    }

    private void initValue() {
        startAlarmTask(this);
    }

    public static void startAlarmTask(Context mContext) {
//        ShareUtil mShareUtil = new ShareUtil(mContext);
//        int hour = mShareUtil.getInt(Config.KEY_HOUR, 07);
//        int minute = mShareUtil.getInt(Config.KEY_MINUTE,0);
//        Intent intent = new Intent(mContext, AccessibilityServiceForBDD.class);
//        intent.setAction(AccessibilityServiceForBDD.ACTION_ALAM_TIMER);
//        AlarmTaskUtil.starRepeatAlarmTaskByService(mContext, hour, minute, 0, intent);
    }
}
