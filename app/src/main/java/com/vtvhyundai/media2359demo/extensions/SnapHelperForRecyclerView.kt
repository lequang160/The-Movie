package com.vtvhyundai.media2359demo.extensions


import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper

fun SnapHelper.getSnapPosition(recyclerView: RecyclerView): Int {
    val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION
    val snapView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

interface OnSnapPositionChangeListener {
    fun onSnapPositionChange(position: Int)
}

class SnapOnScrollListener(
    private var mSnapHelper: SnapHelper,
    private var mOnSnapPositionChangeListener: OnSnapPositionChangeListener
) : RecyclerView.OnScrollListener() {
    private var snapPosition = RecyclerView.NO_POSITION
    private val handle = Handler()
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        handle.removeCallbacksAndMessages(null)
        handle.postDelayed({
            val snapPosition = mSnapHelper.getSnapPosition(recyclerView)
            val snapPositionChanged =
                this.snapPosition != snapPosition && snapPosition != RecyclerView.NO_POSITION
            if (snapPositionChanged) {
                mOnSnapPositionChangeListener
                    .onSnapPositionChange(snapPosition)
                this.snapPosition = snapPosition
            }
        }, 300)


    }
}