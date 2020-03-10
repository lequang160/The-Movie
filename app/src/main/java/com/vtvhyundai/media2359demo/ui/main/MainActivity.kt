package com.vtvhyundai.media2359demo.ui.main

import android.os.Handler
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vtvhyundai.media2359demo.R
import com.vtvhyundai.media2359demo.extensions.showToast
import com.vtvhyundai.media2359demo.ui.base.BaseActivity
import com.vtvhyundai.media2359demo.ui.main.adapter.NowPlayingAdapter

class MainActivity : BaseActivity<MainViewModel>() {

    lateinit var mNowPlayingAdapter: NowPlayingAdapter
    private lateinit var mNowPlayingRecyclerView: RecyclerView
    private var mCurrentPage = 1
    var isLoadMore = false

    override fun subscriberData() {
        mViewModel.movieList.observe(this, Observer {
            mNowPlayingAdapter.addNewData(it)
            mNowPlayingAdapter.isLoading = false
            hideDialogLoading()
        })
        mViewModel.errorLiveData.observe(this, Observer {
            showToast(it.toString())
        })
    }

    override fun initView() {
        mNowPlayingRecyclerView = findViewById(R.id.activity_main_movie_rv)
        mNowPlayingAdapter = NowPlayingAdapter()
        val layoutManager = GridLayoutManager(this, 2)
        /*layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if(position%2 == 0) {
                    return when (mNowPlayingAdapter.getItemViewType(position)) {
                        NowPlayingAdapter.VIEW_TYPE_IS_EMPTY_VIEW -> 1
                        NowPlayingAdapter.VIEW_TYPE_IS_ITEM -> 2
                        else -> 2
                    }
                }
                return 2
            }
        }*/
        mNowPlayingRecyclerView.layoutManager = layoutManager


        /* mNowPlayingRecyclerView.addItemDecoration(
             DividerItemDecoration(
                 mNowPlayingRecyclerView.context,
                 DividerItemDecoration.VERTICAL
             )
         )*/
        mNowPlayingRecyclerView.adapter = mNowPlayingAdapter
    }

    override fun initAction() {
        showDialogLoading(false)
        Handler().postDelayed({
            mViewModel.fetchMovie(mCurrentPage)
        }, 1000)

        mNowPlayingAdapter.onLoadMoreListener =
            { _, _ ->
                showDialogLoading(false)
                mCurrentPage += 1
                showToast("Page : $mCurrentPage")
                mViewModel.fetchMovie(mCurrentPage)
            }
        mNowPlayingAdapter.onItemClickListener = { adapter, position ->
            showToast("Clicked:" + (adapter as NowPlayingAdapter).data[position].title)
        }
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getClazzViewModel(): Class<MainViewModel> = MainViewModel::class.java
}
