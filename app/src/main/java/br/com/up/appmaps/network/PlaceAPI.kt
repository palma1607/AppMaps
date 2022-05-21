package br.com.up.appmaps.network

import br.com.up.appmaps.model.Place
import br.com.up.appmaps.network.interfaces.PlaceService
import retrofit2.*

class PlaceAPI {


    private val baseUrl = "https://maps.googleapis.com/maps/api/place/textsearch/"
    private val apiKey = "AIzaSyDyFUltXibk_rGCb7Nak3wB5HC_KamkKRI"

    fun requestPlaceBySearchTerm(term: String){

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

       val call =  retrofit.create(PlaceService::class.java)
            .getPlacesByTerm(term, apiKey)

        call.enqueue(object : Callback<List<Place>>{
            override fun onResponse(call: Call<List<Place>>,
                                    response: Response<List<Place>>) {

            }

            override fun onFailure(call: Call<List<Place>>,
                                   t: Throwable) {

            }
        })

    }

}