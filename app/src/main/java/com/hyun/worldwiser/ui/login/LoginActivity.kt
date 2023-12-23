package com.hyun.worldwiser.ui.login

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.ActivityLoginBinding
import com.hyun.worldwiser.ui.login.verification.VerificationActivity
import com.hyun.worldwiser.util.IntentFilter
import com.hyun.worldwiser.util.SnackBarFilter

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val intentFilter: IntentFilter = IntentFilter()
    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private lateinit var context: Context
    private val verificationActivity: VerificationActivity = VerificationActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        context = applicationContext

        loginBinding.btnLoginInsert.setOnClickListener { view ->
            auth.createUserWithEmailAndPassword(loginBinding.etEmailFormField.text.toString(), loginBinding.etPasswordFormField.text.toString())
                .addOnSuccessListener {
                    snackBarFilter.getSnackBar(view)
                    intentFilter.getIntent(context, verificationActivity)
                }
        }
    }
}