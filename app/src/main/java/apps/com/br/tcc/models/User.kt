package apps.com.br.tcc.models

import java.io.Serializable

data class User (
        val id: Int,
        var summonerName: String,
        val icon: String
) : Serializable {

}