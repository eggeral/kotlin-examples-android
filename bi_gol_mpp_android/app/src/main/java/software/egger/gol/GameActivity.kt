package software.egger.gol

import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_game.*
import software.egger.libgol.BoardDisplay
import java.util.*
import kotlin.concurrent.fixedRateTimer

class GameActivity : AppCompatActivity() {

    private var running = false
    private lateinit var playIcon: Drawable
    private lateinit var pauseIcon: Drawable
    private lateinit var boardViewModel: BoardViewModel
    private lateinit var boardDisplay: BoardDisplay

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)
        setSupportActionBar(gameToolbar)

        playIcon = resources.getDrawable(R.drawable.ic_play_arrow_black_24dp, null)
        pauseIcon = resources.getDrawable(R.drawable.ic_pause_black_24dp, null)

        boardViewModel = ViewModelProviders.of(this).get(BoardViewModel::class.java)

        boardDisplay = BoardDisplay(
                board = boardViewModel.board,
                minCellSize = 10.0 * resources.displayMetrics.density,
                maxCellSize = 60.0 * resources.displayMetrics.density,
                width = { boardView.width.toDouble() },
                height = { boardView.height.toDouble() },
                cellSize = boardViewModel.cellSize ?: 40.0 * resources.displayMetrics.density,
                offsetX = boardViewModel.offsetX ?: 0.0,
                offsetY = boardViewModel.offsetY ?: 0.0
        )

        boardView.boardDisplay = boardDisplay
    }

    override fun onPause() {
        super.onPause()
        boardViewModel.cellSize = boardDisplay.cellSize
        boardViewModel.offsetX = boardDisplay.offsetX
        boardViewModel.offsetY = boardDisplay.offsetY
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && boardViewModel.cellSize == null)
            boardDisplay.centerBoard()
    }

    private fun play() {
        require(timer == null)
        timer = fixedRateTimer("GameLoop", false, period = 500L) {
            boardViewModel.board.calculateNextGeneration()
            boardView.invalidate()
        }
    }

    private fun pause() {
        val timer = requireNotNull(timer)
        timer.cancel()
        this.timer = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override
    fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_start_stop -> {
                if (running) {
                    item.icon = playIcon
                    pause()
                } else {
                    item.icon = pauseIcon
                    play()
                }
                running = !running
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

    }


}
