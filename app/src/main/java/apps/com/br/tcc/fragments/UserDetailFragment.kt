package apps.com.br.tcc.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.com.br.tcc.R
import android.support.v7.widget.RecyclerView
import android.util.Log
import apps.com.br.tcc.adapters.MatchHistoryAdapter
import apps.com.br.tcc.utils.UserDetailManager
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlin.math.log


class UserDetailFragment : Fragment() {
    private var adapter: MatchHistoryAdapter? = null
    private var rvMatchHistory: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)
        rvMatchHistory = view.findViewById(R.id.rv_match_history)
        displayMatchHistory()
        return view
    }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()
    }


    private fun displayMatchHistory() {
        if(adapter == null) {

            adapter = MatchHistoryAdapter(UserDetailManager.matchHistory!!)

            val layoutManager = GridLayoutManager(context, 1)
            rvMatchHistory?.layoutManager = layoutManager
            rvMatchHistory?.adapter = adapter
        }
    }


}
