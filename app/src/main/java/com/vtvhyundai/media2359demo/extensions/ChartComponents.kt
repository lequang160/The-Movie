package com.vtvhyundai.media2359demo.extensions


import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Line(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    // Some colors for the background
    private var borderColor = Color.BLACK
    // Face border width in pixels
    private var borderWidth = 4.0f

    private var _width: Int = 0
    private var _height: Int = 0


    override fun onDraw(canvas: Canvas) {
        // call the super method to keep any drawing from the parent side.
        super.onDraw(canvas)

        drawLineHorizontalBackground(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        _width = widthMeasureSpec
        _height = heightMeasureSpec
    }

    private fun drawLineHorizontalBackground(canvas: Canvas) {
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        canvas.drawLine(0f, 0f, _width.toFloat(), 0f, paint)
    }
}