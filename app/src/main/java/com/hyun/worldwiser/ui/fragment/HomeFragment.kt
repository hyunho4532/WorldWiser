package com.hyun.worldwiser.ui.fragment

import TourApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.api.Distribution.BucketOptions.Linear
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.GsonBuilder
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.CountryRankingAdapter
import com.hyun.worldwiser.adapter.HomeTravelRecommendAdapter
import com.hyun.worldwiser.adapter.TourSpotsAdapter
import com.hyun.worldwiser.adapter.TravelStatusAdapter
import com.hyun.worldwiser.databinding.FragmentHomeBinding
import com.hyun.worldwiser.model.CountryRanking
import com.hyun.worldwiser.model.HomeTravelRecommend
import com.hyun.worldwiser.model.TravelStatus
import com.hyun.worldwiser.model.spots.Root
import com.hyun.worldwiser.ui.travel.InsertActivity
import com.hyun.worldwiser.util.HomeFragmentTitleFilter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding: FragmentHomeBinding

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val countryRankingList = ArrayList<CountryRanking>()
    private val travelStatusList = ArrayList<TravelStatus>()
    private val travelRecommendList = ArrayList<HomeTravelRecommend>()

    private val uniqueCountries = HashSet<String>()

    val gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://apis.data.go.kr/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val tourApiService = retrofit.create(TourApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        fragmentHomeBinding.btnTravelInsert.setOnClickListener {
            val intent = Intent(requireActivity(), InsertActivity::class.java)
            startActivity(intent)
        }

        val homeFragmentTitleFilter: HomeFragmentTitleFilter = HomeFragmentTitleFilter(fragmentHomeBinding)
        homeFragmentTitleFilter.homeFragmentTitleSettings()

        db.collection("travelRecommends")
            .get()
            .addOnSuccessListener { querySnapshot  ->

                for (document in querySnapshot.documents) {
                    val travelRecommendCountry = document["travelRecommendCountry"].toString()
                    val travelRecommendNickname = document["travelRecommendNickname"].toString()

                    if (travelRecommendCountry.isNotEmpty()) {

                        travelRecommendList.add(HomeTravelRecommend(travelRecommendCountry, travelRecommendNickname))
                    }
                }

                val homeTravelRecommendAdapter = HomeTravelRecommendAdapter(requireContext(), travelRecommendList)

                fragmentHomeBinding.rvRecommendStatus.adapter = homeTravelRecommendAdapter
                fragmentHomeBinding.rvRecommendStatus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

        db.collection("travelInserts")
            .get()
            .addOnSuccessListener { querySnapshot  ->

                val countryCountMap = HashMap<String, Int>()

                for (document in querySnapshot.documents) {
                    val country = document["country"].toString()

                    val count = countryCountMap.getOrDefault(country, 0)
                    countryCountMap[country] = count + 1

                    uniqueCountries.add(country)
                }

                countryRankingList.clear()

                countryCountMap.forEach { (country, count) ->
                    countryRankingList.add(CountryRanking(country, count))
                }

                countryRankingList.sortByDescending {
                    it.countryRankingCount
                }

                val countryRankingAdapter = CountryRankingAdapter(requireContext(), countryRankingList)

                fragmentHomeBinding.rvTravelRanking.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                fragmentHomeBinding.rvTravelRanking.adapter = countryRankingAdapter
            }

        db.collection("verifications")
            .document(auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document  ->

                val favoriteCountry = document["country_favorite"].toString()
                val nickname = document["nickname"].toString()
                val transport = document["transport"].toString()

                fragmentHomeBinding.tvTravelInformationNickname.text = nickname
                fragmentHomeBinding.tvTravelInformationFavoriteCountry.text = favoriteCountry
                fragmentHomeBinding.tvTravelInformationPreferenceTransport.text = transport

                (nickname + "의 관한 여행 정보").also { nicknameResult ->
                    fragmentHomeBinding.tvTravelInformation.text = nicknameResult
                }
            }

        db.collection("travelInserts").whereEqualTo("authUid", auth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot  ->

                val documentCount = querySnapshot.size()

                for (document in querySnapshot.documents) {

                    (documentCount.toString() + "개").also { documentCountResult ->
                        fragmentHomeBinding.tvTravelInformationCount.text = documentCountResult }
                }
            }


        db.collection("travelInserts")
            .get()
            .addOnSuccessListener { querySnapshot  ->

                val travelStatusCountMap = HashMap<String, Int>()

                for (document in querySnapshot.documents) {
                    val countryStatus = document["countryStatus"].toString()

                    val count = travelStatusCountMap.getOrDefault(countryStatus, 0)
                    travelStatusCountMap[countryStatus] = count + 1

                    uniqueCountries.add(countryStatus)
                }

                travelStatusList.clear()

                travelStatusCountMap.forEach { (country, count) ->
                    travelStatusList.add(TravelStatus(country, count))
                }

                travelStatusList.sortByDescending {
                    it.countryStatusCount
                }

                val travelStatusAdapter = TravelStatusAdapter(requireContext(), travelStatusList)

                fragmentHomeBinding.rvTravelStatus.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                fragmentHomeBinding.rvTravelStatus.adapter = travelStatusAdapter
            }

        tourApiService.getTourSpots (
            serviceKey = "KmXwF4GXnRJiiNY68ky5tSl88Zi3IsotZW3VlDC%2BEGf472pLAf%2FgWmsnJDq9d22bOLATJFTTixhypw6BuSDJug%3D%3D", numOfRows = 10,
            pageNo = 1, mobileOS = "ETC",
            mobileApp = "AppTest", listYN = "Y",
            arrange = "A", keyword = "강원", contentTypeId = 12).enqueue(object: Callback<Root> {

            override fun onResponse(call: Call<Root>, response: Response<Root>) {
                if (response.isSuccessful) {
                    val items = response.body()?.response!!.body.items.item

                    val adapter = TourSpotsAdapter(requireContext(), items)

                    fragmentHomeBinding.rvRecommendSpot.adapter = adapter
                    fragmentHomeBinding.rvRecommendSpot.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

                }
            }

            override fun onFailure(call: Call<Root>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })

        return fragmentHomeBinding.root
    }


    override fun onDestroy() {
        super.onDestroy()

        travelRecommendList.clear()
    }
}