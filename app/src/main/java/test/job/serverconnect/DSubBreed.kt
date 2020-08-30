package test.job.testparants

class DSubBreed {

    private var nameSub:String = ""
    private var urls:ArrayList<DImage> = ArrayList()

    fun getNameSub() = nameSub
    fun getUrls() = urls

    fun setNameSub(mNameSub:String) {
        nameSub = mNameSub
    }
    fun setUrls(mUrls:ArrayList<DImage>) {
        urls = mUrls
    }
    fun addUrls(mUrls:DImage) {
        urls.add(mUrls)
    }


}