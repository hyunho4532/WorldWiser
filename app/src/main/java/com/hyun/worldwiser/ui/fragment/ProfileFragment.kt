package com.hyun.worldwiser.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.adapter.TravelAdapter
import com.hyun.worldwiser.adapter.TravelSwipeToDeleteCallback
import com.hyun.worldwiser.databinding.FragmentProfileBinding
import com.hyun.worldwiser.model.Travel
import com.hyun.worldwiser.ui.travel.InsertActivity
import com.hyun.worldwiser.util.AdapterFilter
import com.hyun.worldwiser.util.DateTimeFormatterFilter
import com.hyun.worldwiser.util.IntentFilter
import com.hyun.worldwiser.viewmodel.ProfileSelectViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ProfileFragment : Fragment() {

    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    private val intentFilter: IntentFilter = IntentFilter()
    private val insertActivity: InsertActivity = InsertActivity()

    private val dateTimeFormatterFilter: DateTimeFormatterFilter = DateTimeFormatterFilter()

    private val adapterFilter: AdapterFilter = AdapterFilter()

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private lateinit var country: String
    private lateinit var imageUrl: String
    private lateinit var startDay: String
    private lateinit var endDay: String

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val profileSelectViewModel: ProfileSelectViewModel = ViewModelProvider(this)[ProfileSelectViewModel::class.java]

        val travelList = ArrayList<Travel>()

        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        fragmentProfileBinding.btnTravelInsert.setOnClickListener {
            intentFilter.getIntent(requireContext(), insertActivity)
        }

        profileSelectViewModel.getTravelInserts()

        profileSelectViewModel.country.observe(requireActivity()) { country ->
            this.country = country
        }

        db.collection("travelInserts").whereEqualTo("authUid", auth.currentUser!!.uid).get()
            .addOnSuccessListener { querySnapshot  ->

                for (document in querySnapshot.documents) {

                    try {
                        country = document["country"].toString()
                        imageUrl = document["imageUrl"].toString()
                        startDay = document["startDay"].toString()
                        endDay = document["endDay"].toString()

                        Glide.with(requireActivity())
                            .load(imageUrl)
                            .into(fragmentProfileBinding.imageView)

                        fragmentProfileBinding.tvTravelCountry.text = country

                        fragmentProfileBinding.tvTravelCalendar.text = "$startDay ~ $endDay"

                        val travel = Travel(imageUrl, country, startDay, endDay)
                        travelList.add(travel)

                        val travelAdapter = TravelAdapter(requireContext(), travelList, fireStore = db, auth = auth)
                        val itemTouchHelper = ItemTouchHelper(TravelSwipeToDeleteCallback(travelAdapter))
                        adapterFilter.getAdapter(recyclerView = requireView().findViewById(R.id.travelRecyclerView), travelAdapter = travelAdapter, context = requireContext(), itemTouchHelper = itemTouchHelper)

                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                        dateTimeFormatterFilter.settingDateTimeFormatter(formatter, startDay, endDay, fragmentProfileBinding)

                    } catch (e: UninitializedPropertyAccessException) {
                        fragmentProfileBinding.tvTravelCalendar.text = ""
                    }
                }
            }


        return fragmentProfileBinding.root
    }
}
