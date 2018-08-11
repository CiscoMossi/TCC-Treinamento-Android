package apps.com.br.tcc.utils

import android.graphics.drawable.Drawable
import apps.com.br.tcc.R
import apps.com.br.tcc.adapters.MatchHistoryAdapter
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


        const val PROFILE_ICON_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/profileicon/"


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