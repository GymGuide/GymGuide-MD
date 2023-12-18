// CustomImageView.kt
package com.example.gymguide.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView

class CustomImageView : AppCompatImageView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private val handler = Handler(Looper.getMainLooper())
    private var isInsideImage = false

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isInsideImage = isTouchInsideImage(event)
                if (isInsideImage) {
                    alpha = 1f
                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isInsideImage) {
                    handler.postDelayed({
                        alpha = 0f
                    }, 500)
                    performClick()
                }
            }
        }
        return false
    }

    override fun performClick(): Boolean {
        super.performClick()

        return true
    }

    private fun isTouchInsideImage(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        val bmp = getBitmapFromView(this)
        val color = bmp.getPixel(x, y)
        return color != 0
    }

    private fun getBitmapFromView(view: CustomImageView): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}
