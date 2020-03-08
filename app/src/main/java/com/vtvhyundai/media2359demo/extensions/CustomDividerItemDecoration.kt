package com.vtvhyundai.media2359demo.extensions

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import timber.log.Timber
import kotlin.math.roundToInt

/*
* Copyright 2018 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

/**
 * DividerItemDecoration is a [RecyclerView.ItemDecoration] that can be used as a divider
 * between items of a [LinearLayoutManager]. It supports both [.HORIZONTAL] and
 * [.VERTICAL] orientations.
 *
 * <pre>
 * mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 * mLayoutManager.getOrientation());
 * recyclerView.addItemDecoration(mDividerItemDecoration);
</pre> *
 */
class CustomDividerItemDecoration(context: Context, orientation: Int, private val rect: Rect) :
    ItemDecoration() {
    /**
     * @return the [Drawable] for this divider.
     */
    var drawable: Drawable?
        private set
    /**
     * Current orientation. Either [.HORIZONTAL] or [.VERTICAL].
     */
    private var mOrientation = 0
    private val mBounds = Rect()
    /**
     * Sets the orientation for this divider. This should be called if
     * [RecyclerView.LayoutManager] changes orientation.
     *
     * @param orientation [.HORIZONTAL] or [.VERTICAL]
     */
    fun setOrientation(orientation: Int) {
        require(!(orientation != HORIZONTAL && orientation != VERTICAL)) { "Invalid orientation. It should be either HORIZONTAL or VERTICAL" }
        mOrientation = orientation
    }

    /**
     * Sets the [Drawable] for this divider.
     *
     * @param drawable Drawable that should be used as a divider.
     */
    fun setDrawable(drawable: Drawable) {
        requireNotNull(drawable) { "Drawable cannot be null." }
        this.drawable = drawable
    }

    override fun onDraw(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.layoutManager == null || drawable == null) {
            return
        }
        if (mOrientation == VERTICAL) {
            drawVertical(c, parent)
        } else {
            drawHorizontal(c, parent)
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int = rect.left
        val right: Int = rect.right
        canvas.clipRect(
            left, 0, right,
            0
        )

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            val bottom = mBounds.bottom + child.translationY.roundToInt()
            val top = bottom - drawable!!.intrinsicHeight
            drawable!!.setBounds(left, top, right, bottom)
            drawable!!.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val top: Int = parent.paddingTop
        val bottom: Int = parent.height - parent.paddingBottom
        canvas.clipRect(
            parent.paddingLeft, top,
            parent.width - parent.paddingRight, bottom
        )
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.layoutManager!!.getDecoratedBoundsWithMargins(child, mBounds)
            val right = mBounds.right + child.translationX.roundToInt()
            val left = right - drawable!!.intrinsicWidth
            drawable!!.setBounds(left, top, right, bottom)
            drawable!!.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (drawable == null) {
            outRect.set(0, 0, 0, 0)
            return
        }
        if (mOrientation == VERTICAL) {
            outRect.set(rect.left, 0, rect.right, drawable!!.intrinsicHeight)
        } else {
            outRect.set(0, 0, drawable!!.intrinsicWidth, 0)
        }
    }

    companion object {
        const val HORIZONTAL = LinearLayout.HORIZONTAL
        const val VERTICAL = LinearLayout.VERTICAL
        private val ATTRS = intArrayOf(android.R.attr.listDivider)
    }

    init {
        val a =
            context.obtainStyledAttributes(ATTRS)
        drawable = a.getDrawable(0)
        if (drawable == null) {
            Timber.d(
                "@android:attr/listDivider was not set in the theme used for this "
                        + "DividerItemDecoration. Please set that attribute all call setDrawable()"
            )
        }
        a.recycle()
        setOrientation(orientation)
    }
}