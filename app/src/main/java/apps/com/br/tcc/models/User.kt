package apps.com.br.tcc.models

import java.io.Serializable

data class User (
        val id: Int,
        val accountId: Int,
        var summonerName: String,
        val icon: String
) : Serializable {

}