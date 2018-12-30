package software.egger.gol

import android.arch.lifecycle.ViewModel
import software.egger.libgol.Board
import software.egger.libgol.cells
import software.egger.libgol.translatedTo

class BoardViewModel : ViewModel() {

    private val size = 1000

    val board: Board = Board(size, size).apply {
        setCells(
                """
                ***_*
                *____
                ___**
                _**_*
                *_*_*
                """.trimIndent().cells().translatedTo(size / 2 - 2, size / 2 - 2))

    }

    var cellSize: Double? = null
    var offsetX: Double? = null
    var offsetY: Double? = null
}