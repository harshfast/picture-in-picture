package com.harsh.pip

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build

/**
 * Call this method to know, if Picture In Picture mode supported by the device.
 */
fun Activity.isPipSupported() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
        packageManager.hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)