package com.cloutsmith.moviedb.ui.uiHelpers

/**
 * Created by @Aman Shandilya on 29/01/2018.
 */
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private val mVisibleThreshold = 6
    private var mPreviousTotal = 0
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = recyclerView.layoutManager.itemCount
        val firstVisibleItem = (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }
        if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem + mVisibleThreshold) {
            onLoadMore()
            mLoading = true
        }
    }

    abstract fun onLoadMore()
}