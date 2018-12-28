package software.egger.libgol

import kotlin.math.max
import kotlin.math.min

interface Canvas {
    fun drawRect(rectangle: RectF, paint: Paint)

}

class RectF(left: Double, top: Double, right: Double, bottom: Double)

enum class Style {
    Fill
}

class Paint() {
    var color = Color(0x00, 0x00, 0x00)
    var style = Style.Fill
    var strokeWidth = 1.0
}

data class Color(val red: Int, val green: Int, val blue: Int, val alpha: Float = 1.0f)

class BoardDisplay(var width: Double, var height: Double, val minCellSize: Double, val maxCellSize: Double) {

    private val paint = Paint().apply {
        color = Color(0x44, 0x44, 0x44)
        style = Style.Fill
        strokeWidth = 0.0
    }
    private var cellPaddingFactor: Float = 0.15f

    var board: Board? = null
    var cellSize: Double = 25.0

    var offsetX = 0.0
    var offsetY = 0.0


    fun draw(canvas: Canvas) {

        val board = board ?: return

        val leftBorder = 0.0
        val topBorder = 0.0
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

    private fun idxForOffset(offset: Double) = (-offset / cellSize).toInt()

    private fun rowForScreenY(y: Double) = ((y - offsetY) / cellSize).toInt()
    private fun columnForScreenX(x: Double) = ((x - offsetX) / cellSize).toInt()

}

