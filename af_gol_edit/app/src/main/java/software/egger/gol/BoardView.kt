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
import software.egger.libgol.Cell
import kotlin.math.ceil

class BoardView : View {

    private val paint = Paint().apply {
        color = Color.DKGRAY
        style = Paint.Style.FILL
        strokeWidth = 0.0f
    }

    var cells: Array<Array<Cell>> = arrayOf(arrayOf())
    var cellSize: Float = 25f
    var cellPadding: Float = 10f

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            // always return true otherwise the other gesture will not work
            return true
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            val rowIdx = rowFor(event.y)
            val columnIdx = columnFor(event.x)

            if (rowIdx < 0 || rowIdx >= cells.size) return true
            if (columnIdx < 0 || columnIdx >= cells[rowIdx].size) return true

            with(cells[rowIdx][columnIdx]) {
                alive = !alive
            }

            invalidate()
            return true
        }
    }

    private val gestureDetector: GestureDetector = GestureDetector(context, gestureListener)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for ((rowIdx, row) in cells.withIndex()) {
            for ((columnIdx, cell) in row.withIndex()) {
                drawCell(canvas, cell, rowIdx, columnIdx)
            }
        }

    }

    private fun drawCell(canvas: Canvas, cell: Cell, rowIdx: Int, columnIdx: Int) {
        if (cell.alive) {
            canvas.drawRect(rectFor(rowIdx, columnIdx), paint)
        }
    }

    private fun rectFor(rowIdx: Int, columnIdx: Int): RectF {
        val left = (columnIdx * cellSize) + cellPadding
        val right = (left + cellSize) - cellPadding
        val top = (rowIdx * cellSize) + cellPadding
        val bottom = (top + cellSize) - cellPadding

        return RectF(left, top, right, bottom)
    }

    private fun rowFor(y: Float) = (y / cellSize).toInt()

    private fun columnFor(x: Float) = (x / cellSize).toInt()

    override fun onTouchEvent(event: MotionEvent) = gestureDetector.onTouchEvent(event)

}
