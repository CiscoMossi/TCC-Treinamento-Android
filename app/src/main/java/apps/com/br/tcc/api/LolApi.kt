package apps.com.br.tcc.api

import apps.com.br.tcc.dtos.ChampionDTO
import apps.com.br.tcc.dtos.ChampionsMasterieDTO
import apps.com.br.tcc.dtos.RankingDTO
import apps.com.br.tcc.dtos.SummonerDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LolApi {
    @GET("summoner/v3/summoners/by-name/{summonerName}")
    fun getSummonerInfo(
            @Path("summonerName") summonerName: String,
            @Query("api_key") api_key: String) : Call<SummonerDto>

    @GET("champion-mastery/v3/champion-masteries/by-summoner/{summonerId}")
    fun getSummonerChampionMasteries(
            @Path("summonerId") summonerId: Int,
            @Query("api_key") api_key: String) : Call<List<ChampionsMasterieDTO>>

    @GET("static-data/v3/champions/{id}")
    fun getChampionInfo(
            @Path("id") id: Number,
            @Query("locale") locale: String,
            @Query("api_key") api_key: String) : Call<ChampionDTO>


    @GET("league/v3/positions/by-summoner/{summonerId}")
    fun getRankedInfo(
            @Path("summonerId") id: Int,
            @Query("api_key") api_key: String) : Call<List<RankingDTO>>

}