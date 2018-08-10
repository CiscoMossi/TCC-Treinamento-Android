package apps.com.br.tcc.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import apps.com.br.tcc.R
import apps.com.br.tcc.adapters.HistoryAdapter
import apps.com.br.tcc.utils.HistoryManager
import kotlinx.android.synthetic.main.fragment_search.et_search as search

class SearchFragment : Fragment() {
    private var adapter: HistoryAdapter? = null
    private var rvHistory: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        rvHistory = view.findViewById(R.id.rv_last_searchs)

        setOnSubmitListener()

        displayHistory()
        return view
    }

    private fun setOnSubmitListener() {
        search.setOnEditorActionListener(activity as TextView.OnEditorActionListener)
    }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()
    }

    private fun displayHistory() {
        if(adapter == null) {
            adapter = HistoryAdapter(HistoryManager.history, activity as HistoryAdapter.Listener)
            val layoutManager = GridLayoutManager(context, 1)
            rvHistory?.layoutManager = layoutManager
            rvHistory?.adapter = adapter
        }
    }
}
