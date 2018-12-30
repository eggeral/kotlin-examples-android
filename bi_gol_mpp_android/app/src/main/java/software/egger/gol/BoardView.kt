package software.egger.gol

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import software.egger.libgol.BoardDisplay
import software.egger.libgolandroid.AndroidCanvas

class BoardView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    lateinit var boardDisplay: BoardDisplay
    private val commonCanvas = AndroidCanvas()

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            // always return true otherwise the other gesture will not work
            return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {

            boardDisplay.tap(event.x.toDouble(), event.y.toDouble())
            invalidate()
            return true
        }

        override fun onScroll(start: MotionEvent, end: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            boardDisplay.scroll(distanceX.toDouble(), distanceY.toDouble())
            invalidate()
            return true
        }

    }

    private val zoomListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            boardDisplay.scale(
                    detector.scaleFactor.toDouble(),
                    detector.focusX.toDouble(),
                    detector.focusY.toDouble()
            )

            invalidate()
            return true

        }

    }

    private val gestureDetector: GestureDetector = GestureDetector(context, gestureListener)
    private val zoomDetector: ScaleGestureDetector = ScaleGestureDetector(context, zoomListener)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var result = zoomDetector.onTouchEvent(event)
        if (!zoomDetector.isInProgress) {
            result = gestureDetector.onTouchEvent(event)
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        commonCanvas.canvas = canvas
        boardDisplay.draw(commonCanvas)

    }

}
