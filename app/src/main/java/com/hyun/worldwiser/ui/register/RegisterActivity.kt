package com.hyun.worldwiser.ui.register

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityRegisterBinding
import com.hyun.worldwiser.ui.login.LoginActivity
import com.hyun.worldwiser.ui.register.verification.VerificationActivity
import com.hyun.worldwiser.util.IntentFilter
import com.hyun.worldwiser.util.SnackBarFilter
import com.hyun.worldwiser.viewmodel.AuthRegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding

    private val intentFilter: IntentFilter = IntentFilter()
    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private lateinit var context: Context

    private val verificationActivity: VerificationActivity = VerificationActivity()
    private val loginActivity: LoginActivity = LoginActivity()

    private lateinit var authRegisterViewModel: AuthRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        authRegisterViewModel = ViewModelProvider(this)[AuthRegisterViewModel::class.java]

        context = applicationContext

        registerBinding.btnRegisterInsert.setOnClickListener {
            val email = registerBinding.etEmailFormField.text.toString()
            val password = registerBinding.etPasswordFormField.text.toString()

            authRegisterViewModel.registerUsers(email, password)
        }

        registerBinding.loginToMove.setOnClickListener {
            intentFilter.getIntent(context, loginActivity)
        }

        authRegisterViewModel.registerResult.observe(this) { success ->
            if (success) {
                snackBarFilter.getEmailInsertSnackBar(registerBinding.root)
                intentFilter.getIntent(context, verificationActivity)
            } else {
                snackBarFilter.getEmailNotInsertSnackBar(registerBinding.root)
            }
        }
    }
}