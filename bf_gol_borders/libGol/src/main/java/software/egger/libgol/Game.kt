package software.egger.libgol

class Game(private val cells: Array<Array<Cell>>) {


    fun calculateNextGeneration() {

        for ((rowIdx, row) in cells.withIndex()) {
            for ((colIdx, cell) in row.withIndex()) {
                cell.livingNeighbours = cells.countLivingNeighbours(colIdx, rowIdx)
            }
        }

        for (row in cells) {
            for (cell in row) {
                cell.calculateNextGeneration()
            }
        }

    }

}

fun Array<Array<Cell>>.countLivingNeighbours(column: Int, row: Int): Int {
    var count = 0
    if (row >= 1 && column >= 1 && this[row - 1][column - 1].alive) count++
    if (row >= 0 && column >= 1 && this[row][column - 1].alive) count++
    if (row < this.size - 1 && column >= 1 && this[row + 1][column - 1].alive) count++

    if (row >= 1 && this[row - 1][column].alive) count++
    if (row < this.size - 1 && this[row + 1][column].alive) count++

    if (row >= 1 && column < this[row - 1].size - 1 && this[row - 1][column + 1].alive) count++
    if (row >= 0 && column < this[row].size - 1 && this[row][column + 1].alive) count++
    if (row < this.size - 1 && column < this[row + 1].size - 1 && this[row + 1][column + 1].alive) count++


    return count
}

