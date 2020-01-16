package dc.xyn.auto.service

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

    lateinit var accessibilityServiceForBDD: AccessibilityServiceForBDD

    fun start(accessibilityServiceForBDD: AccessibilityServiceForBDD) {
        object : Thread() {
            override fun run() {
                accessibilityServiceForBDD.apply {
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_LAUNCHER)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    val cn = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
                    intent.component = cn
                    startActivity(intent)

    //                sleep(DELAY_TIME_LONG)
    //                printAllChild(accessibilityServiceForBDD.rootInActiveWindow)
    //                scroll(accessibilityServiceForBDD, 540F, 300F, 540F, 1800F)

                    while (true) {
                        Log.e("dcdc", "BDD running...")
                        sleep(DELAY_TIME_LONG)

                        var node = findViewByText(rootInActiveWindow, "CPU数据库")
                        if(node !=null && node.isVisibleToUser) {
//                            clickNodeOrParent(node)
                            clickNodePosition(node)
                        }
                        node = findViewByText(rootInActiveWindow, "CPU性能对比")
                        if(node !=null && node.isVisibleToUser) {
                            clickNodePosition(node)
//                            clickNodeOrParent(node)
                        }
                    }
                }
            }
        }.start()
    }

    private fun clickNodeOrParent(nodeInfo: AccessibilityNodeInfo?) {
        nodeInfo?: return
        if (nodeInfo.isClickable)
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
        else clickNodeOrParent(nodeInfo.parent)
    }

    private fun clickNodePosition (nodeInfo: AccessibilityNodeInfo) {
        val rect = Rect()
        nodeInfo.getBoundsInScreen(rect)
        clickPosition(rect.centerX().toFloat(), rect.centerY().toFloat())
    }

    private fun clickPosition(x: Float, y: Float) {
        val path = Path()
        path.moveTo(x, y)
        val des = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path, DELAY_TIME_SHORT, DELAY_TIME_SHORT)).build()
        handler.post {
            accessibilityServiceForBDD.dispatchGesture(des, object : AccessibilityService.GestureResultCallback() {}, null)
        }
    }

    private fun scroll(x: Float, y: Float, xTo: Float, yTo: Float) {
        val path = Path()
        path.moveTo(x, y)
        path.lineTo(xTo, yTo)
        val des = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(path, DELAY_TIME_SHORT, DELAY_TIME_SHORT)).build()
        handler.post {
            accessibilityServiceForBDD.dispatchGesture(des, object : AccessibilityService.GestureResultCallback() {}, null)
        }

    }

    fun findViewByText(str: String): AccessibilityNodeInfo? {
        return findViewByText(accessibilityServiceForBDD.rootInActiveWindow, str)
    }

    fun findViewByText(nodeInfo: AccessibilityNodeInfo?, str: String): AccessibilityNodeInfo? {
        if (nodeInfo == null) return null
        if (nodeInfo.text != null &&nodeInfo.text.contains(str)) {
            Log.e("dcdc", "findViewByText: $str\nView: $nodeInfo\n")
            return nodeInfo
        }
        for (i in 0 until nodeInfo.childCount) {
            val node = findViewByText(nodeInfo.getChild(i), str)
            if (node != null)
                return node
        }
        return null
    }

    private val temp: StringBuilder = StringBuilder()

    fun printAll(): String {
        Log.e("dcdc", "printAll\n")
        printAllChild(accessibilityServiceForBDD.rootInActiveWindow)
        val temp2 = temp.toString()
        temp.clear()
        return temp2
    }

    private fun printAllChild(nodeInfo: AccessibilityNodeInfo?) {
        if (nodeInfo == null) return
        Log.e("dcdc", nodeInfo.toString() + "\n")
        temp.append(nodeInfo.toString() + "\n")
        for (i in 0 until nodeInfo.childCount) {
            printAllChild(nodeInfo.getChild(i))
        }
    }

}
