package com.hyun.worldwiser.ui.login

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityLoginBinding
import com.hyun.worldwiser.ui.login.verification.VerificationActivity
import com.hyun.worldwiser.util.IntentFilter
import com.hyun.worldwiser.util.SnackBarFilter
import com.hyun.worldwiser.viewmodel.AuthRegisterViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val intentFilter: IntentFilter = IntentFilter()
    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private lateinit var context: Context

    private val verificationActivity: VerificationActivity = VerificationActivity()
    private lateinit var authRegisterViewModel: AuthRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        authRegisterViewModel = ViewModelProvider(this)[AuthRegisterViewModel::class.java]

        context = applicationContext

        loginBinding.btnLoginInsert.setOnClickListener {
            val email = loginBinding.etEmailFormField.text.toString()
            val password = loginBinding.etPasswordFormField.text.toString()

            authRegisterViewModel.registerUsers(email, password)
        }

        authRegisterViewModel.loginResult.observe(this) { success ->
            if (success) {
                snackBarFilter.getEmailInsertSnackBar(loginBinding.root)
                intentFilter.getIntent(context, verificationActivity)
            } else {
                snackBarFilter.getEmailNotInsertSnackBar(loginBinding.root)
            }
        }
    }
}