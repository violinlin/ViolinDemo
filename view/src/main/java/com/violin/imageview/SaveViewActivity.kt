package com.violin.imageview

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_save_view.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import kotlin.concurrent.thread


class SaveViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_view)


        btn_save.setOnClickListener {
            tv_text.text = System.currentTimeMillis().toString()
            thread {
                saveBitmapToGallery(it.context, viewToBitmap(ll_view_layout))
            }

        }
    }

    fun viewToBitmap(view: View): Bitmap {
        val resultBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.RGB_565)
        val canvas = Canvas(resultBitmap)
        canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return resultBitmap;
    }

    fun saveBitmapToGallery(context: Context, bitmap: Bitmap) {
        val storeFilePath = Environment.getExternalStorageDirectory().absolutePath + File.separator + "Kwai" +File.separator + context.packageName
        val storeFile = File(storeFilePath)
        if (!storeFile.exists()) {
            storeFile.mkdirs()
        }
        val fileName = System.currentTimeMillis().toString() + "-" + SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis()) + ".jpg"
        val targetFile = File(storeFile, fileName)
        val fileOutputStream = FileOutputStream(targetFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()
        val uri = Uri.fromFile(targetFile)
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))


        runOnUiThread {
            Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show()
        }


    }

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, SaveViewActivity::class.java)
            context.startActivity(starter)
        }
    }


}