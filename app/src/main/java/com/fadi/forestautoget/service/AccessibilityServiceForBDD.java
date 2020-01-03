package com.fadi.forestautoget.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;


public class AccessibilityServiceForBDD extends AccessibilityService {

    private static final String TAG = AccessibilityServiceForBDD.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (null == intent) {
            return super.onStartCommand(intent, flags, startId);
        }

        String action = intent.getAction();
        Log.d(TAG, "onStartCommand Aciton: " + action);

        BDDExecutor.INSTANCE.start(this);

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        AccessibilityServiceInfo serviceInfo = new AccessibilityServiceInfo();
        serviceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
        serviceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        serviceInfo.packageNames = new String[]{"com.gotokeep.keep", "com.eg.android.AlipayGphone", "com.sinovatech.unicom.ui", "com.tencent.mm"};// 监控的app
        serviceInfo.notificationTimeout = 100;
        serviceInfo.flags = serviceInfo.flags | AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY;
        setServiceInfo(serviceInfo);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
//        int eventType = event.getEventType();
//        String packageName = event.getPackageName().toString();
//        String className = event.getClassName().toString();
//        Log.e("dcdc","packageName = " + packageName + ", className = " + className);
//
//        Log.e("dcdc", "eventType: " + eventType);
//        switch (eventType) {
//            case AccessibilityEvent.TYPE_VIEW_FOCUSED:
//            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
//            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
//                    BDDExecutor.policy(getRootInActiveWindow(), packageName, className);
//
//            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
//            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
//            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_END:
//            case AccessibilityEvent.TYPE_TOUCH_EXPLORATION_GESTURE_START:
//            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_END:
//            case AccessibilityEvent.TYPE_TOUCH_INTERACTION_START:
//            case AccessibilityEvent.TYPE_VIEW_HOVER_ENTER:
//            case AccessibilityEvent.TYPE_VIEW_HOVER_EXIT:
//            case AccessibilityEvent.TYPE_ANNOUNCEMENT:
//            case AccessibilityEvent.TYPE_ASSIST_READING_CONTEXT:
//            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
//            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED:
//            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED:
//            case AccessibilityEvent.TYPE_VIEW_CLICKED:
//            case AccessibilityEvent.TYPE_VIEW_CONTEXT_CLICKED:
//            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
//            case AccessibilityEvent.TYPE_VIEW_SELECTED:
//            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
//            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
//            case AccessibilityEvent.TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY:
//            case AccessibilityEvent.TYPE_WINDOWS_CHANGED:
//            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
//                break;
//        }
    }

    @Override
    public void onInterrupt() {

    }

}
