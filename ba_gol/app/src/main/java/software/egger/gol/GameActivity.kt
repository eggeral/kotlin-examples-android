package software.egger.gol

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*
import software.egger.libgol.Cell
import software.egger.libgol.Game
import kotlin.concurrent.fixedRateTimer

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        val game = Game(cells)

        boardView.cellSize = 40.0f * resources.displayMetrics.density
        boardView.cells = cells

        fixedRateTimer("GameLoop", false, period = 500L) {
            game.calculateNextGeneration()
            boardView.invalidate()
        }

    }

    private val cells = arrayOf(
            arrayOf(
                    //0
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //1
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(true),
                    Cell(true),
                    Cell(true),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //2
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(true),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //3
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //4
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //5
                    Cell(false),
                    Cell(false),
                    Cell(true),
                    Cell(true),
                    Cell(true),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //6
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //7
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //8
                    Cell(false),
                    Cell(false),
                    Cell(true),
                    Cell(true),
                    Cell(true),
                    Cell(false),
                    Cell(false),
                    Cell(false)),
            arrayOf(
                    //9
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false),
                    Cell(false))
    )
}
