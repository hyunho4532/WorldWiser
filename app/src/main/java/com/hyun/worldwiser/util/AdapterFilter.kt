package com.hyun.worldwiser.util

import android.content.Context
import android.widget.Adapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.worldwiser.adapter.TravelAdapter

class AdapterFilter {
    fun getAdapter(
        recyclerView: RecyclerView,
        travelAdapter: TravelAdapter,
        context: Context,
        itemTouchHelper: ItemTouchHelper
    ) {
        recyclerView.adapter = travelAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}