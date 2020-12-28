package com.harsh.pip

import android.annotation.TargetApi
import android.app.PictureInPictureParams
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.util.Rational
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager

abstract class PipActivity : AppCompatActivity() {

    var isRunningInPipMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        switchToPipMode()
    }

    override fun onStop() {
        super.onStop()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastToFinishPipMode)
        finishIfRunningInPipMode()
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration?
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        isRunningInPipMode = isInPictureInPictureMode

        when (isInPictureInPictureMode) {
            true -> showPipModeView()
            else -> showFullScreenView()
        }

    }

    @TargetApi(26)
    fun switchToPipMode() {

        val isActivityResumed = lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)

        if (BuildConfig.FEATURE_PIP_ENABLED
            && isPipSupported()
            && isActivityResumed
            && shouldSwitchToPipMode()
        ) {
            LocalBroadcastManager.getInstance(this).registerReceiver(
                broadcastToFinishPipMode, IntentFilter(
                    ACTION_FINISH_PIP_MODE
                )
            )
            enterPictureInPictureMode(getPipParams())
        }

    }

    @TargetApi(26)
    private fun getPipParams() = PictureInPictureParams.Builder()
        .setAspectRatio(Rational(getViewForPipParams().width, getViewForPipParams().height)).build()


    fun finishIfRunningInPipMode() {
        if (isRunningInPipMode) {
            finish()
        }
    }

    private val broadcastToFinishPipMode = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            finishIfRunningInPipMode()
        }
    }

    /**
     * Implement this method to return the view to calculate height and width for PIP window.
     *
     * @return view - View
     */
    abstract fun getViewForPipParams(): View

    /**
     * Implement this method to decide when to switch in PIP mode.
     *
     * @return boolean - true to switch in pip mode, otherwise not.
     */
    abstract fun shouldSwitchToPipMode(): Boolean

    /**
     * Implement this method handle UI for PIP window.
     * Hide all views which are not required to be shown in PIP window.
     */
    abstract fun showPipModeView()

    /**
     * Implement this method to handle fullscreen mode, when user switches back to fullscreen.
     * Show all views again which were hidden during PIP mode.
     */
    abstract fun showFullScreenView()


    companion object {
        /**
         * Use this broadcast action to finish PIP mode when it is not needed.
         * For example - When user clicks on app icon while PIP window is running.
         */
        val ACTION_FINISH_PIP_MODE = "ACTION_FINISH_PIP_MODE"
    }

}