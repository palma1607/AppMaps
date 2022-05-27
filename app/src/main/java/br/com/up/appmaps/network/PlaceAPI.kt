package br.com.up.appmaps.network

import br.com.up.appmaps.model.Place
import br.com.up.appmaps.model.PlaceResponse
import br.com.up.appmaps.network.interfaces.PlaceService
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class PlaceAPI {


    private val baseUrl = "https://maps.googleapis.com/maps/api/place/textsearch/"
    private val apiKey = "AIzaSyDyFUltXibk_rGCb7Nak3wB5HC_KamkKRI"

    fun requestPlaceBySearchTerm(term: String,
                                 listener :
                                 OnPlaceResponseListener){

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory
                .create())
            .build()

       val call =  retrofit.create(PlaceService::class.java)
            .getPlacesByTerm(term, apiKey)

        call.enqueue(object : Callback<PlaceResponse>{
            override fun onResponse(call: Call<PlaceResponse>,
                                    response: Response<PlaceResponse>) {

                val places = response.body()?.places
                listener.onSuccess(places)
            }

            override fun onFailure(call: Call<PlaceResponse>,
                                   t: Throwable) {
                listener.onSuccess(null)
            }
        })
    }

    interface OnPlaceResponseListener{
        fun onSuccess(places:ArrayList<Place>?)
    }
}