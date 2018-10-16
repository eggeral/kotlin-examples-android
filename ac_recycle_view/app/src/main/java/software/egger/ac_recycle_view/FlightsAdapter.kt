package software.egger.ac_recycle_view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList

import kotlinx.android.synthetic.main.flight_list_row.view.*

const val TAG = "software.egger.ac_recycle_view"

class FlightsAdapter : RecyclerView.Adapter<FlightsAdapter.ViewHolder>() {

    private val flights = ArrayList<Flight>()
    override fun getItemCount() = flights.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var flight: Flight? = null

        init {

            itemView.setOnClickListener { Log.i(TAG, "onClick: " + flight?.number) }

        }

        fun updateView(flight: Flight) {

            // itemView. does the trick!
            itemView.flightNumberTextView.text = flight.number
            itemView.flightFromTextView.text = flight.from
            itemView.flightToTextView.text = flight.to
            this.flight = flight

        }

    }

    override fun onBindViewHolder(holder: FlightsAdapter.ViewHolder, position: Int) {
        holder.updateView(flights[position])
    }

    fun displayFlights(flights: List<Flight>?) {

        this.flights.clear()
        if (flights != null) {
            this.flights.addAll(flights)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flight_list_row, parent, false)
        return ViewHolder(view)
    }

}
