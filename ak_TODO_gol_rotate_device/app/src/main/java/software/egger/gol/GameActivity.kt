package software.egger.gol

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_game.*
import software.egger.libgol.Cell
import software.egger.libgol.Game
import java.util.*
import kotlin.concurrent.fixedRateTimer
import android.R.attr.duration
import android.widget.Toast


class GameActivity : AppCompatActivity() {

    private var running = false
    private lateinit var playIcon: Drawable
    private lateinit var pauseIcon: Drawable
    private var timer: Timer? = null
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)
        setSupportActionBar(gameToolbar)

        playIcon = resources.getDrawable(R.drawable.ic_play_arrow_black_24dp, null)
        pauseIcon = resources.getDrawable(R.drawable.ic_pause_black_24dp, null)

        game = Game(cells)
        boardView.cells = cells
        boardView.cellSize = 40.0f * resources.displayMetrics.density

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus)
            boardView.centerBoard()
    }

    private fun play() {
        require(timer == null)
        timer = fixedRateTimer("GameLoop", false, period = 500L) {
            game.calculateNextGeneration()
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

    private val size = 1000
    private val cells = Array(size) { _ -> Array(size) { _ -> Cell(false) } }.apply {

        val center = size / 2
        get(center - 2)[center - 2].alive = true
        get(center - 1)[center - 2].alive = true
        get(center - 0)[center - 2].alive = true
        get(center + 2)[center - 2].alive = true

        get(center - 2)[center - 1].alive = true

        get(center + 1)[center - 0].alive = true
        get(center + 2)[center - 0].alive = true

        get(center - 1)[center + 1].alive = true
        get(center - 0)[center + 1].alive = true
        get(center + 2)[center + 1].alive = true

        get(center - 2)[center + 2].alive = true
        get(center - 0)[center + 2].alive = true
        get(center + 2)[center + 2].alive = true

    }
}
