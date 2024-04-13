package com.hyun.worldwiser.util

import com.hyun.worldwiser.databinding.FragmentHomeBinding

class HomeFragmentTitleFilter(
    private val fragmentHomeBinding: FragmentHomeBinding
) {
    fun homeFragmentTitleSettings() {
        val rocketUnicode = 0x1F680
        val worldWiserTravelStatistics = "월드 와이저 여행 통계 ${String(Character.toChars(rocketUnicode))}"
        fragmentHomeBinding.tvWorldWiserTravelStatistics.text = worldWiserTravelStatistics

        val thoughtBalloonUnicode = 0x1F4AD
        val worldWiserTravelForm = "월드 와이저 여행 형태 ${String(Character.toChars(thoughtBalloonUnicode))}"
        fragmentHomeBinding.tvWorldWiserTravelForm.text = worldWiserTravelForm

        val	fireUnicode = 0x1F525
        val worldWiserTravelRecommend = "월드 와이저 추천 여행지 ${String(Character.toChars(fireUnicode))}"
        fragmentHomeBinding.tvWorldWiserTravelRecommend.text = worldWiserTravelRecommend
    }
}