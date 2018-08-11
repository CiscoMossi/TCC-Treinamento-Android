package apps.com.br.tcc.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.com.br.tcc.R
import android.support.v7.widget.RecyclerView
import apps.com.br.tcc.adapters.MatchHistoryAdapter
import apps.com.br.tcc.utils.UserDetailManager
import kotlin.math.log
import kotlinx.android.synthetic.main.fragment_user_detail.rv_match_history as rvMatchHistory

class UserDetailFragment : Fragment() {
    private var adapter: MatchHistoryAdapter? = null

    companion object {
        val TAG = "UserDetailFragment"
        val USERNAME_KEY = "USERNAME"

        fun newInstance(username: String?): UserDetailFragment {
            val args = Bundle()
            args.putString(USERNAME_KEY, username)

            val fragment = UserDetailFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.user_detail_fragment, container, false)
        val username = arguments?.getString(USERNAME_KEY)

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
