package com.chama.challenge.heritages.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.chama.challenge.R
import kotlinx.android.synthetic.main.item_error.view.errorTextView
import kotlinx.android.synthetic.main.item_error.view.retryButton

class ErrorViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_error, parent, false)
) {

    private val errorTextView = itemView.errorTextView
    private val retryButton = itemView.retryButton

    fun onBind(errorText: String, onRetry: () -> Unit) {
        errorTextView.text = errorText
        retryButton.setOnClickListener { onRetry() }
    }

    fun onBind(@StringRes errorTextRes: Int, onRetry: () -> Unit) {
        errorTextView.setText(errorTextRes)
        retryButton.setOnClickListener { onRetry() }
    }
}