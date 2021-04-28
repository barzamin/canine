package `in`.imer.canine.ime

import `in`.imer.canine.R
import android.inputmethodservice.InputMethodService
import android.view.LayoutInflater
import android.view.View

class CanineIME : InputMethodService() {
    override fun onCreateInputView(): View? {
        val inputView = LayoutInflater.from(this).inflate(R.layout.ime, null)

        return inputView
    }
}