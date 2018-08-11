package apps.com.br.tcc.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import apps.com.br.tcc.R
import apps.com.br.tcc.models.History
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter(private val historyItems: MutableList<History>, private val listener: Listener) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.history_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history: History = historyItems.get(position)
        //Picasso
        holder.itemView.tv_username.text = history.username

        holder.itemView.setOnClickListener({
            listener.onHistoryItemClicked(history)
        })
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivUseIcon: ImageView = itemView.findViewById(R.id.iv_history_item_icon)
        private val tvUsername: TextView = itemView.findViewById(R.id.tv_username)
    }

    interface Listener {
        fun onHistoryItemClicked(history: History)
    }
}