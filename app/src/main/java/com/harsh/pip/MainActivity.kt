package com.harsh.pip

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LocalBroadcastManager.getInstance(this)
            .sendBroadcast(Intent(PipActivity.ACTION_FINISH_PIP_MODE))

        findViewById<Button>(R.id.pip_activity_button).setOnClickListener {
            startActivity(Intent(this, MyPipActivity::class.java))
        }
    }

}
