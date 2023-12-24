package com.hyun.worldwiser.ui.login.verification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryAdapter
import com.hyun.worldwiser.model.Country
import com.hyun.worldwiser.util.SnackBarFilter

class VerificationActivity : AppCompatActivity() {

    private var countryList = arrayListOf<Country>()

    private val snackBarFilter: SnackBarFilter = SnackBarFilter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertification)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val etCountryTextFormField = findViewById<EditText>(R.id.et_country_text_form_field)
        val btnCountryInsert = findViewById<Button>(R.id.btn_country_insert)

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
    }
}