package com.hyun.worldwiser.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hyun.worldwiser.R
import com.hyun.worldwiser.model.TravelRecommend
import com.hyun.worldwiser.ui.travel.RecommendDetailActivity

class TravelRecommendAdapter(private val context: Context, private val travelRecommendList: ArrayList<TravelRecommend>) :
    RecyclerView.Adapter<TravelRecommendAdapter.ViewHolder>() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_travel_recommend_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(travelRecommendList[position])
    }

    override fun getItemCount(): Int {
        return travelRecommendList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(travelRecommend: TravelRecommend) {
            val travelRecommendAuthNickname = travelRecommend.travelRecommendAuthNickname
            val travelRecommendCountry = travelRecommend.travelRecommendCountry
            val travelRecommendFavoriteCount = travelRecommend.travelRecommendFavoriteCount
            val travelRecommendImageUrlFirst = travelRecommend.travelRecommendImageUrlFirst!!
            val travelRecommendImageUrlSecond = travelRecommend.travelRecommendImageUrlSecond!!

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_favorite_count).setOnClickListener {
                db.collection("travelRecommends")
                    .whereEqualTo("travelRecommendCountry", travelRecommend.travelRecommendCountry)
                    .get()
                    .addOnSuccessListener { documents ->
                        if (documents != null && !documents.isEmpty) {
                            val document = documents.documents[0]
                            val currentCount = document.getLong("travelRecommendFavoriteCount") ?: 0
                            val updatedCount = currentCount.plus(1)

                            itemView.findViewById<TextView>(R.id.tv_travel_recommend_favorite_count).text = currentCount.toString()

                            db.collection("travelRecommends")
                                .document(document.id)
                                .update("travelRecommendFavoriteCount", updatedCount)
                                .addOnSuccessListener {
                                    itemView.findViewById<TextView>(R.id.tv_travel_recommend_favorite_count).text = currentCount.toString()
                                }
                                .addOnFailureListener {

                                }
                        }
                    }
            }

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_nickname).text = travelRecommendAuthNickname

            Glide
                .with(context)
                .load(travelRecommendImageUrlFirst)
                .into(itemView.findViewById(R.id.iv_travel_recommend_imageUrl_First))

            Glide
                .with(context)
                .load(travelRecommendImageUrlSecond)
                .into(itemView.findViewById(R.id.iv_travel_recommend_imageUrl_Second))

            itemView.setOnClickListener {
                val intent = Intent(context, RecommendDetailActivity::class.java)
                intent.putExtra("travelRecommendImageUrlFirst", travelRecommendImageUrlFirst)
                intent.putExtra("travelRecommendImageUrlSecond", travelRecommendImageUrlSecond)
                context.startActivity(intent)
            }

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_favorite_count).text = travelRecommendFavoriteCount.toString()

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_country).text = travelRecommendCountry
        }
    }
}