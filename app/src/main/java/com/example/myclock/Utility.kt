package com.example.myclock

import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class Utility {
    companion object {
        fun print(text: String) {
            Log.d("MY-CLOCK-APP", text)
        }

        fun showKeyboard(context: Context?, view: EditText?) {
            if (view == null) return
            if (view.requestFocus()) {
                // Not working
                // val imm =
                //     context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                // imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
            }
        }
    }
}