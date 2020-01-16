package dc.xyn.auto

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Environment
import android.util.Log
import dc.xyn.auto.service.BDDExecutor
import java.io.*

class MyReceiver : BroadcastReceiver() {

    private val socketFile = File("${Environment.getExternalStorageDirectory().path}/bddTest.socket")

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.getStringExtra("find_view_by_text") != null) {
            val node = BDDExecutor.findViewByText(intent.getStringExtra("find_view_by_text"))
            if(node !=null) {
                Log.e("dcdc", "View Visible: ${node.isVisibleToUser}")
                val rect = Rect()
                node.getBoundsInScreen(rect)
                writeToFile(socketFile, "${rect.centerX()} ${rect.centerY()}")
            } else {
                Log.e("dcdc", "View not found")
                writeToFile(socketFile, "View not found")
            }
        } else if (intent?.getStringExtra("find_view_by_id") != null) {
        } else if (intent?.getStringExtra("print_all") != null) {
            val data = BDDExecutor.printAll()
            writeToFile(socketFile, data)
        }
    }

    private fun writeToFile(file: File, content: String) {
        if(socketFile.exists())
            socketFile.delete()
        var out: BufferedWriter? = null
        try {
            out = BufferedWriter(OutputStreamWriter(FileOutputStream(file, true)))
            out.write(content)
            out.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                out?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}