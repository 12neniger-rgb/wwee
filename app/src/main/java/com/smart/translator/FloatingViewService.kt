package com.smart.translator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.core.app.NotificationCompat
import com.smart.translator.databinding.OverlayLayoutBinding

class FloatingViewService : Service() {
    private lateinit var wm: WindowManager
    private var floatingView: View? = null
    private lateinit var binding: OverlayLayoutBinding

    override fun onCreate() {
        super.onCreate()
        wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val inflater = LayoutInflater.from(this)
        binding = OverlayLayoutBinding.inflate(inflater)
        floatingView = binding.root

        val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        else WindowManager.LayoutParams.TYPE_PHONE

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.END
        params.x = 50; params.y = 200
        wm.addView(floatingView, params)

        binding.bubbleIcon.setOnTouchListener(object: View.OnTouchListener {
            var initX=0; var initY=0; var touchX=0f; var touchY=0f
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> { initX=params.x; initY=params.y; touchX=event.rawX; touchY=event.rawY; return true }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initX + (event.rawX - touchX).toInt()
                        params.y = initY + (event.rawY - touchY).toInt()
                        try{ wm.updateViewLayout(floatingView, params) }catch(_:Exception){}
                        return true
                    }
                    MotionEvent.ACTION_UP -> { return true }
                }
                return false
            }
        })

        binding.btnTranslate.setOnClickListener {
            val text = binding.editText.text.toString()
            if (text.isNotBlank()) {
                binding.txtResult.text = "Перевожу..."
                binding.txtResult.text = "(пример) Перевод: " + text
            }
        }

        startForegroundCompat()
    }

    private fun startForegroundCompat() {
        val channelId = "smart_translator_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan = NotificationChannel(channelId, "Smart Translator", NotificationManager.IMPORTANCE_LOW)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(chan)
        }
        val n = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Умный переводчик")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
        startForeground(101, n)
    }

    override fun onDestroy() {
        try { if (floatingView!=null) wm.removeViewImmediate(floatingView) } catch(_:Exception) {}
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
