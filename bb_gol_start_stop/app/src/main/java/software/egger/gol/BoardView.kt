package software.egger.gol

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
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
    var cellPadding: Float = 10f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

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
        val left = (columnIdx * cellSize) + cellPadding
        val right = (left + cellSize) - cellPadding
        val top = (rowIdx * cellSize) + cellPadding
        val bottom = (top + cellSize) - cellPadding

        return RectF(left, top, right, bottom)
    }

}
