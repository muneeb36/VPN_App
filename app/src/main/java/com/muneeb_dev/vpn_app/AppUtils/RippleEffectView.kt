package com.muneeb_dev.vpn_app.AppUtils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator

class RippleEffectView(context: Context) : View(context) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var rippleRadius = 0f
    private var maxRadius = 0f
    private var centerX = 0
    private var centerY = 0
    private var rippleColorStart = Color.TRANSPARENT
    private var rippleColorEnd = Color.TRANSPARENT
    private var isAnimating = false
    private var animationDuration = 600L
    private lateinit var animator: ValueAnimator

    init {
        visibility = GONE
    }

    fun startRipple(
        targetView: View,
        colorStart: Int,
        colorEnd: Int,
        duration: Long = 600L
    ) {
        // Set up properties
        rippleColorStart = colorStart
        rippleColorEnd = colorEnd
        animationDuration = duration

        // Calculate center and max radius
        centerX = targetView.width / 2
        centerY = targetView.height / 2
        maxRadius = Math.hypot(targetView.width.toDouble(), targetView.height.toDouble()).toFloat()

        // Position and size this view to overlay the target
        layoutParams = ViewGroup.LayoutParams(targetView.width, targetView.height)
        (targetView.parent as? ViewGroup)?.addView(this)
        x = targetView.x
        y = targetView.y

        // Prepare the animator
        animator = ValueAnimator.ofFloat(0f, maxRadius).apply {
//            duration = animationDuration
            repeatCount = ValueAnimator.INFINITE // Repeat for continuous ripple effect
            repeatMode = ValueAnimator.RESTART
            interpolator = AccelerateDecelerateInterpolator()

            addUpdateListener { animation ->
                rippleRadius = animation.animatedValue as Float
                val fraction = animation.animatedFraction
                paint.color = blendColors(rippleColorStart, rippleColorEnd, fraction)
                invalidate()
            }
        }

        isAnimating = true
        visibility = VISIBLE
        animator.start()
    }

    fun stopRipple() {
        if (!isAnimating) return
        animator.cancel()
        isAnimating = false
        visibility = GONE
        (parent as? ViewGroup)?.removeView(this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), rippleRadius, paint)
    }

    private fun blendColors(colorStart: Int, colorEnd: Int, fraction: Float): Int {
        val startA = Color.alpha(colorStart)
        val startR = Color.red(colorStart)
        val startG = Color.green(colorStart)
        val startB = Color.blue(colorStart)

        val endA = Color.alpha(colorEnd)
        val endR = Color.red(colorEnd)
        val endG = Color.green(colorEnd)
        val endB = Color.blue(colorEnd)

        val alpha = (startA + (fraction * (endA - startA))).toInt()
        val red = (startR + (fraction * (endR - startR))).toInt()
        val green = (startG + (fraction * (endG - startG))).toInt()
        val blue = (startB + (fraction * (endB - startB))).toInt()

        return Color.argb(alpha, red, green, blue)
    }
}
