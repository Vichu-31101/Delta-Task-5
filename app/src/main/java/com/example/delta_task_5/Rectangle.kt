package com.example.delta_task_5

import android.util.Log
import kotlin.math.abs

class Rectangle {
    var left = 200f
    var top = 700f
    var right = 400f
    var bottom = 900f

    fun move(x: Float,y: Float){
        this.left += x
        this.right += x
        this.top += y
        this.bottom += y
    }

    fun resize(lDiff: Float,tDiff: Float,rDiff: Float,bDiff: Float,x: Float,y: Float){
        Log.d("test",lDiff.toString())
        if(lDiff < 30f){
            if(abs((this.left-this.right).toDouble()) >= 100.0){
                this.left += x
            }
            else{
                this.left = this.right - 100f
            }
        }
        if(rDiff < 30f){
            if(abs((this.left-this.right).toDouble()) >= 100.0){
                this.right += x
            }
            else{
                this.right = this.left + 100f
            }
        }
        if(tDiff < 30f){
            if(abs((this.top-this.bottom).toDouble()) >= 100.0){
                this.top += y
            }
            else{
                this.top = this.bottom - 100f
            }
        }
        if(bDiff < 30f){
            if(abs((this.top-this.bottom).toDouble()) >= 100.0){
                this.bottom += y
            }
            else{
                this.bottom = this.top + 100f
            }
        }

    }
}