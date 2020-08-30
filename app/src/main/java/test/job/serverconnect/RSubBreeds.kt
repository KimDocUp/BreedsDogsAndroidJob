package test.job.serverconnect

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RSubBreeds(
    @SerializedName("message")
    @Expose
    var namesSubBreeds: ArrayList<String>
)