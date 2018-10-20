package software.egger.gol

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import software.egger.libgol.Board
import software.egger.libgol.Cell


class BoardView : View {

    private val paint = Paint().apply {
        color = Color.DKGRAY
        style = Paint.Style.FILL
        strokeWidth = 0.0f
    }

    var board: Board? = null
    var cellSize: Float = 25f
    var cellPaddingFactor: Float = 0.15f
    var offsetX = 0.0f
    var offsetY = 0.0f

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            // always return true otherwise the other gesture will not work
            return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            val rowIdx = rowFor(event.y)
            val columnIdx = columnFor(event.x)

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


    private val gestureDetector: GestureDetector = GestureDetector(context, gestureListener)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onTouchEvent(event: MotionEvent) = gestureDetector.onTouchEvent(event)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val board = board ?: return

        for (rowIdx in 0 until board.rows) {
            for (columnIdx in 0 until board.columns) {
                drawCell(canvas, board.cellAt(column = columnIdx, row = rowIdx), rowIdx, columnIdx)
            }
        }

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

    private fun rowFor(y: Float) = ((y - offsetY) / cellSize).toInt()

    private fun columnFor(x: Float) = ((x - offsetX) / cellSize).toInt()

}
