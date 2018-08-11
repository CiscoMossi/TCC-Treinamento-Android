package apps.com.br.tcc.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.com.br.tcc.R
import apps.com.br.tcc.models.Match
import kotlinx.android.synthetic.main.match_history_item.view.*

class MatchHistoryAdapter(private val historyMatches: MutableList<Match>) : RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.match_history_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int{
        return historyMatches.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match: Match = historyMatches.get(position)

        holder.itemView.rv_match_status.text = match.status
        holder.itemView.rv_player_performance.text = """${match.kill}\${match.death}\${match.assist}"""
        //Picasso to add pictures
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}