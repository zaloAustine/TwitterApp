package com.zalocoders.twitterapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.twitterapp.R



fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
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

fun Fragment.hideSoftInput(){
    val inputmethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputmethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun View.showRetrySnackBar(message: String, action: ((View) -> Unit)?) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)

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



