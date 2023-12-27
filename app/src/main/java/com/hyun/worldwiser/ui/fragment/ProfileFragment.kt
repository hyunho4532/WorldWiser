package com.hyun.worldwiser.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hyun.worldwiser.R
import com.hyun.worldwiser.databinding.FragmentProfileBinding
import com.hyun.worldwiser.ui.travel.InsertActivity
import com.hyun.worldwiser.util.IntentFilter

class ProfileFragment : Fragment() {

    private lateinit var fragmentProfileBinding: FragmentProfileBinding
    private val intentFilter: IntentFilter = IntentFilter()
    private val insertActivity: InsertActivity = InsertActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        fragmentProfileBinding.btnTravelInsert.setOnClickListener {
            intentFilter.getIntent(requireContext(), insertActivity)
        }

        return fragmentProfileBinding.root
    }
}
