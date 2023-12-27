package com.hyun.worldwiser.ui.login.verification

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryAdapter
import com.hyun.worldwiser.databinding.ActivityVerificationBinding
import com.hyun.worldwiser.model.Country
import com.hyun.worldwiser.ui.MainActivity
import com.hyun.worldwiser.util.HashMapOfFilter
import com.hyun.worldwiser.util.IntentFilter
import com.hyun.worldwiser.util.SnackBarFilter

class VerificationActivity : AppCompatActivity() {

    private var countryList = arrayListOf<Country>()
    private lateinit var activityVerificationBinding: ActivityVerificationBinding

    private val snackBarFilter: SnackBarFilter = SnackBarFilter()
    private val hashMapOf: HashMapOfFilter = HashMapOfFilter()
    private val intentFilter: IntentFilter = IntentFilter()
    private val mainActivity: MainActivity = MainActivity()

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityVerificationBinding = DataBindingUtil.setContentView(this, R.layout.activity_verification)

        val recyclerView = activityVerificationBinding.recyclerView
        val context: Context = applicationContext

        val countryAdapter = CountryAdapter(this, countryList)

        recyclerView.adapter = countryAdapter

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        activityVerificationBinding.btnCountryInsert.setOnClickListener {
            val countryName = activityVerificationBinding.etCountryTextFormField.text.toString()

            countryList.add(Country(countryName))

            countryAdapter.notifyItemInserted(countryList.size - 1)
        }

        activityVerificationBinding.btnVerificationInsert.setOnClickListener { view ->

            val countriesString = countryList.joinToString(", ") { it.countryFavorite }

            val verification = hashMapOf.insertVerificationDataFromMap (
                countriesString,
                activityVerificationBinding.powerSpinnerView.text.toString(),
                activityVerificationBinding.powerSpinnerView2.text.toString(),
                activityVerificationBinding.etNicknameTextFormField.text.toString()
            )

            db.collection("verifications").document(auth.currentUser!!.uid).set(verification)
                .addOnSuccessListener {
                    snackBarFilter.getVerificationInsertSnackBar(view)
                    intentFilter.getIntent(context, mainActivity)
                }
                .addOnFailureListener {
                    snackBarFilter.getVerificationFailureSnackBar(view)
                }
        }
    }
}