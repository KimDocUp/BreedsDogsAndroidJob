package test.job.serverconnect

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RBreeds (
    @SerializedName("message")
    @Expose
    var breedsNames: ArrayList<String>

)