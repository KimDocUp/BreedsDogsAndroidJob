package test.job.testparants

class DBreed {

    private var name:String = ""
    private var subBreeds: ArrayList<DSubBreed> = ArrayList()
    private var urls:ArrayList<DImage> = ArrayList()

    fun getName() = name
    fun getSubBreeds() = subBreeds
    fun getUrls() = urls

    fun setName(mName:String){ name = mName }
    fun setSubBreeds(mSubs: ArrayList<DSubBreed>){ subBreeds = mSubs }
    fun setUrls(mUrls:ArrayList<DImage>){ urls = mUrls }

    fun addSubBreeds(mSubs: DSubBreed){ subBreeds.add(mSubs) }
    fun addUrls(mUrls:DImage){ urls.add(mUrls) }

}