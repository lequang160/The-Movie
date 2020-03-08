package com.vtvhyundai.media2359demo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vtvhyundai.media2359demo.di.viewModel.ViewModelFactory

import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<TViewModel : ViewModel> : DaggerFragment() {
    companion object {
        @JvmStatic
        val ARG_GO_TO_TIMESTAMP = "ARG_GO_TO_TIMESTAMP"
    }

    lateinit var mViewModel: TViewModel


    @Inject
    lateinit var mViewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(getLayoutRes(), container, false)
        initView(rootView)
        initAction()
        Timber.e("" + getClazzViewModel())
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(getClazzViewModel())
        // TODO: Use the ViewModel
        subscriberData()
        Timber.e("onActivityCreated")
    }

    abstract fun getClazzViewModel(): Class<TViewModel>


    abstract fun subscriberData()

    abstract fun initView(view: View)

    abstract fun initAction()

    fun initRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        orientation: Int?
    ) {
        var layoutManager: LinearLayoutManager

        when (orientation) {
            null -> {
                layoutManager =
                    LinearLayoutManager(recyclerView.context, LinearLayoutManager.VERTICAL, false)

            }
            else -> {
                layoutManager = LinearLayoutManager(recyclerView.context, orientation, false)
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                layoutManager.orientation
            )
        )
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.adapter = adapter
    }


    @LayoutRes
    abstract fun getLayoutRes(): Int

}