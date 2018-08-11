package apps.com.br.tcc.utils

import android.graphics.drawable.Drawable
import apps.com.br.tcc.R
import apps.com.br.tcc.dtos.ChampionDTO
import apps.com.br.tcc.dtos.RankingDTO
import apps.com.br.tcc.dtos.SummonerDto
import apps.com.br.tcc.models.Match
import apps.com.br.tcc.models.User
import apps.com.br.tcc.models.UserRank
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserDetailManager {
    companion object {
        const val USER_DETAIL_KEY = "user_detail"
        var matchHistory: List<Match>? = null
        var userDetails: User? = null
        var mainChampionDetails: ChampionDTO? = null
        var solo_ranking: UserRank? = null
        var flex_ranking: UserRank? = null

        const val RANKING_FLEX = "RANKED_FLEX_SR"
        const val RANKING_SOLO = "RANKED_SOLO_5x5"

        const val PROFILE_ICON_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/profileicon/"
        const val CHAMPION_IMAGE_BASE_URL = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"


        fun addUserInfo(summoner: SummonerDto) {
            userDetails = User(
                    id = summoner.id,
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
                    icon = "",
                    summonerName = summonerName
            )
        }

        fun loadMatchHistory() {
            val matchHistoryString = SharedPreferencesManager.getString(Companion.USER_DETAIL_KEY)

            matchHistory = if (matchHistoryString != null) {
                Gson().fromJson<List<Match>>(matchHistoryString, object : TypeToken<List<Match>>() {}.type)
            } else {
                ArrayList()
            }

            val items = listOf("http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/1001.png",
                    "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/1001.png",
                    "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/1001.png",
                    "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/1001.png",
                    "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/1001.png",
                    "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/item/1001.png")

            matchHistory = List(5) {
                Match(
                        1,
                        "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/profileicon/588.png",
                        "Vitória",
                        "9",
                        "0",
                        "5",
                        items
                )
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