package apps.com.br.tcc.adapters

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.com.br.tcc.R
import apps.com.br.tcc.models.Match
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.match_history_item.view.*

class MatchHistoryAdapter(private val historyMatches: List<Match>) : RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder>() {
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

        Picasso.get().load(match.championIcon).into(holder.itemView.rv_champion_icon)
        Picasso.get().load(match.items[0]).into(holder.itemView.rv_item_1)
        Picasso.get().load(match.items[1]).into(holder.itemView.rv_item_2)
        Picasso.get().load(match.items[2]).into(holder.itemView.rv_item_3)
        Picasso.get().load(match.items[3]).into(holder.itemView.rv_item_4)
        Picasso.get().load(match.items[4]).into(holder.itemView.rv_item_5)
        Picasso.get().load(match.items[5]).into(holder.itemView.rv_item_6)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}