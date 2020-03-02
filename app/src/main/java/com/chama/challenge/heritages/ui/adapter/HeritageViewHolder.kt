package com.chama.challenge.heritages.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chama.challenge.R
import com.chama.challenge.heritages.model.Heritage
import kotlinx.android.synthetic.main.item_heritage.view.imageView
import kotlinx.android.synthetic.main.item_heritage.view.infoTextView
import kotlinx.android.synthetic.main.item_heritage.view.nameTextView

class HeritageViewHolder(
    private val parent: ViewGroup,
    private val onItemClicked: (Heritage) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_heritage, parent, false)
) {

    private val imageView = itemView.imageView
    private val nameTextView = itemView.nameTextView
    private val infoTextView = itemView.infoTextView

    fun onBind(item: Heritage) {
        nameTextView.text = item.name
        infoTextView.text = item.shortInfo

        Glide
            .with(parent.context)
            .load(item.image)
            .placeholder(R.drawable.ic_home_black_24dp)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView)

        itemView.setOnClickListener { onItemClicked(item) }
    }
}