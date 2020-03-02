package com.chama.challenge.heritages.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chama.challenge.R
import com.chama.challenge.heritages.model.Heritage
import com.chama.challenge.heritages.model.PagingState

class HeritagesAdapter(
    private val onItemClicked: (Heritage) -> Unit,
    private val pageLoadFailedListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data = mutableListOf<Heritage>()
    private var state: PagingState = PagingState.Idle

    fun setData(new: List<Heritage>) {
        val oldSize = data.size
        data.clear()
        data.addAll(new)

        notifyItemRangeInserted(oldSize, data.size - oldSize)
    }

    fun setPagingState(newState: PagingState) {
        if (state == newState) return

        val shouldHasExtraItem = stateRequiresExtraItem(newState)
        val hasExtraItem = stateRequiresExtraItem(state)

        state = newState

        // since item count is a function - cache its value.
        val count = itemCount

        when {
            hasExtraItem && shouldHasExtraItem -> notifyItemChanged(count)
            hasExtraItem && !shouldHasExtraItem -> notifyItemRemoved(count)
            !hasExtraItem && shouldHasExtraItem -> notifyItemInserted(count)
        }
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_heritage -> HeritageViewHolder(parent, onItemClicked)
            R.layout.item_loading -> LoadingViewHolder(parent)
            R.layout.item_error -> ErrorViewHolder(parent)
            else -> throw IllegalStateException("Unknown view type $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeritageViewHolder -> holder.onBind(data[position])
            is ErrorViewHolder -> holder.onBind(R.string.error_common_message, pageLoadFailedListener)
            is LoadingViewHolder -> {
                // do nothing
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when {
        !stateRequiresExtraItem(state) || position < itemCount - 1 -> R.layout.item_heritage
        state is PagingState.Loading -> R.layout.item_loading
        else -> R.layout.item_error
    }

    override fun getItemCount(): Int =
        data.size + if (stateRequiresExtraItem(state)) 1 else 0

    private fun stateRequiresExtraItem(state: PagingState) =
        state == PagingState.Loading || state is PagingState.Error
}