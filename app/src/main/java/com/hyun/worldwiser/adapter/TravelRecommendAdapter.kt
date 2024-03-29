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
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

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
            val travelRecommendAuthUid = travelRecommend.travelRecommendAuthUid
            val travelRecommendAuthNickname = travelRecommend.travelRecommendAuthNickname
            val travelRecommendCountry = travelRecommend.travelRecommendCountry
            val travelRecommendFavoriteCount = travelRecommend.travelRecommendFavoriteCount
            val travelRecommendImageUrl = travelRecommend.travelRecommendImageUrl!!

            Log.d("travelRecommendAdapter", travelRecommendImageUrl)

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

            Log.d("travelRecommendAuthUid", travelRecommendAuthUid)

            "@user-$travelRecommendAuthUid".also { authUid ->
                itemView.findViewById<TextView>(R.id.tv_travel_recommend_auth_uid).text = authUid
            }

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_nickname).text = travelRecommendAuthNickname

            Log.d("@userRecommendAuthUid", "@user-$travelRecommendAuthUid")
            Log.d("@userAuthUid", "@user-${auth.currentUser!!.uid}")

            if ("@user-$travelRecommendAuthUid" == "@user-${auth.currentUser!!.uid}") {
                itemView.findViewById<ImageView>(R.id.iv_travel_recommend_delete).visibility = View.VISIBLE
            } else {
                itemView.findViewById<ImageView>(R.id.iv_travel_recommend_delete).visibility = View.INVISIBLE
            }

            val imageUrlSpilt = travelRecommendImageUrl.split(",")

            if (imageUrlSpilt.size >= 2) {
                val firstImageUrl: String = if (imageUrlSpilt.isNotEmpty()) {
                    imageUrlSpilt[0].trim()
                } else {
                    ""
                }

                val secondImageUrl: String = if (imageUrlSpilt.isNotEmpty()) {
                    imageUrlSpilt[1].trim()

                } else {
                    ""
                }

                Glide
                    .with(context)
                    .load(firstImageUrl)
                    .into(itemView.findViewById(R.id.iv_travel_recommend_imageUrl_First))

                Glide
                    .with(context)
                    .load(secondImageUrl)
                    .into(itemView.findViewById(R.id.iv_travel_recommend_imageUrl_Second))

                itemView.setOnClickListener {
                    val intent = Intent(context, RecommendDetailActivity::class.java)
                    intent.putExtra("travelRecommendFirstImageUrl", firstImageUrl)
                    intent.putExtra("travelRecommendSecondImageUrl", secondImageUrl)
                    context.startActivity(intent)
                }
            } else {
                val imageUrl: String = if (imageUrlSpilt.isNotEmpty()) {
                    imageUrlSpilt.toString().trim()
                } else {
                    ""
                }

                val formatterImageUrl = imageUrl.replace("[", "").replace("]", "")


                Log.d("imageUrlTravelRecommendAdapter", formatterImageUrl)

                Glide.with(context)
                    .load(formatterImageUrl)
                    .into(itemView.findViewById(R.id.iv_travel_recommend_imageUrl_First))


                itemView.setOnClickListener {
                    val intent = Intent(context, RecommendDetailActivity::class.java)
                    intent.putExtra("travelRecommendFormatterImageUrl", formatterImageUrl)
                    context.startActivity(intent)
                }
            }

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_favorite_count).text = travelRecommendFavoriteCount.toString()

            itemView.findViewById<TextView>(R.id.tv_travel_recommend_country).text = travelRecommendCountry
        }
    }
}