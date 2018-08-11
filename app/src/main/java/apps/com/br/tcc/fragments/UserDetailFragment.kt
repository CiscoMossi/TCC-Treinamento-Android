package apps.com.br.tcc.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import apps.com.br.tcc.R
import android.support.v7.widget.RecyclerView
import apps.com.br.tcc.adapters.MatchHistoryAdapter
import apps.com.br.tcc.api.LolService
import apps.com.br.tcc.dtos.*
import apps.com.br.tcc.models.Match
import apps.com.br.tcc.models.User
import apps.com.br.tcc.utils.UserDetailManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_user_detail.view.*
import retrofit2.Call
import retrofit2.Response


@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class UserDetailFragment : Fragment() {
    private var adapter: MatchHistoryAdapter? = null
    private var rvMatchHistory: RecyclerView? = null

    private val PROFILE_ICON_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/profileicon/"
    private val CHAMPION_IMAGE_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"
    private val CHAMPION_PROFILE_ICON = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion/"
    private val ITEM_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/"

    private val apiKey = "RGAPI-56c7273f-631d-4bab-a7e0-1fd4a7a9825a"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_detail, container, false)
        rvMatchHistory = view.findViewById(R.id.rv_match_history)

        fetchData(UserDetailManager.userDetails?.summonerName!!)

        displayMatchHistory()

        return view
    }

    override fun onResume() {
        super.onResume()

        adapter?.notifyDataSetChanged()
    }


    @SuppressLint("SetTextI18n")
    private fun setUserDetailOnView(view: View?) {
        val user = UserDetailManager.userDetails!!
        val champion = UserDetailManager.mainChampionDetails!!
        val soloRanking = UserDetailManager.solo_ranking!!
        val flexRanking = UserDetailManager.flex_ranking!!

        view?.collapse_toolbar?.title = user.summonerName
        view?.tv_champion?.text = "Main ${champion.name}"
        view?.iv_solo_icon?.setImageDrawable(resources.getDrawable(soloRanking.icon))
        view?.tv_solo_ranking?.text = soloRanking.type
        view?.tv_solo_lp?.text = "${soloRanking.pdl} PDL"
        view?.tv_flex_ranking?.text = flexRanking.type
        view?.tv_flex_lp?.text = "${flexRanking.pdl} PDL"
        view?.iv_flex_icon?.setImageDrawable(resources.getDrawable(flexRanking.icon))
        Picasso.get().load("${CHAMPION_IMAGE_BASE_URL}${champion.name}_0.jpg").into(view?.iv_background)
        Picasso.get().load(user.icon).into(view?.iv_player_icon)
    }

    private fun displayMatchHistory() {
        if(adapter == null) {

            adapter = MatchHistoryAdapter(UserDetailManager.matchHistory!!)

            val layoutManager = GridLayoutManager(context, 1)
            rvMatchHistory?.layoutManager = layoutManager
            rvMatchHistory?.adapter = adapter
        }
    }

    fun fetchData(summonerName: String) {
        val summonerNameNormalized = summonerName.toLowerCase()
        val lol = LolService().getInstance()?.getSummonerInfo(summonerNameNormalized, apiKey)

        lol?.enqueue(object: retrofit2.Callback<SummonerDto> {
            override fun onFailure(call: Call<SummonerDto>?, t: Throwable?) {
                return
            }

            override fun onResponse(call: Call<SummonerDto>?, response: Response<SummonerDto>?) {
                response?.body()?.let {
                    fetchUserChampionMasterie(it)
                    UserDetailManager.addUserInfo(it)
                    fetchMatchList(it)
                }
            }

        })

    }

    fun fetchUserChampionMasterie(summoner: SummonerDto) {
        var championIdWithMostMasterie : ChampionsMasterieDTO?

        val request = LolService().getInstance()?.getSummonerChampionMasteries(summoner.id, apiKey)

        request?.enqueue(object: retrofit2.Callback<List<ChampionsMasterieDTO>?> {
            override fun onFailure(call: Call<List<ChampionsMasterieDTO>?>?, t: Throwable?) {
               return
            }

            override fun onResponse(call: Call<List<ChampionsMasterieDTO>?>?, response: Response<List<ChampionsMasterieDTO>?>?) {
                response?.body()?.let {
                    championIdWithMostMasterie = it.first()

                    fetchChampionDetail(championIdWithMostMasterie!!.championId)
                }
            }
        })
    }

    fun fetchChampionDetail(championId: Int) {
        val request = LolService().getInstance()?.getChampionInfo(championId, "pt_BR", apiKey)
        var champion : ChampionDTO?

        request?.enqueue(object: retrofit2.Callback<ChampionDTO?> {
            override fun onFailure(call: Call<ChampionDTO?>, t: Throwable?) {
                return
            }

            override fun onResponse(call: Call<ChampionDTO?>, response: Response<ChampionDTO?>) {
                response?.body()?.let {
                    champion = it

                    UserDetailManager.addChampionInfo(champion!!)

                    fetchRankedInfo()
                }
            }
        })
    }

    fun fetchRankedInfo() {
        val request = LolService().getInstance()?.getRankedInfo(UserDetailManager.userDetails?.id!!, apiKey)
        var rankings : List<RankingDTO>?

        request?.enqueue(object: retrofit2.Callback<List<RankingDTO>> {
            override fun onFailure(call: Call<List<RankingDTO>>?, t: Throwable?) {
              return
            }

            override fun onResponse(call: Call<List<RankingDTO>>?, response: Response<List<RankingDTO>>?) {
                response?.body()?.let {
                    rankings = it

                    UserDetailManager.handleRankings(rankings!!)
                    setUserDetailOnView(view)
                }
            }
        })
    }

    fun fetchMatchList(summoner: SummonerDto) {
        val request = LolService().getInstance()?.getMatchList(summoner.accountId, 3, apiKey)
        var matchList: MatchListDTO?

        request?.enqueue(object : retrofit2.Callback<MatchListDTO> {
            override fun onFailure(call: Call<MatchListDTO>?, t: Throwable?) {
                return
            }

            override fun onResponse(call: Call<MatchListDTO>?, response: Response<MatchListDTO>?) {
                response?.body()?.let {
                    matchList = it

                    matchList?.matches?.forEach({ m -> fetchMatchDetails(m) })
                }
            }
        })
    }

    fun fetchMatchDetails(matchDto: MatchDTO) {
        val request = LolService().getInstance()?.getMatchDetails(matchDto.gameId, apiKey)
        var matchDetail: MatchDetailDTO?

        request?.enqueue(object : retrofit2.Callback<MatchDetailDTO> {
            override fun onFailure(call: Call<MatchDetailDTO>?, t: Throwable?) {
                return
            }

            override fun onResponse(call: Call<MatchDetailDTO>?, response: Response<MatchDetailDTO>?) {
                response?.body()?.let {
                    matchDetail = it

                    handleMatchDetail(matchDetail!!)
                }
            }
        })
    }

    fun handleMatchDetail (matchDetailDTO: MatchDetailDTO) {
        val participant = matchDetailDTO.participantIdentities.firstOrNull({p ->
            p.player.summonerId === UserDetailManager.userDetails?.id
        })

        val participantStats = matchDetailDTO.participants.firstOrNull({p ->
            p.participantId === participant?.participantId
        })

        val items  = listOf(
                "${ITEM_BASE_URL}${participantStats?.stats?.item0.toString()}.png",
                "${ITEM_BASE_URL}${participantStats?.stats?.item1.toString()}.png",
                "${ITEM_BASE_URL}${participantStats?.stats?.item2.toString()}.png",
                "${ITEM_BASE_URL}${participantStats?.stats?.item3.toString()}.png",
                "${ITEM_BASE_URL}${participantStats?.stats?.item4.toString()}.png",
                "${ITEM_BASE_URL}${participantStats?.stats?.item5.toString()}.png"
        )


        val request = LolService().getInstance()?.getChampionInfo(
                participantStats?.championId!!, "pt_BR", apiKey)

        var match : Match? = null

        request?.enqueue(object: retrofit2.Callback<ChampionDTO?> {
            override fun onFailure(call: Call<ChampionDTO?>, t: Throwable?) {
                return
            }

            override fun onResponse(call: Call<ChampionDTO?>, response: Response<ChampionDTO?>) {
                response?.body()?.let {

                    match = Match (
                            id = matchDetailDTO.gameId,
                            status = if(participantStats?.stats?.win!!) {
                                "Vitória"
                            } else "Derrota",
                            kill = participantStats.stats.kills.toString(),
                            death = participantStats.stats.deaths.toString(),
                            assist = participantStats.stats.assists.toString(),
                            items = items,
                            championIcon = "${CHAMPION_PROFILE_ICON}${it.name}.png"
                    )


                    this@UserDetailFragment.adapter?.addMatch(match!!)
                }
            }


        })

    }
}
