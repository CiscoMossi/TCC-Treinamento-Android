package apps.com.br.tcc.utils

import android.graphics.drawable.Drawable
import apps.com.br.tcc.R
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

        fun loadData(summonerName: String) {

            loadUserInfo(summonerName)
            loadMatchHistory()
        }

        fun loadUserInfo(summonerName: String) {

            userDetails = User(
                1,
                summonerName = summonerName,
                mainChampionName = "Jax",
                soloRank =  UserRank (
                        icon = R.drawable.challenger,
                        type = "Challenger",
                        pdl = 50
                ),
                flexRank =  UserRank (
                        icon = R.drawable.diamond,
                        type = "Diamond",
                        pdl = 50
                ),
                icon = "http://ddragon.leagueoflegends.com/cdn/6.24.1/img/profileicon/588.png",
                backgroundImage = "http://ddragon.leagueoflegends.com/cdn/img/champion/splash/Jax_13.jpg"
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