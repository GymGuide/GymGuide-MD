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
                // Handle touch down event
                isInsideImage = isTouchInsideImage(event)
                if (isInsideImage) {
                    alpha = 1f
                }
                return true
            }
            MotionEvent.ACTION_UP -> {
                // Handle touch up event
                if (isInsideImage) {
                    handler.postDelayed({
                        alpha = 0f
                        // Perform click action and start MuscleDetailActivity
                        performClick()
                    }, 500) // 500 milliseconds delay (adjust as needed)
                }
                return true
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

        return color != 0 // Check for non-transparent part
    }

    private fun getBitmapFromView(view: CustomImageView): Bitmap {
        val bitmap = Bitmap.createBitmap(
            view.width, view.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}
