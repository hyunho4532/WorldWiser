package com.hyun.worldwiser.ui.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryRankingAdapter
import com.hyun.worldwiser.adapter.TravelAdapter
import com.hyun.worldwiser.databinding.FragmentHomeBinding
import com.hyun.worldwiser.model.CountryRanking
import com.hyun.worldwiser.model.Travel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val countryRankingList = ArrayList<CountryRanking>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        db.collection("travelInserts").get()
            .addOnSuccessListener { querySnapshot  ->

                for (document in querySnapshot.documents) {
                    val country = document["country"].toString()

                    val countryRanking = CountryRanking(country)

                    countryRankingList.add(countryRanking)

                    val countryRankingAdapter = CountryRankingAdapter(requireContext(), countryRankingList)

                    fragmentHomeBinding.rvTravelRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    fragmentHomeBinding.rvTravelRanking.adapter = countryRankingAdapter

                    countryRankingAdapter.notifyDataSetChanged()
                }
            }

        return fragmentHomeBinding.root
    }
}