package com.zalocoders.twitterapp.utils

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.twitterapp.R

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun Context.showToast(message: String, length: Int = Toast.LENGTH_LONG) = Toast.makeText(this, message, length).show()



fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun View.showSnackbar(message: String, length: Int) {
    val snackbar = Snackbar.make(this, message, length)

    snackbar.apply {
        setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
        this.setBackgroundTint(ContextCompat.getColor(context, R.color.colorPrimary))
        show()

    }
}



fun View.showErrorSnackbar(message: String, length: Int = Snackbar.LENGTH_LONG) {
    val snackbar = Snackbar.make(this, message, length)

    snackbar.apply {
        this.setBackgroundTint(ContextCompat.getColor(this.context, android.R.color.holo_red_light))
        this.setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
        show()
    }
}

fun View.showSuccessSnackbar(message: String, length: Int) {
    val snackbar = Snackbar.make(this, message, length)

    snackbar.apply {
        this.setBackgroundTint(
                ContextCompat.getColor(view.context, R.color.colorPrimary)
        )
        this.setTextColor(ContextCompat.getColor(this.context, android.R.color.white))
        show()
    }
}

fun Chip.setChipBackgroundColor(context: Context, color: Int) {
    chipBackgroundColor = ColorStateList.valueOf(
            ContextCompat.getColor(context, color)
    )
}

fun Fragment.hideSoftInput(){
    val inputmethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputmethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}


fun Activity.getView(): View {
    return window.decorView.rootView
}


fun View.showRetrySnackBar(message: String, action: ((View) -> Unit)?) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)

    snackBar.apply {
        this.setBackgroundTint(ContextCompat.getColor(this.context, android.R.color.holo_red_light))

        val colorWhite = ContextCompat.getColor(this.context, android.R.color.white)
        this.setTextColor(colorWhite)
        this.setActionTextColor(colorWhite)
        setAction("RETRY") {
            dismiss()
            action?.invoke(this@showRetrySnackBar)
        }
        show()

    }
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT in 21..29) {
        window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

    } else if (Build.VERSION.SDK_INT >= 30) {
        window.statusBarColor = Color.TRANSPARENT
        window.setDecorFitsSystemWindows(false)
    }
}


