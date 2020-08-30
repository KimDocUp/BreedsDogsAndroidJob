package test.job.serverconnect

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("/breeds/list/")
    fun getBreeds(): Call<RBreeds>

    @GET("/breed/{NB}/images")
    fun getImgBreeds(@Path("NB") breedName: String): Call<RImgBreeds>

    @GET("/breed/{NB}/list")
    fun getSubBreeds(@Path("NB") breedName: String): Call<RSubBreeds>

    @GET("/breed/{NB1}/{NB2}/images")
    fun getImgSubBreeds(@Path("NB1") breedName1: String,@Path("NB2") breedName2: String): Call<RImgBreeds>

}