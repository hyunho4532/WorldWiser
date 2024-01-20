package com.hyun.worldwiser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.TravelRecommendAdapter
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.ui.travel.RecommendActivity

class TravelFragment : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
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
                        val travelRecommendAuthUid = document["travelRecommendAuthUid"].toString()
                        val travelRecommendAuthNickname = document["travelRecommendNickname"].toString()
                        val travelRecommendCountry = document["travelRecommendCountry"].toString()
                        val imageUrlsList = document["travelRecommendImageUrls"] as? List<*>
                        val imageUrlList = document["travelRecommendImageUrl"].toString()
                        val travelRecommendAloneStatus = document["travelRecommendAloneStatus"].toString()
                        val travelRecommendImpression = document["travelRecommendImpression"].toString()
                        val travelRecommendFavoriteCount = Integer.parseInt(document["travelRecommendFavoriteCount"].toString())

                        Log.d("TravelFragmentImageUrlCheck", imageUrlsList!!.size.toString())

                        val imageUrlNullCheck = when {
                            imageUrlsList == null || imageUrlsList.isEmpty() -> imageUrlList
                            imageUrlsList.size >= 2 -> {
                                val imageFirstUrl = imageUrlsList[0].toString()
                                val imageSecondUrl = imageUrlsList[1].toString()

                                Log.d("TravelFragmentImageUrl", imageFirstUrl)
                                Log.d("TravelFragmentImageUrl", imageSecondUrl)

                                "$imageFirstUrl, $imageSecondUrl"
                            }
                            else -> imageUrlsList[0].toString()
                        }

                        Log.d("TravelFragmentUrlNullCheck", imageUrlNullCheck)

                        val travelRecommend =
                            TravelRecommend(
                                travelRecommendAuthUid,
                                travelRecommendAuthNickname,
                                travelRecommendCountry,
                                imageUrlNullCheck,
                                imageUrlNullCheck,
                                travelRecommendAloneStatus,
                                travelRecommendImpression,
                                travelRecommendFavoriteCount
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
