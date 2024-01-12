package com.hyun.worldwiser.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hyun.worldwiser.R
import com.hyun.worldwiser.ui.travel.RecommendActivity

class TravelFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_travel, container, false)

        view.findViewById<FloatingActionButton>(R.id.fBtn_travel_recommend_insert).setOnClickListener {
            val intent = Intent(requireActivity(), RecommendActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}