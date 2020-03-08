package com.vtvhyundai.media2359demo.ui.base


import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vtvhyundai.media2359demo.di.viewModel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


abstract class BaseActivity<TViewModel : ViewModel> : DaggerAppCompatActivity() {
    companion object {
        @JvmStatic
        val ARG_GO_TO_TIMESTAMP = "ARG_GO_TO_TIMESTAMP"
    }

    lateinit var mViewModel: TViewModel
    var hasToolBar: Boolean = false


    @Inject
    lateinit var mViewModelFactory: ViewModelFactory

    lateinit var containerView: FrameLayout

    lateinit var mProgressBarDialog: Dialog

    @Suppress("IMPLICIT_NOTHING_AS_TYPE_PARAMETER", "UNREACHABLE_CODE")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        containerView = findViewById(android.R.id.content)
        mViewModel = ViewModelProvider(this, this.mViewModelFactory)
            .get(this.getClazzViewModel())
        initProgressbar()
        initView()
        initAction()
        subscriberData()
    }

    private fun initProgressbar() {
        mProgressBarDialog = Dialog(this)
        val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyle)
        progressBar.indeterminateDrawable.colorFilter = PorterDuffColorFilter(
            Color.WHITE,
            PorterDuff.Mode.MULTIPLY
        )
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
        mProgressBarDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mProgressBarDialog.addContentView(progressBar, layoutParams)
    }

    fun showDialogLoading(cancelable: Boolean) {
        mProgressBarDialog.setCancelable(cancelable)
        mProgressBarDialog.show()
    }

    fun hideDialogLoading() {
        mProgressBarDialog.dismiss()
    }

    fun hideKeyboard(view: View?) {
        if (view == null) {
            return
        }
        val windowsToken = view.windowToken
        if (windowsToken != null) {
            val inputMethodManager =
                getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
            inputMethodManager?.hideSoftInputFromWindow(windowsToken, 0)
        }
    }

    fun showKeyboard() {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.toggleSoftInput(
            InputMethodManager.SHOW_FORCED,
            InputMethodManager.HIDE_IMPLICIT_ONLY
        );
    }

    abstract fun subscriberData()

    abstract fun initView()

    abstract fun initAction()

    fun initRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
    ) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun getClazzViewModel(): Class<TViewModel>

}