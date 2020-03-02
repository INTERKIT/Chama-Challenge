package com.chama.challenge.heritages.ui.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@SuppressWarnings("MagicNumber")
abstract class EndlessScrollListener(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private var visibleThreshold = 5

    private var currentPage = 1

    private var previousTotalItemCount = 0

    // True if we are still waiting for the last set of data to load.
    private var isLoading = true

    private val startingPageIndex = 1

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        val totalItemCount = layoutManager.itemCount

        val lastVisibleItemPosition: Int = layoutManager.findLastVisibleItemPosition()

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                this.isLoading = true
            }
        }

        if (isLoading && totalItemCount > previousTotalItemCount) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }

        if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            isLoading = true
        }
    }

    fun resetState() {
        this.currentPage = this.startingPageIndex
        this.previousTotalItemCount = 0
        this.isLoading = true
    }

    // Defines the process for actually loading more data based on page
    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView)
}
