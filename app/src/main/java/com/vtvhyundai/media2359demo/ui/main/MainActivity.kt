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
    lateinit var mNowPlayingRecyclerView: RecyclerView
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
        Handler().postDelayed({
            mViewModel.fetchMovie(mCurrentPage)
        }, 1000)

        mNowPlayingAdapter.onLoadMoreListener =
            { adapter: RecyclerView.Adapter<*>, isLoading: Boolean ->
                showDialogLoading(false)
                mCurrentPage += 1
                mViewModel.fetchMovie(mCurrentPage)
            }
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getClazzViewModel(): Class<MainViewModel> = MainViewModel::class.java
}
