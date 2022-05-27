package br.com.up.appmaps.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("results")
    var places: ArrayList<Place>
)
