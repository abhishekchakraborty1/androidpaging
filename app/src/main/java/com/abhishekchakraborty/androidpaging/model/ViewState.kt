package com.abhishekchakraborty.androidpaging.model

import android.view.View
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.abhishekchakraborty.androidpaging.presentation.base.BaseEvent
import com.abhishekchakraborty.androidpaging.presentation.base.BaseResult
import com.abhishekchakraborty.androidpaging.presentation.base.BaseViewEffect
import com.abhishekchakraborty.androidpaging.presentation.base.BaseViewState

data class ListViewState(
    val page: PagingData<ResultData>? = null,
    val adapterList: List<ResultData> = emptyList(),
    val errorMessageResource: Int? = null,
    val errorMessage: String? = null,
    val loadingStateVisibility: Int? = View.GONE,
    val errorVisibility: Int? = View.GONE
): BaseViewState

sealed class ViewEffect: BaseViewEffect {
    data class TransitionToScreen(val photo: ResultData) : ViewEffect()
}

sealed class Event: BaseEvent {
    object SwipeToRefreshEvent: Event()
    data class LoadState(val state: CombinedLoadStates): Event()
    data class ListItemClicked(val item: ResultData): Event()
    // Suspended
    object ScreenLoad: Event()
}

sealed class Result: BaseResult {
    data class Error(val errorMessage: String?): Result()
    data class Content(val content: PagingData<ResultData>): Result()
    //data class ItemClickedResult(val item: Photo, val sharedElement: View) : Result()
}