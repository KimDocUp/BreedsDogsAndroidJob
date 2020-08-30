package test.job.serverconnect

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RImgBreeds (
    @SerializedName("message")
    @Expose
    var urlImg : ArrayList<String>
)