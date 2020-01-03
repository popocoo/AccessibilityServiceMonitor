package com.fadi.forestautoget.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.ComponentName
import android.content.Intent
import android.graphics.Path
import android.graphics.Rect
import android.os.Handler
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo

internal object BDDExecutor {

    private const val DELAY_TIME_SHORT = 500L
    private const val DELAY_TIME_DEFAULT = 1000L
    private const val DELAY_TIME_LONG = 2000L
    private const val DELAY_TIME_LONG_MORE = 5000L

    private val handler = Handler()

    fun start(accessibilityServiceForBDD: AccessibilityServiceForBDD) {
        object : Thread() {
            override fun run() {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val cn = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
                intent.component = cn
                accessibilityServiceForBDD.startActivity(intent)

                sleep(DELAY_TIME_LONG)
                printAllChild(accessibilityServiceForBDD.rootInActiveWindow)

                scroll(accessibilityServiceForBDD, 540F, 300F, 540F, 1800F)

                sleep(DELAY_TIME_LONG)
                printAllChild(accessibilityServiceForBDD.rootInActiveWindow)

                val node = findViewByText(accessibilityServiceForBDD.rootInActiveWindow, "CPU数据库")
                if (node != null) {
                    val rect = Rect()
                    node.getBoundsInScreen(rect)
                    click(accessibilityServiceForBDD, rect.centerX().toFloat(), rect.centerY().toFloat())
                }

                sleep(DELAY_TIME_LONG_MORE)
                printAllChild(accessibilityServiceForBDD.rootInActiveWindow)

            }
        }.start()
    }


    private fun click(accessibilityService: AccessibilityService, x: Float, y: Float) {
        val path = Path()
        path.moveTo(x, y)
        val des = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path, DELAY_TIME_SHORT, DELAY_TIME_SHORT)).build()
        handler.post {
            accessibilityService.dispatchGesture(des, object : AccessibilityService.GestureResultCallback() {}, null)
        }
    }

    private fun scroll(accessibilityService: AccessibilityService, x: Float, y: Float, xTo: Float, yTo: Float) {
        val path = Path()
        path.moveTo(x, y)
        path.lineTo(xTo, yTo)
        val des = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path, DELAY_TIME_SHORT, DELAY_TIME_SHORT)).build()
        handler.post {
            accessibilityService.dispatchGesture(des, object : AccessibilityService.GestureResultCallback() {}, null)
        }

    }

    private fun findViewByText(nodeInfo: AccessibilityNodeInfo?, str: String): AccessibilityNodeInfo? {
        if (nodeInfo == null) return null
        if (nodeInfo.text != null &&nodeInfo.text.contains(str)) {
            Log.e("dcdc", "findViewByText: $str\nView: $nodeInfo\n")
            return nodeInfo
        }
        for (i in 0 until nodeInfo.childCount) {
            findViewByText(nodeInfo.getChild(i), str)
        }
        return null
    }


    private fun printAllChild(nodeInfo: AccessibilityNodeInfo?) {
        if (nodeInfo == null) return
        Log.e("dcdc", nodeInfo.toString() + "\n")
        for (i in 0 until nodeInfo.childCount) {
            printAllChild(nodeInfo.getChild(i))
        }
    }


    fun policy(nodeInfo: AccessibilityNodeInfo, packageName: String, className: String) {

        if ("com.tencent.mm" != packageName) {
            return
        }

        printAllChild(nodeInfo)

        // 该界面下所有 ViewId 节点
        //        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b6a");
        //        for (int i = 0; i < list.size() ; i++) {
        //            if (i == 0) {
        //                // 防止点赞自己，跳转到其他界面
        //                continue;
        //            }
        //
        //            if (list.get(i).isClickable()) {
        //                list.get(i).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        //                Log.d(Config.TAG, "clickBtnByResId = " + list.get(i).toString());
        //            }
        //        }
    }
}
