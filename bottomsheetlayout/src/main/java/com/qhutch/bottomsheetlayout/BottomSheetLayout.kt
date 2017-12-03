package com.qhutch.bottomsheetlayout

import android.animation.ValueAnimator
import android.content.Context
import android.os.Build
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * Created by quentin on 07/11/2017.
 */

class BottomSheetLayout : FrameLayout {

    private lateinit var valueAnimator: ValueAnimator
    private var collapsedHeight: Int = 0

    private var progress = 0f
    private var startsCollapsed = true

    private var scrollTranslationY = 0f
    private var userTranslationY = 0f

    private var isScrollingUp: Boolean = false

    private var clickListener: OnClickListener? = null

    var animationDuration: Long = 300

    override fun setOnClickListener(l: OnClickListener?) {
        clickListener = l
    }

    private var progressListener: OnProgressListener? = null

    fun setOnProgressListener(l: OnProgressListener?) {
        progressListener = l
    }

    private val touchToDragListener = TouchToDragListener(true)

    fun setOnProgressListener(l: (progress: Float) -> Unit) {
        progressListener = object : OnProgressListener {
            override fun onProgress(progress: Float) {
                l(progress)
            }
        }
    }

    fun isExpended(): Boolean {
        return progress == 1f
    }

    constructor(context: Context) : super(context) {
        initView(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initView(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(attrs)
    }

    override fun setTranslationY(translationY: Float) {
        userTranslationY = translationY
        super.setTranslationY(scrollTranslationY + userTranslationY)
    }

    private fun initView(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BottomSheetLayout)

        collapsedHeight = a.getDimensionPixelSize(R.styleable.BottomSheetLayout_collapsedHeight, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            minimumHeight = Math.max(minimumHeight, collapsedHeight)
        }

        a.recycle()

        valueAnimator = ValueAnimator.ofFloat(0f, 1f)

        setOnTouchListener(touchToDragListener)

        if (height == 0) {
            addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
                override fun onLayoutChange(view: View, i: Int, i1: Int, i2: Int, i3: Int, i4: Int, i5: Int, i6: Int, i7: Int) {
                    removeOnLayoutChangeListener(this)
                    animate(0f)
                }
            })
        } else {
            animate(0f)
        }
    }

    fun toggle() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        val duration: Long
        valueAnimator = if (progress > 0.5f) {
            duration = (animationDuration * progress).toLong()
            ValueAnimator.ofFloat(progress, 0f)
        } else {
            duration = (animationDuration * (1 - progress)).toLong()
            ValueAnimator.ofFloat(progress, 1f)
        }

        valueAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            animate(progress)
        }

        valueAnimator.duration = duration

        valueAnimator.start()
    }

    fun collapse() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        valueAnimator = ValueAnimator.ofFloat(progress, 0f)

        valueAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            animate(progress)
        }

        valueAnimator.duration = (animationDuration * progress).toLong()

        valueAnimator.start()
    }

    fun expand() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        valueAnimator = ValueAnimator.ofFloat(progress, 1f)

        valueAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            animate(progress)
        }

        valueAnimator.duration = (animationDuration * (1 - progress)).toLong()

        valueAnimator.start()
    }

    //1 is expanded, 0 is collapsed
    private fun animate(progress: Float) {
        this.progress = progress
        val height = height
        val distance = height - collapsedHeight
        scrollTranslationY = distance * (1 - progress)
        super.setTranslationY(scrollTranslationY + userTranslationY)
        progressListener?.onProgress(progress)
    }

    private fun animateScroll(firstPos: Float, touchPos: Float) {
        val distance = touchPos - firstPos
        val height = height
        val totalDistance = height - collapsedHeight
        var progress = this.progress
        if (!startsCollapsed) {
            isScrollingUp = false
            progress = Math.max(0f, 1 - distance / totalDistance)
        } else if (startsCollapsed) {
            isScrollingUp = true
            progress = Math.min(1f, -distance / totalDistance)
        }
        progress = Math.max(0f, Math.min(1f, progress))
        animate(progress)
    }

    private fun animateScrollEnd() {
        if (valueAnimator.isRunning) {
            valueAnimator.cancel()
        }
        val duration: Long
        val progressLimit = if (isScrollingUp) 0.2f else 0.8f
        valueAnimator = if (progress > progressLimit) {
            duration = (animationDuration * (1 - progress)).toLong()
            ValueAnimator.ofFloat(progress, 1f)
        } else {
            duration = (animationDuration * progress).toLong()
            ValueAnimator.ofFloat(progress, 0f)
        }

        valueAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            animate(progress)
        }

        valueAnimator.duration = duration

        valueAnimator.start()
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (ev != null) {
            return touchToDragListener.onTouch(this, ev)
        }
        return false
    }

    private fun performChildClick(eventX: Float, eventY: Float): Boolean {
        return performChildClick(eventX, eventY, this, 0)
    }

    private fun performChildClick(eventX: Float, eventY: Float, viewGroup: ViewGroup, nest: Int): Boolean {

        for (i in (viewGroup.childCount - 1) downTo 0) {
            val view = viewGroup.getChildAt(i)
            if (isViewAtLocation(eventX, eventY, view)) {
                if (view is ViewGroup) {
                    val performChildClick = performChildClick(eventX - view.left, eventY - view.top, view, nest + 1)
                    if (performChildClick) {
                        return true
                    }
                }
                if (view.performClick()) {
                    return true
                }
            }
        }
        return performClick()
    }

    private fun isViewAtLocation(rawX: Float, rawY: Float, view: View): Boolean {
        if (view.left <= rawX && view.right >= rawX) {
            if (view.top <= rawY && view.bottom >= rawY) {
                return true
            }
        }
        return false
    }

    private inner class TouchToDragListener(private val touchToDrag: Boolean) : View.OnTouchListener {

        private val CLICK_ACTION_THRESHOLD = 200
        private var startX: Float = 0.toFloat()
        private var startY: Float = 0.toFloat()
        private var startTime: Double = 0.toDouble()

        override fun onTouch(v: View, ev: MotionEvent): Boolean {
            //val action = MotionEventCompat.getActionMasked(ev)
            val action = ev.action

            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    if (ev.pointerCount == 1) {
                        startX = ev.rawX
                        startY = ev.rawY
                        startTime = System.currentTimeMillis().toDouble()
                        startsCollapsed = progress < 0.5
                    }
                }

                MotionEvent.ACTION_MOVE -> {

                    val y = ev.rawY

                    animateScroll(startY, y)

                    invalidate()
                }

                MotionEvent.ACTION_UP -> {

                    val endX = ev.rawX
                    val endY = ev.rawY
                    if (isAClick(startX, endX, startY, endY, System.currentTimeMillis())) {
                        if (performChildClick(ev.x, ev.y)) {
                            return true
                        }
                        if (touchToDrag && clickListener != null) {
                            onClick()// WE HAVE A CLICK!!
                            return true
                        }
                    }

                    animateScrollEnd()
                }
            }
            return true
        }

        private fun isAClick(startX: Float, endX: Float, startY: Float, endY: Float, endTime: Long): Boolean {
            val differenceX = Math.abs(startX - endX)
            val differenceY = Math.abs(startY - endY)
            val differenceTime = Math.abs(startTime - endTime)
            return !(differenceX > CLICK_ACTION_THRESHOLD || differenceY > CLICK_ACTION_THRESHOLD || differenceTime > 400)
        }
    }

    private fun onClick() {
        clickListener?.onClick(this)
    }

    interface OnProgressListener {
        fun onProgress(progress: Float)
    }
}