package com.hyun.worldwiser.ui.login

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityLoginBinding
import com.hyun.worldwiser.ui.MainActivity
import com.hyun.worldwiser.ui.register.verification.VerificationActivity
import com.hyun.worldwiser.util.IntentFilter
import com.hyun.worldwiser.util.SnackBarFilter
import com.hyun.worldwiser.viewmodel.AuthLoginViewModel
import com.hyun.worldwiser.viewmodel.AuthRegisterViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val intentFilter: IntentFilter = IntentFilter()
    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private lateinit var context: Context

    private val mainActivity: MainActivity = MainActivity()
    private lateinit var authLoginViewModel: AuthLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        authLoginViewModel = ViewModelProvider(this)[AuthLoginViewModel::class.java]

        context = applicationContext

        loginBinding.btnLoginInsert.setOnClickListener {
            val email = loginBinding.etEmailFormField.text.toString()
            val password = loginBinding.etPasswordFormField.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                authLoginViewModel.loginUsers(email, password)
            }
        }

        authLoginViewModel.loginResult.observe(this) { success ->
            if (success) {
                snackBarFilter.getEmailInsertSnackBar(loginBinding.root)
                intentFilter.getIntent(applicationContext, mainActivity)
            } else {
                snackBarFilter.getEmailNotInsertSnackBar(loginBinding.root)
            }
        }
    }
}