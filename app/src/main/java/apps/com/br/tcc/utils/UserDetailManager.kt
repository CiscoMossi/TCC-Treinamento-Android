package apps.com.br.tcc.utils

import android.graphics.drawable.Drawable
import apps.com.br.tcc.R
import apps.com.br.tcc.api.LolService
import apps.com.br.tcc.dtos.ChampionDTO
import apps.com.br.tcc.dtos.MatchDetailDTO
import apps.com.br.tcc.dtos.RankingDTO
import apps.com.br.tcc.dtos.SummonerDto
import apps.com.br.tcc.models.Match
import apps.com.br.tcc.models.User
import apps.com.br.tcc.models.UserRank
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Response

@Suppress("IMPLICIT_BOXING_IN_IDENTITY_EQUALS")
class UserDetailManager {
    companion object {
        const val USER_DETAIL_KEY = "user_detail"
        var matchHistory: ArrayList<Match>? = null
        var userDetails: User? = null
        var mainChampionDetails: ChampionDTO? = null
        var solo_ranking: UserRank? = null
        var flex_ranking: UserRank? = null

        const val RANKING_FLEX = "RANKED_FLEX_SR"
        const val RANKING_SOLO = "RANKED_SOLO_5x5"

        const val apiKey = "RGAPI-76b4b99d-25eb-4995-9521-bc70dbfe6858"

        const val PROFILE_ICON_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/profileicon/"
        const val CHAMPION_IMAGE_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"
        const val CHAMPION_PROFILE_ICON = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/champion/"


        fun addUserInfo(summoner: SummonerDto) {
            userDetails = User(
                    id = summoner.id,
                    accountId = summoner.accountId,
                    summonerName = summoner.name,
                    icon = "${PROFILE_ICON_BASE_URL}${summoner.profileIconId}.png"
            )
            solo_ranking = UserRank(
                    type = "Unranked",
                    icon = R.drawable.provisional,
                    pdl = 0
            )

            flex_ranking = UserRank(
                    type = "Unranked",
                    icon = R.drawable.provisional,
                    pdl = 0
            )
        }

        fun addChampionInfo(champion: ChampionDTO) {
            mainChampionDetails = champion
        }

        fun handleRankings(rankings: List<RankingDTO>) {
            rankings.forEach({ r ->
                when (r.queueType) {
                    RANKING_FLEX -> flex_ranking = UserRank(
                            type = r.tier,
                            icon = getIcon(r.tier),
                            pdl = r.leaguePoints
                    )

                    RANKING_SOLO -> solo_ranking = UserRank(
                            type = r.tier,
                            icon = getIcon(r.tier),
                            pdl = r.leaguePoints
                    )
                }
            })
        }


        fun loadUserInfo(summonerName: String) {
            userDetails = User(
                    id = 0,
                    accountId = 0,
                    icon = "",
                    summonerName = summonerName
            )
        }

        fun loadMatchHistory() {
            val matchHistoryString = SharedPreferencesManager.getString(Companion.USER_DETAIL_KEY)

            matchHistory = if (matchHistoryString != null) {
                Gson().fromJson<ArrayList<Match>>(matchHistoryString, object : TypeToken<ArrayList<Match>>() {}.type)
            } else {
                ArrayList()
            }

        }

        fun handleMatchDetail (matchDetailDTO: MatchDetailDTO) {
            val participant = matchDetailDTO.participantIdentities.firstOrNull({p ->
                p.player.summonerId === UserDetailManager.userDetails?.id
            })

            val participantStats = matchDetailDTO.participants.firstOrNull({p ->
                p.participantId === participant?.participantId
            })

            val items  = listOf(
                participantStats?.stats?.item0.toString(),
                participantStats?.stats?.item1.toString(),
                participantStats?.stats?.item2.toString(),
                participantStats?.stats?.item3.toString(),
                participantStats?.stats?.item4.toString(),
                participantStats?.stats?.item5.toString()
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

                        matchHistory?.add(match!!)
                    }
                }


            })

        }

    }
}

private fun getIcon(tier: String) : Int {
    val icon = when (tier) {
        "BRONZE" -> R.drawable.bronze
        "SILVER" -> R.drawable.silver
        "GOLD" -> R.drawable.gold
        "PLATINUM" -> R.drawable.platinum
        "DIAMOND" -> R.drawable.diamond
        "MASTER" -> R.drawable.master
        "CHALLENGER" -> R.drawable.challenger

        else -> R.drawable.provisional
    }
    return icon
}