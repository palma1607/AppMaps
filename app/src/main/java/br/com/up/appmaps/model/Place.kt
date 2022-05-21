package br.com.up.appmaps.model

import com.google.gson.annotations.SerializedName

data class Place(
    var name: String? = null,
    var rating: Float? = null,
    @SerializedName("formatted_address")
    var formattedAddress: String? = null
)
