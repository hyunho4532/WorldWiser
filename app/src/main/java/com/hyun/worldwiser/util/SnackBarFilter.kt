package com.hyun.worldwiser.util

import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class SnackBarFilter {
    fun getEmailInsertSnackBar(view: View) {
        val snackBar = Snackbar.make(view, "계정이 정상적으로 등록되었습니다.", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }

    fun getVerificationInsertSnackBar(view: View) {
        val snackBar = Snackbar.make(view, "정보가 정상적으로 등록되었습니다.", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }

    fun getEmailNotInsertSnackBar(view: View) {
        val snackBar = Snackbar.make(view, "이미 존재하는 계정입니다.", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }

    fun getVerificationFailureSnackBar(view: View) {
        val snackBar = Snackbar.make(view, "정보를 받아오는 중 에러가 발생하였습니다.", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }

    fun getCountryEmptySnackBar(view: View) {
        val snackBar = Snackbar.make(view, "좋아하는 나라를 입력해주세요.", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }

    fun removeCountrySnackBar(view: View, country: String) {
        val snackBar = Snackbar.make(view, "나라: $country 를 삭제하였습니다.", Snackbar.LENGTH_SHORT)

        snackBar.show()
    }
}