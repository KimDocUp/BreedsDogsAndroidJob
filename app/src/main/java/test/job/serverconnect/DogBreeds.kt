package test.job.testparants

import test.job.serverconnect.SQLDogBreeds

open class DogBreeds{

    companion object{
        var Dbreeds:ArrayList<DBreed> = ArrayList()
        var SQLDbreeds:ArrayList<SQLDogBreeds> = ArrayList()
        var clickImgNum:Int = -1
        var clickImgSubNum:Int = -1
        var listOpen:Boolean = true
    }
    fun getBreeds() = Dbreeds
    fun getSQLDbreeds() = SQLDbreeds
    fun setBreeds(mBreeds:ArrayList<DBreed>){ Dbreeds = mBreeds }
    fun addBreeds(mBreeds:DBreed){ Dbreeds.add(mBreeds) }

}