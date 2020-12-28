package com.harsh.pip


import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar


class MyPipActivity : PipActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_pip)

        findViewById<Button>(R.id.button_launch_pip_mode).setOnClickListener {
            switchToPipMode()
        }

        val webview = findViewById<WebView>(R.id.webview_pip_mode)
        webview.isScrollContainer = false
        webview.loadUrl("file:///android_asset/anim_pip.gif")

    }

    override fun getViewForPipParams(): View {
        return findViewById<ImageView>(R.id.webview_pip_mode) as View
    }

    override fun shouldSwitchToPipMode() = true

    override fun showPipModeView() {
        findViewById<Button>(R.id.button_launch_pip_mode).visibility = View.INVISIBLE
        findViewById<Toolbar>(R.id.my_toolbar).visibility = View.GONE
    }

    override fun showFullScreenView() {
        findViewById<Button>(R.id.button_launch_pip_mode).visibility = View.VISIBLE
        findViewById<Toolbar>(R.id.my_toolbar).visibility = View.VISIBLE
    }
}