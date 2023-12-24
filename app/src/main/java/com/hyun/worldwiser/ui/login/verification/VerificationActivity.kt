package com.hyun.worldwiser.ui.login.verification

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryAdapter
import com.hyun.worldwiser.model.Country

class VerificationActivity : AppCompatActivity() {

    private var countryList = arrayListOf<Country>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vertification)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        var countryList = arrayListOf (
            Country("일본"),
            Country("대한민국")
        )

        val countryAdapter = CountryAdapter(this, countryList)

        recyclerView.adapter = countryAdapter

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.setHasFixedSize(true)
    }
}