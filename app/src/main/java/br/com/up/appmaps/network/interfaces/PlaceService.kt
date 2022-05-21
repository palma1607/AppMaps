package br.com.up.appmaps.network.interfaces

import br.com.up.appmaps.model.Place
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {

    @GET("json")
    fun getPlacesByTerm(@Query("query") term:String,
                        @Query("key") key:String):
            Call<List<Place>>
}