package apps.com.br.tcc.models

import android.graphics.drawable.Drawable
import java.io.Serializable

class UserRank (
        val icon: Int,
        val type: String,
        val pdl: Number
) : Serializable{
}