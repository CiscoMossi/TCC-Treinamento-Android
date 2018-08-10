package apps.com.br.tcc.models


import java.io.Serializable

data class Match(
        val id: Number,
        val championIcon: String,
        val status: String,
        val kill: String,
        val death: String,
        val assist: String,
        val items: List<String>
) : Serializable {
}