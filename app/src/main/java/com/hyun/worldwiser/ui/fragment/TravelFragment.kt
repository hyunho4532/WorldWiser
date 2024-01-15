package com.hyun.worldwiser.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.TravelAdapter
import com.hyun.worldwiser.adapter.TravelRecommendAdapter
import com.hyun.worldwiser.adapter.TravelSwipeToDeleteCallback
import com.hyun.worldwiser.model.Travel
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.ui.travel.RecommendActivity
import com.hyun.worldwiser.util.AdapterFilter
import com.hyun.worldwiser.viewmodel.DateTimeFormatterViewModel
import java.net.URLEncoder
import java.time.format.DateTimeFormatter

class TravelFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val adapterFilter: AdapterFilter = AdapterFilter()
    private lateinit var travelRecommendAdapter: TravelRecommendAdapter // Declare the adapter at the class level

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_travel, container, false)

        val travelRecommendList = ArrayList<TravelRecommend>()

        view.findViewById<FloatingActionButton>(R.id.fBtn_travel_recommend_insert).setOnClickListener {
            val intent = Intent(requireActivity(), RecommendActivity::class.java)
            startActivity(intent)
        }

        val rvTravelRecommend = view.findViewById<RecyclerView>(R.id.rv_travel_recommend)

        travelRecommendAdapter = TravelRecommendAdapter(requireContext(), travelRecommendList)
        rvTravelRecommend.adapter = travelRecommendAdapter
        rvTravelRecommend.layoutManager = LinearLayoutManager(requireContext())

        db.collection("travelRecommends").get()
            .addOnSuccessListener { querySnapshot ->

                for (document in querySnapshot.documents) {
                    try {
                        val travelRecommendCountry = document["travelRecommendCountry"].toString()
                        val travelRecommendImageUrl = document["travelRecommendImageUrl"].toString()
                        val travelRecommendAloneStatus = document["travelRecommendAloneStatus"].toString()
                        val travelRecommendImpression = document["travelRecommendImpression"].toString()

                        val travelRecommend =
                            TravelRecommend(
                                travelRecommendCountry,
                                travelRecommendImageUrl,
                                travelRecommendAloneStatus,
                                travelRecommendImpression
                            )
                        travelRecommendList.add(travelRecommend)
                    } catch (e: UninitializedPropertyAccessException) {

                    }
                }

                travelRecommendAdapter.notifyDataSetChanged()
            }

        return view
    }
}
