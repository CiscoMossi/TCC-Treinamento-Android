package apps.com.br.tcc.api

import okhttp3.internal.Internal
import okhttp3.internal.Internal.instance
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LolService {
    val BASE_URL = "https://br1.api.riotgames.com/lol/"

    private var instance: LolApi? = null


    init {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

        instance = retrofit.create(LolApi::class.java)
    }

    fun getInstance() : LolApi? {
        if (instance == null) {
            LolService()
        }

        return instance
    }




}