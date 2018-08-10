package apps.com.br.tcc.models

import java.io.Serializable

data class User (
        val id: Number,
        val summonerName: String,
        val mainChampionName: String,
        val soloRank: UserRank,
        val flexRank: UserRank,
        val icon: String,
        val backgroundImage: String
) : Serializable {

}