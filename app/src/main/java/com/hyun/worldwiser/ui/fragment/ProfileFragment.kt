package com.hyun.worldwiser.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.FragmentProfileBinding
import com.hyun.worldwiser.ui.travel.InsertActivity
import com.hyun.worldwiser.util.IntentFilter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ProfileFragment : Fragment() {

    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    private val intentFilter: IntentFilter = IntentFilter()
    private val insertActivity: InsertActivity = InsertActivity()

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        fragmentProfileBinding.btnTravelInsert.setOnClickListener {
            intentFilter.getIntent(requireContext(), insertActivity)
        }

        db.collection("travelInserts").document(auth.currentUser!!.uid).get()
            .addOnSuccessListener { document ->
                val country = document["country"].toString()
                val imageUrl = document["imageUrl"].toString()
                val startDay = document["startDay"].toString()
                val endDay = document["endDay"].toString()

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

                val currentDate = LocalDate.now()

                try {
                    val day = LocalDate.parse(startDay, formatter)

                    val daysUntilStartDay = ChronoUnit.DAYS.between(currentDate, day)

                    if (daysUntilStartDay > 0) {
                        fragmentProfileBinding.tvTravelCalendarDay.text = "여행 시작일이 " + daysUntilStartDay.toString() + "일이 남았어요!!"
                    } else if (daysUntilStartDay == 0L) {
                        fragmentProfileBinding.tvTravelCalendarDay.text = "여행하고 있어요!"
                    } else {
                        fragmentProfileBinding.tvTravelCalendarDay.text = "여행이 끝났어요!"
                    }

                } catch(e: Exception){
                    print(e.printStackTrace())
                }

                Glide.with(requireActivity())
                    .load(imageUrl)
                    .into(fragmentProfileBinding.imageView)

                fragmentProfileBinding.tvTravelCountry.text = country

                fragmentProfileBinding.tvTravelCalendar.text = "$startDay ~ $endDay"
            }
            .addOnFailureListener {
                fragmentProfileBinding.tvTravelCountry.text = "새로운 여행을 등록해보세요!!"
            }


        return fragmentProfileBinding.root
    }
}
