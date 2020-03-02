package com.chama.challenge.heritages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chama.challenge.R
import com.chama.challenge.common.utils.replaceFragment
import com.chama.challenge.heritages.ui.adapter.EndlessScrollListener
import com.chama.challenge.heritages.ui.adapter.HeritagesAdapter
import com.chama.challenge.location.LocationFragment
import kotlinx.android.synthetic.main.fragment_heritages.heritagesRecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeritagesFragment : Fragment() {

    companion object {
        fun create() = HeritagesFragment()
    }

    private val viewModel: HeritagesViewModel by viewModel()

    private val heritagesAdapter: HeritagesAdapter by lazy {
        HeritagesAdapter(
            onItemClicked = { replaceFragment(LocationFragment.create(it)) },
            pageLoadFailedListener = { resetState() }
        )
    }

    private lateinit var scrollListener: EndlessScrollListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_heritages, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(heritagesRecyclerView) {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = linearLayoutManager
            adapter = heritagesAdapter

            scrollListener = object : EndlessScrollListener(linearLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                    viewModel.loadHeritages(page)
                }
            }
            addOnScrollListener(scrollListener)
        }

        viewModel.heritagesLiveData.observe(viewLifecycleOwner, Observer { heritages ->
            heritagesAdapter.setData(heritages)
        })

        viewModel.stateLiveData.observe(viewLifecycleOwner, Observer { state ->
            heritagesAdapter.setPagingState(state)
        })

        viewModel.loadHeritages()
    }

    private fun resetState() {
        heritagesAdapter.clear()
        scrollListener.resetState()
        viewModel.reloadHeritages()
    }
}