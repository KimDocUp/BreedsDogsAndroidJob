package test.job.serverconnect

import test.job.testparants.DBreed
import test.job.testparants.DImage
import test.job.testparants.DogBreeds

class SQLDogBreeds {

    private var name:String = ""
    private var urls:ArrayList<DImage> = ArrayList()

    fun getName() = name
    fun getUrls() = urls

    fun setBreeds(mName:String) { name = mName }
    fun setUrls(mDImage:ArrayList<DImage>) { urls = mDImage }
    fun addUrls(mDImage:DImage) { urls.add(mDImage) }

}