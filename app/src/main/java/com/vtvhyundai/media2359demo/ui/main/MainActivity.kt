package com.vtvhyundai.media2359demo.ui.main

import android.os.Handler
import androidx.lifecycle.Observer
import com.vtvhyundai.media2359demo.R
import com.vtvhyundai.media2359demo.extensions.showToast
import com.vtvhyundai.media2359demo.ui.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {


    override fun subscriberData() {
        mViewModel.liveData.observe(this, Observer {
            showToast(it.toString())
        })
    }

    override fun initView() {

    }

    override fun initAction() {
        Handler().postDelayed({
            mViewModel.fetchMovie()
        }, 1000)
    }

    override fun getLayoutRes(): Int = R.layout.activity_main

    override fun getClazzViewModel(): Class<MainViewModel> = MainViewModel::class.java
}
