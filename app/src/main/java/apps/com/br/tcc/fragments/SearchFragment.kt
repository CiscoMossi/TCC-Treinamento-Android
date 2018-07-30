package apps.com.br.tcc.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import apps.com.br.tcc.R
import apps.com.br.tcc.adapters.HistoryAdapter
import apps.com.br.tcc.models.History

class SearchFragment : Fragment(), HistoryAdapter.Listener {
    private var adapter: HistoryAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)



        return view
    }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()
    }

    override fun onHistoryItemClicked(history: History) {
        //val intent = Intent(context, UserActivity::class.java)
        //intent.putExtra(key, history.usename)

        //startActivity(intent)

        Toast.makeText(context, history.usename, Toast.LENGTH_LONG).show()
    }

    private fun displayHistory() {
        if(adapter == null) {

        }
    }
}
