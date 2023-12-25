package com.hyun.worldwiser.ui.login.verification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryAdapter
import com.hyun.worldwiser.model.Country
import com.hyun.worldwiser.util.SnackBarFilter
import com.skydoves.powerspinner.PowerSpinnerView

class VerificationActivity : AppCompatActivity() {

    private var countryList = arrayListOf<Country>()

    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertification)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val etCountryTextFormField = findViewById<EditText>(R.id.et_country_text_form_field)
        val etNicknameTextFormField = findViewById<EditText>(R.id.et_nickname_text_form_field)

        val btnCountryInsert = findViewById<Button>(R.id.btn_country_insert)
        val btnVerificationInsert = findViewById<AppCompatButton>(R.id.btn_verification_insert)

        val powerSpinnerView = findViewById<PowerSpinnerView>(R.id.powerSpinnerView)
        val powerSpinnerView2 = findViewById<PowerSpinnerView>(R.id.powerSpinnerView2)

        val countryAdapter = CountryAdapter(this, countryList)

        recyclerView.adapter = countryAdapter

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)

        btnCountryInsert.setOnClickListener {
            val countryName = etCountryTextFormField.text.toString()

            countryList.add(Country(countryName))

            countryAdapter.notifyItemInserted(countryList.size - 1)
        }

        btnVerificationInsert.setOnClickListener { view ->

            val verification = hashMapOf(
                "country_favorite" to countryList.toString(),
                "travel_preferences" to powerSpinnerView.text.toString(),
                "transport" to powerSpinnerView2.text.toString(),
                "nickname" to etNicknameTextFormField.text.toString()
            )

            db.collection("verifications").document(auth.currentUser!!.uid).set(verification)
                .addOnSuccessListener {
                    snackBarFilter.getVerificationInsertSnackBar(view)
                }
                .addOnFailureListener {
                    snackBarFilter.getVerificationFailureSnackBar(view)
                }
        }
    }
}