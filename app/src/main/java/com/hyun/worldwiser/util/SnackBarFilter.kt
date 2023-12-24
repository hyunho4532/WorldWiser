package com.hyun.worldwiser.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackBarFilter {
    fun getSnackBar(view: View) {
        val snackBar = Snackbar.make(view, "계정이 정상적으로 등록되었습니다", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }

    fun getCountryEmptySnackBar(view: View) {
        val snackBar = Snackbar.make(view, "좋아하는 나라를 입력해주세요.", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }
}