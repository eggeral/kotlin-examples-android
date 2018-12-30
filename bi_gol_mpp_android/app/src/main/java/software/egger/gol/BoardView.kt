package software.egger.gol

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import software.egger.libgol.*
import software.egger.libgolandroid.AndroidCanvas

class BoardView : View {

    private var cellPaddingFactor: Float = 0.15f

    var board: Board? = null
    var cellSize: Float = 25f

    var offsetX = 0.0f
    var offsetY = 0.0f
    private var minCellSize = 10 * resources.displayMetrics.density
    private var maxCellSize = 60 * resources.displayMetrics.density


    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            // always return true otherwise the other gesture will not work
            return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            val rowIdx = rowForScreenY(event.y)
            val columnIdx = columnForScreenX(event.x)

            val board = board ?: return true
            if (rowIdx !in 0 until board.rows) return true
            if (columnIdx !in 0 until board.columns) return true

            with(board.cellAt(column = columnIdx, row = rowIdx)) {
                alive = !alive
            }

            invalidate()
            return true
        }

        override fun onScroll(start: MotionEvent, end: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            offsetX -= distanceX
            offsetY -= distanceY

            invalidate()
            return true
        }

    }

    private val zoomListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {

            val board = board ?: return true

            boardDisplay.scale(
                    board,
                    detector.scaleFactor.toDouble(),
                    width.toDouble(),
                    height.toDouble(),
                    detector.focusX.toDouble(),
                    detector.focusY.toDouble()
            )

            invalidate()
            return true

        }

    }

    private val gestureDetector: GestureDetector = GestureDetector(context, gestureListener)
    private val zoomDetector: ScaleGestureDetector = ScaleGestureDetector(context, zoomListener)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var result = zoomDetector.onTouchEvent(event)
        if (!zoomDetector.isInProgress) {
            result = gestureDetector.onTouchEvent(event)
        }
        return result
    }

    private val boardDisplay = BoardDisplay(minCellSize.toDouble(), maxCellSize.toDouble())
    private val commonCanvas = AndroidCanvas()

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        val board = board ?: return
        commonCanvas.canvas = canvas
        boardDisplay.draw(commonCanvas,board, width.toDouble(), height.toDouble())

    }

    fun centerBoard() {
        val board = board ?: return
        boardDisplay.centerBoard(board, width.toDouble(), height.toDouble())
        invalidate()
    }

    private fun rectFor(rowIdx: Int, columnIdx: Int): Rectangle {

        val cellPadding = cellSize * cellPaddingFactor

        val left = (columnIdx * cellSize) + cellPadding
        val right = (left + cellSize) - cellPadding
        val top = (rowIdx * cellSize) + cellPadding
        val bottom = (top + cellSize) - cellPadding

        return Rectangle(
                (left + offsetX).toDouble(),
                (top + offsetY).toDouble(),
                (right + offsetX).toDouble(),
                (bottom + offsetY).toDouble()
        )
    }

    private fun idxForOffset(offset: Float) = (-offset / cellSize).toInt()

    private fun rowForScreenY(y: Float) = ((y - offsetY) / cellSize).toInt()
    private fun columnForScreenX(x: Float) = ((x - offsetX) / cellSize).toInt()

}
