package software.egger.ac_recycle_view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

data class Flight(var number: String, var from: String, var to: String)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flights = Arrays.asList(
                Flight("LH3454", "GRZ", "FRA"),
                Flight("OS101", "GRZ", "DUS"),
                Flight("BM2001", "MUC", "RST")
        )

        val flightsAdapter = FlightsAdapter()

        flightsRecyclerView.layoutManager = LinearLayoutManager(this)
        flightsRecyclerView.adapter = flightsAdapter

        flightsAdapter.displayFlights(flights)

    }

}
