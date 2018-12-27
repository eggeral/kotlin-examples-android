package software.egger.gol

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import software.egger.libgol.Board
import software.egger.libgol.Cell
import kotlin.math.max
import kotlin.math.min

class BoardView : View {

    private val paint = Paint().apply {
        color = Color.DKGRAY
        style = Paint.Style.FILL
        strokeWidth = 0.0f
    }
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

            val newCellSize = cellSize * detector.scaleFactor
            val oldCellSize = cellSize

            val board = board ?: return true

            cellSize = when {
                newCellSize * board.columns < width -> cellSize
                newCellSize * board.rows < height -> cellSize
                newCellSize < minCellSize -> minCellSize
                newCellSize > maxCellSize -> maxCellSize
                else -> newCellSize
            }

            val realScaleFactor = cellSize / oldCellSize

            val distX = detector.focusX - offsetX
            val distY = detector.focusY - offsetY

            val corrX = distX - distX * realScaleFactor
            val corrY = distY - distY * realScaleFactor

            offsetX += corrX
            offsetY += corrY

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

    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)

        val board = board ?: return

        val leftBorder = 0.0f
        val topBorder = 0.0f
        val rightBorder = width - board.columns * cellSize
        val bottomBorder = height - board.rows * cellSize

        if (offsetX < rightBorder) offsetX = rightBorder
        if (offsetY < bottomBorder) offsetY = bottomBorder

        if (offsetX > leftBorder) offsetX = leftBorder
        if (offsetY > topBorder) offsetY = topBorder

        val columnIdxStart = max(0, idxForOffset(offsetX))
        val columnIdxEnd = min(board.columns, idxForOffset(offsetX - width) + 1)

        val rowIdxStart = max(0, idxForOffset(offsetY))
        val rowIdxEnd = min(board.rows, idxForOffset(offsetY - height) + 1)

        for (rowIdx in rowIdxStart until rowIdxEnd) {
            for (columnIdx in columnIdxStart until columnIdxEnd) {
                drawCell(canvas, board.cellAt(column = columnIdx, row = rowIdx), rowIdx, columnIdx)
            }
        }

    }

    fun centerBoard() {
        val board = board ?: return
        offsetX = (width - board.rows * cellSize - cellSize) / 2.0f
        offsetY = (height - board.columns * cellSize - cellSize) / 2.0f
        invalidate()
    }

    private fun drawCell(canvas: Canvas, cell: Cell, rowIdx: Int, columnIdx: Int) {

        if (cell.alive) {
            canvas.drawRect(rectFor(rowIdx, columnIdx), paint)
        }
    }

    private fun rectFor(rowIdx: Int, columnIdx: Int): RectF {

        val cellPadding = cellSize * cellPaddingFactor

        val left = (columnIdx * cellSize) + cellPadding
        val right = (left + cellSize) - cellPadding
        val top = (rowIdx * cellSize) + cellPadding
        val bottom = (top + cellSize) - cellPadding

        return RectF(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
    }

    private fun idxForOffset(offset: Float) = (-offset / cellSize).toInt()

    private fun rowForScreenY(y: Float) = ((y - offsetY) / cellSize).toInt()
    private fun columnForScreenX(x: Float) = ((x - offsetX) / cellSize).toInt()

}
