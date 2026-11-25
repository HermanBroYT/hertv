package com.hertv.app

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnWifi).setOnClickListener { open(Settings.ACTION_WIFI_SETTINGS) }
        findViewById<Button>(R.id.btnBt).setOnClickListener { open(Settings.ACTION_BLUETOOTH_SETTINGS) }
        // Системные настройки сети: там включаются VPN и Private DNS
        findViewById<Button>(R.id.btnBypass).setOnClickListener { open(Settings.ACTION_WIRELESS_SETTINGS) }

        val et = findViewById<EditText>(R.id.etUrl)
        findViewById<Button>(R.id.btnDownload).setOnClickListener {
            val url = et.text.toString().trim()
            if (!url.startsWith("https://")) {
                Toast.makeText(this, "Нужна https-ссылка", Toast.LENGTH_SHORT).show(); return@setOnClickListener
            }
            val req = DownloadManager.Request(Uri.parse(url))
                .setTitle("HerTV")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalFilesDir(this, "HerTV", Uri.parse(url).lastPathSegment ?: "file")
            (getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager).enqueue(req)
            Toast.makeText(this, "Загрузка началась", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btnDownloads).setOnClickListener { open(DownloadManager.ACTION_VIEW_DOWNLOADS) }
    }

    private fun open(action: String) = startActivity(Intent(action))
}
