package software.egger.gol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import software.egger.libgol.Board
import software.egger.libgol.Cell
import software.egger.libgol.cells
import software.egger.libgol.translatedTo
import kotlin.concurrent.fixedRateTimer

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        val size = 15
        val board = Board(size, size)
        board.setCells(
                """
                ***_*
                *____
                ___**
                _**_*
                *_*_*
                """.trimIndent().cells().translatedTo(size / 2 - 2, size / 2 - 2))


        boardView.cellSize = 40.0f * resources.displayMetrics.density
        boardView.board = board

        fixedRateTimer("GameLoop", false, period = 500L) {
            board.calculateNextGeneration()
            boardView.invalidate()
        }

    }

}
