package com.example.delta_task_5

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat

private const val STROKE_WIDTH = 12f

class DrawView(context: Context,attributeSet: AttributeSet) : View(context) {


    var canvasHeight: Float = 0f
    val rectangle = Rectangle()
    var rectMove = false
    var rectSize = false
    var lDiff = 100f
    var tDiff = 100f
    var rDiff = 100f
    var bDiff = 100f

    private val drawColor = ResourcesCompat.getColor(resources, R.color.colorAccent, null)
    private val border = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)


    // Set up the paint with which to draw.
    private val paint = Paint().apply {
        color = drawColor
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }
    private val bPaint = Paint().apply {
        color = border
        // Smooths out edges of what is drawn without affecting shape.
        isAntiAlias = true
        // Dithering affects how colors with higher-precision than the device are down-sampled.
        isDither = true
        strokeJoin = Paint.Join.ROUND // default: MITER
        strokeCap = Paint.Cap.ROUND // default: BUTT
        strokeWidth = STROKE_WIDTH // default: Hairline-width (really thin)
    }



    private var currentX = 0f
    private var currentY = 0f

    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f



    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        canvasHeight = height.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(rectangle.left,rectangle.top,rectangle.right,rectangle.bottom,paint)
        canvas.drawRect(rectangle.left+30f,rectangle.top+30f,rectangle.right-30f,rectangle.bottom-30f,bPaint)

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionTouchEventX = event.x
        motionTouchEventY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }

        return true
    }


    private fun touchStart() {
        currentX = motionTouchEventX
        currentY = motionTouchEventY
        Log.d("test",motionTouchEventX.toString()+" "+motionTouchEventY.toString())
        if((motionTouchEventX > rectangle.left && motionTouchEventX < rectangle.right)&&(motionTouchEventY < rectangle.bottom && motionTouchEventY > rectangle.top)){
            lDiff = motionTouchEventX-rectangle.left
            rDiff = rectangle.right - motionTouchEventX
            tDiff = motionTouchEventY - rectangle.top
            bDiff = rectangle.bottom - motionTouchEventY
            if(lDiff <= 30f || tDiff <= 30f || rDiff <= 30f || bDiff <= 30f){
                rectSize = true
            }
            else{
                rectMove = true
            }
        }

    }

    private fun touchMove() {
        if(rectMove){
            rectangle.move(motionTouchEventX-currentX,motionTouchEventY-currentY)
        }
        if(rectSize){
            rectangle.resize(lDiff,tDiff,rDiff,bDiff,motionTouchEventX-currentX,motionTouchEventY-currentY)
        }
        currentX = motionTouchEventX
        currentY = motionTouchEventY

        invalidate()
    }

    private fun touchUp() {
        rectMove = false
        rectSize = false
    }

}