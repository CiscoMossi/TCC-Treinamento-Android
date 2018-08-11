package apps.com.br.tcc.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import apps.com.br.tcc.R
import apps.com.br.tcc.adapters.HistoryAdapter
import apps.com.br.tcc.utils.HistoryManager
import apps.com.br.tcc.models.History


class SearchFragment : Fragment() {
    private var adapter: HistoryAdapter? = null
    private var rvHistory: RecyclerView? = null
    private val historyCopy: ArrayList<History> = ArrayList()
    private var etSearch: EditText? = null
    private var btSearch: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        this.rvHistory = view.findViewById(R.id.rv_last_searchs)
        this.etSearch = view.findViewById(R.id.et_search)
        this.btSearch = view.findViewById(R.id.bt_search)

        setBtOnClickListener()

        displayHistory()
        return view
    }

    fun onKeyUp(): Boolean {
        filter(this.etSearch?.text.toString())
        return true
    }

    private fun setBtOnClickListener() {
        this.btSearch?.setOnClickListener(activity as View.OnClickListener)
    }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()
    }

    private fun filter(text: String) {
        if (!text.isEmpty()) {
            val lowerText = text.toLowerCase()
            this.historyCopy.clear()
            this.historyCopy.addAll(HistoryManager.history.filter { item -> item.username.toLowerCase().contains(lowerText) } as ArrayList<History>)
        } else {
            this.historyCopy.clear()
            this.historyCopy.addAll(HistoryManager.history)
        }
        adapter?.notifyDataSetChanged()
    }

    private fun displayHistory() {
        this.historyCopy.addAll(HistoryManager.history  )

        if(adapter == null) {
            adapter = HistoryAdapter(this.historyCopy, activity as HistoryAdapter.Listener)

            val layoutManager = GridLayoutManager(context, 1)
            rvHistory?.layoutManager = layoutManager
            rvHistory?.adapter = adapter
        }
    }
}
