package com.abhishekchakraborty.androidpaging.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.abhishekchakraborty.androidpaging.R
import com.abhishekchakraborty.androidpaging.databinding.FragmentNewsListBinding
import com.abhishekchakraborty.androidpaging.ui.list.callback.OnItemClickListener
import com.abhishekchakraborty.androidpaging.ui.list.adapter.NewsRecyclerViewAdapter
import com.abhishekchakraborty.androidpaging.ui.list.style.VerticalSpaceItemDecoration
import com.abhishekchakraborty.androidpaging.model.Event
import com.abhishekchakraborty.androidpaging.model.ListViewState
import com.abhishekchakraborty.androidpaging.model.ResultData
import com.abhishekchakraborty.androidpaging.ui.list.NewsLoadStateAdapter
import com.abhishekchakraborty.androidpaging.viewModel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@FlowPreview
@ExperimentalCoroutinesApi
class NewsListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {

	companion object {
		private val TAG = NewsListFragment::class.qualifiedName
		fun newInstance(): NewsListFragment {
			return NewsListFragment()
		}
	}

	private lateinit var binding: FragmentNewsListBinding

	private val newsAdapter: NewsRecyclerViewAdapter = NewsRecyclerViewAdapter(listener = this)

	// Lazy Inject ViewModel
	private val viewModel by sharedViewModel<MainViewModel>()


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentNewsListBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupBinding()
		observeViewState()
		if (savedInstanceState == null) {
			lifecycleScope.launch {
				viewModel.onSuspendedEvent(Event.ScreenLoad)
			}
		}
	}

	private fun setupBinding() {
		binding.swiperefresh.setOnRefreshListener(this)
		binding.list.apply {
			layoutManager = LinearLayoutManager(context)
			addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.list_item_decoration)))
			initAdapter()
		}
		binding.retryButton.setOnClickListener { Log.d("Abhishek", "click") }
	}

	private fun initAdapter() {
		binding.list.adapter = newsAdapter.withLoadStateHeaderAndFooter(
			header = NewsLoadStateAdapter { newsAdapter.retry() },
			footer = NewsLoadStateAdapter { newsAdapter.retry() }
		)
		newsAdapter.addLoadStateListener {
			Log.d("Abhishek", "loading state: ${it.toString()}")
			viewModel.onEvent(Event.LoadState(it))
		}
		setScrollToTopWHenRefreshedFromNetwork()
	}

	private fun setScrollToTopWHenRefreshedFromNetwork() {
		// Scroll to top when the list is refreshed from network.
		lifecycleScope.launch {
			newsAdapter.loadStateFlow
				// Only emit when REFRESH LoadState for RemoteMediator changes.
				.distinctUntilChangedBy { it.refresh }
				// Only react to cases where Remote REFRESH completes i.e., NotLoading.
				.filter { it.refresh is LoadState.NotLoading }
				.collect { binding.list.scrollToPosition(0) }
		}
	}

	private fun observeViewState() {
		viewModel.obtainState.observe(viewLifecycleOwner, {
			Log.d(TAG, "observeViewState obtainState result: ${it.adapterList.size}")
			render(it)
		})
	}

	private fun render(state: ListViewState) {
		binding.swiperefresh.isRefreshing = false
		state.loadingStateVisibility?.let { binding.progressBar.visibility = it }
		lifecycleScope.launch {
			state.page?.let { newsAdapter.submitData(it) }
		}
		state.errorVisibility?.let {
			binding.mainListErrorMsg.visibility = it
			binding.retryButton.visibility = it
			state.errorMessage?.let { binding.mainListErrorMsg.text = state.errorMessage }
			state.errorMessageResource?.let { binding.mainListErrorMsg.text = getString(state.errorMessageResource) }
		}
	}

	override fun onRefresh() {
		lifecycleScope.launch {
			viewModel.onSuspendedEvent(Event.ScreenLoad)
		}
	}

	override fun onItemClick(item: ResultData) {
		// TODO: implement click
		//viewModel.event(Event.ListItemClicked(item))
	}
}
