package com.muneeb_dev.vpn_app.AppUtils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.view.WindowCompat
import com.muneeb_dev.vpn_app.AppUtils.Constants_.ISPREMUM_USER
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//////---------------- Global Valuse ------------------/////////////////////
//var isReceiverRegistered = false // Tracks if InnerReceiver is registered
//var isPowerReceiverRegistered = false // Tracks if PowerButtonReceiver is registered
//var isLockScreenActive = false // Tracks if LockActivity (MainActivity) is active
var isLockScreenAppear = false // Tracks if LockActivity (MainActivity) is active
var isAuthenticationRequired = false





//////---------------- Extensions ------------------/////////////////////
fun Activity.updateStatusBarColor(@ColorInt color: Int, isIconDark: Boolean) {
    // Set the status bar color for Android 5.0 (API 21) and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.statusBarColor = color
    }

    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
            // For Android 11 (API 30) and above
            val windowInsetsController = window.insetsController
            if (windowInsetsController != null) {
                if (isIconDark) {
                    // Set dark icons
                    windowInsetsController.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                } else {
                    // Set light icons
                    windowInsetsController.setSystemBarsAppearance(
                        0,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                }
            }
        }

        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
            // For Android 6.0 (API 23) to Android 10 (API 29)
            val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            windowInsetsController.isAppearanceLightStatusBars = isIconDark
        }

        else -> {
            // For older versions (pre-API 23), we cannot change icon color directly
            // Instead, we can only set the status bar color if API 21 or higher.
            if (!isIconDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Set a dark status bar color for better contrast with light icons
                window.statusBarColor = color
            }
        }
    }
}



val Context.fadeTransitionOptions: ActivityOptions?
    get() =
        ActivityOptions.makeCustomAnimation(
            this,
            android.R.anim.fade_in, // Replace with R.anim.fade_in for custom animations
            android.R.anim.fade_out // Replace with R.anim.fade_out for custom animations
        )


fun Context.checkIsPremiumUser(): Boolean {
    return TinyDB(this).getBoolean(ISPREMUM_USER, false)
}


//////---------------- Extensions ------------------/////////////////////

fun formatTimestampToDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}

fun openUrl(activity: Activity,url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    if (intent.resolveActivity(activity.packageManager) != null) {
        activity.startActivity(intent)
    } else {
        // Handle the case where no browser is available
        Toast.makeText(activity, "No browser found to open the link.", Toast.LENGTH_SHORT).show()
    }
}

fun shareApp(activity: Activity) {
    val packageName = activity.packageName
    val playStoreUrl = "https://play.google.com/store/apps/details?id=$packageName"
    val shareText = "Check out this amazing app: $playStoreUrl"
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, shareText)
    }
    if (intent.resolveActivity(activity.packageManager) != null) {
        activity.startActivity(Intent.createChooser(intent, "Share App"))
    } else {
        // Handle the case where no sharing app is available
        Toast.makeText(activity, "No app found to share the link.", Toast.LENGTH_SHORT).show()
    }
}



