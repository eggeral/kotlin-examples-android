package software.egger.libgol

import kotlin.math.max
import kotlin.math.min

interface CommonCanvas {
    fun drawRect(rectangle: Rectangle, commonPaint: CommonPaint)

}

class Rectangle(val left: Double, val top: Double, val right: Double, val bottom: Double)

enum class Style {
    Fill
}

class CommonPaint {
    var color = CommonColor(0x00, 0x00, 0x00)
    var style = Style.Fill
    var strokeWidth = 1.0
}

data class CommonColor(val red: Int, val green: Int, val blue: Int, val alpha: Float = 1.0f)

class BoardDisplay(private val minCellSize: Double, private val maxCellSize: Double) {

    private val paint = CommonPaint().apply {
        color = CommonColor(0x44, 0x44, 0x44)
        style = Style.Fill
        strokeWidth = 0.0
    }
    private var cellPaddingFactor: Float = 0.15f

    var cellSize: Double = 25.0

    var offsetX = 0.0
    var offsetY = 0.0

    fun draw(commonCanvas: CommonCanvas, board: Board, width: Double, height: Double) {

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
                drawCell(commonCanvas, board.cellAt(column = columnIdx, row = rowIdx), rowIdx, columnIdx)
            }
        }

    }

    fun centerBoard(board: Board, width: Double, height: Double) {
        offsetX = (width - board.rows * cellSize - cellSize) / 2.0f
        offsetY = (height - board.columns * cellSize - cellSize) / 2.0f
    }

    fun scale(board: Board, scaleFactor: Double, width: Double, height: Double, focusX: Double, focusY: Double) {
        val newCellSize = cellSize * scaleFactor
        val oldCellSize = cellSize


        cellSize = when {
            newCellSize * board.columns < width -> cellSize
            newCellSize * board.rows < height -> cellSize
            newCellSize < minCellSize -> minCellSize
            newCellSize > maxCellSize -> maxCellSize
            else -> newCellSize
        }

        val realScaleFactor = cellSize / oldCellSize

        val distX = focusX - offsetX
        val distY = focusY - offsetY

        val corrX = distX - distX * realScaleFactor
        val corrY = distY - distY * realScaleFactor

        offsetX += corrX
        offsetY += corrY
    }

    private fun drawCell(commonCanvas: CommonCanvas, cell: Cell, rowIdx: Int, columnIdx: Int) {

        if (cell.alive) {
            commonCanvas.drawRect(rectFor(rowIdx, columnIdx), paint)
        }
    }

    private fun rectFor(rowIdx: Int, columnIdx: Int): Rectangle {

        val cellPadding = cellSize * cellPaddingFactor

        val left = (columnIdx * cellSize) + cellPadding
        val right = (left + cellSize) - cellPadding
        val top = (rowIdx * cellSize) + cellPadding
        val bottom = (top + cellSize) - cellPadding

        return Rectangle(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
    }

    private fun idxForOffset(offset: Double) = (-offset / cellSize).toInt()

}

