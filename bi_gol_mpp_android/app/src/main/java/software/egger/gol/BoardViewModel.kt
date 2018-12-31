package software.egger.gol

import android.arch.lifecycle.ViewModel
import software.egger.libgol.Board
import software.egger.libgol.cells
import software.egger.libgol.defaultBoard
import software.egger.libgol.translatedTo

class BoardViewModel : ViewModel() {

    val board = defaultBoard
    var cellSize: Double? = null
    var offsetX: Double? = null
    var offsetY: Double? = null

}