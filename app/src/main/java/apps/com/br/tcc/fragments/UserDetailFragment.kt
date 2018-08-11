package apps.com.br.tcc.fragments

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.com.br.tcc.R
import android.support.v7.widget.RecyclerView
import android.util.Log
import apps.com.br.tcc.R.id.tv_username
import apps.com.br.tcc.adapters.MatchHistoryAdapter
import apps.com.br.tcc.utils.UserDetailManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_detail.*
import kotlinx.android.synthetic.main.fragment_user_detail.view.*
import kotlinx.android.synthetic.main.history_item.view.*
import kotlin.math.log

class UserDetailFragment : Fragment() {
    private var adapter: MatchHistoryAdapter? = null
    private var rvMatchHistory: RecyclerView? = null

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
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)
        rvMatchHistory = view.findViewById(R.id.rv_match_history)

        setUserDetailOnView(view)
        displayMatchHistory()
        return view
    }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()
    }


    @SuppressLint("SetTextI18n")
    private fun setUserDetailOnView(view: View) {
        val user = UserDetailManager.userDetails!!

        view.collapse_toolbar.title = user.summonerName
        view.tv_champion.text = "Main ${user.mainChampionName}"
        view.iv_solo_icon.setImageDrawable(resources.getDrawable(user.soloRank.icon))
        view.tv_solo_ranking.text = user.soloRank.type
        view.tv_solo_lp.text = "${user.soloRank.pdl} PDL"
        view.tv_flex_ranking.text = user.flexRank.type
        view.tv_flex_lp.text = "${user.flexRank.pdl} PDL"
        view.iv_flex_icon.setImageDrawable(resources.getDrawable(user.flexRank.icon))
        Picasso.get().load(user.backgroundImage).into(view.iv_background)
        Picasso.get().load(user.icon).into(view.iv_player_icon)
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
