package test.job.serverconnect

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import test.job.testparants.DBreed
import test.job.testparants.DImage
import test.job.testparants.DSubBreed
import test.job.testparants.DogBreeds
import test.job.testparants.DogBreeds.Companion.Dbreeds
import test.job.testparants.DogBreeds.Companion.SQLDbreeds
import test.job.testparants.DogBreeds.Companion.clickImgNum
import test.job.testparants.DogBreeds.Companion.listOpen


class ListFragment : Fragment() {

    lateinit var d: Dialog
    lateinit var progerssProgressDialog: ProgressDialog
    var dataList = ArrayList<DBreed>()
    lateinit var recyclerView: RecyclerView
    lateinit var lBack : LinearLayout
    lateinit var txtHeader: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        lBack = view.findViewById(R.id.lBack)
        txtHeader = view.findViewById(R.id.txtHeader)

        d = Dialog(context!!)
        
        recyclerView.adapter = DataAdpter(dataList, context!!)
        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )

        listOpen = true
        lBack.visibility = View.INVISIBLE
        if(Dbreeds.size>0) {
            if(clickImgNum == -1) {
                txtHeader.text = "Breeds"
                getDataBreed()
            } else {
                lBack.visibility = View.VISIBLE
                txtHeader.text = Dbreeds.get(clickImgNum).getName().capitalize()
                getDataSubBreed()
                lBack.setOnClickListener {
                    if(clickImgNum!=-1) {
                        (context as Activity).finish()
                        (context as Activity).startActivity(
                            Intent(
                                context,
                                MainActivity::class.java
                            )
                        )
                        clickImgNum = -1
                    }
                }
            }
        } else {

            if(getConnection(context!!)) {
                progerssProgressDialog = ProgressDialog(context)
                progerssProgressDialog.setCancelable(false)
                progerssProgressDialog.show()
                progerssProgressDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                progerssProgressDialog.setContentView(R.layout.progerss_dialog)
                getData()
            } else {
                openDialog("","Connect to Internet")
            }

        }


        return view
    }

    fun getDataSubBreed() {

        dataList = Dbreeds
        recyclerView.adapter = DataAdpterSub(dataList.get(clickImgNum).getSubBreeds(), context!!)
        recyclerView.adapter?.notifyDataSetChanged()

    }

    fun getDataBreed() {

        dataList = Dbreeds
        recyclerView.adapter = DataAdpter(dataList, context!!)
        recyclerView.adapter?.notifyDataSetChanged()

    }

    fun getData() {

        val dbHandler = DBHelper(context)
        SQLDbreeds = dbHandler.getAllName()

        var dogBreeds:DogBreeds = DogBreeds()

        val call1: Call<RBreeds> = ApiClient.getClient.getBreeds()
        call1.enqueue(object : Callback<RBreeds> {

            override fun onResponse(call: Call<RBreeds>, response1: Response<RBreeds>) {

                for (name in response1.body()!!.breedsNames) {
                    val dName = DBreed()
                    dName.setName(name)
                    dogBreeds.addBreeds(dName)
                }
                var i = 0
                for (dName in dogBreeds.getBreeds()) {

                    val call2: Call<RSubBreeds> = ApiClient.getClient.getSubBreeds(dName.getName())
                    call2.enqueue(object : Callback<RSubBreeds> {

                        override fun onResponse(
                            call: Call<RSubBreeds>,
                            response2: Response<RSubBreeds>
                        ) {
                            i++
                            for (subName in response2!!.body()!!.namesSubBreeds!!) {
                                val sub = DSubBreed()
                                sub.setNameSub(subName)
                                dName.addSubBreeds(sub)
                            }

                            if (dogBreeds.getBreeds().size == i) {
                                getDataBreed()
                                progerssProgressDialog.dismiss()
                            }

                            val call3: Call<RImgBreeds> =
                                ApiClient.getClient.getImgBreeds(dName.getName())
                            call3.enqueue(object : Callback<RImgBreeds> {

                                override fun onResponse(
                                    call: Call<RImgBreeds>,
                                    response3: Response<RImgBreeds>
                                ) {

                                    for (url in response3!!.body()!!.urlImg) {
                                        val dImg = DImage()
                                        dImg.setUrl(url)
                                        for (db in SQLDbreeds)
                                            for (dbUrl in db.getUrls())
                                                if (dbUrl.getUrl() == url)
                                                    dImg.setLike(true)
                                        dName.addUrls(dImg)
                                    }

                                    for (subName in dName.getSubBreeds()) {

                                        val call4: Call<RImgBreeds> =
                                            ApiClient.getClient.getImgSubBreeds(
                                                dName.getName(),
                                                subName.getNameSub()
                                            )
                                        call4.enqueue(object : Callback<RImgBreeds> {

                                            override fun onResponse(
                                                call: Call<RImgBreeds>,
                                                response4: Response<RImgBreeds>
                                            ) {
                                                for (url in response4!!.body()!!.urlImg) {
                                                    val dImg = DImage()
                                                    dImg.setUrl(url)
                                                    for (db in SQLDbreeds)
                                                        for (dbUrl in db.getUrls())
                                                            if (dbUrl.getUrl() == url)
                                                                dImg.setLike(true)
                                                    subName.addUrls(dImg)

                                                }

                                            }

                                            override fun onFailure(
                                                call: Call<RImgBreeds>,
                                                t: Throwable
                                            ) {
                                                openDialog("Error","Connect to Internet")
                                                progerssProgressDialog.dismiss()
                                            }
                                        })
                                    }

                                }

                                override fun onFailure(call: Call<RImgBreeds>, t: Throwable) {
                                    openDialog("Error","Connect to Internet")
                                }

                            })

                        }

                        override fun onFailure(call: Call<RSubBreeds>, t: Throwable) {
                            openDialog("Error","Connect to Internet")
                        }

                    })

                }

            }

            override fun onFailure(call: Call<RBreeds>, t: Throwable) {
                progerssProgressDialog.dismiss()
                openDialog("Error","Connect to Internet")
            }

        })

    }

    fun openDialog(title:String = "Some server error", message: String = "Try connect later") {

        if(!d.isShowing) {
            d.requestWindowFeature(Window.FEATURE_NO_TITLE)
            d.setContentView(R.layout.dialogerror)
            d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            d.setCancelable(false)
            val txtOk = d.findViewById<TextView>(R.id.txtOk)
            val txtHeader = d.findViewById<TextView>(R.id.txtTitle)
            val txtMessage = d.findViewById<TextView>(R.id.txtMessage)
            txtHeader.text = title
            txtMessage.text = message
            txtOk.setOnClickListener {
                d.dismiss()
            }
            d.show()
        }
    }

    fun getConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifiInfo != null && wifiInfo.isConnected) {
            return true
        }
        wifiInfo = cm.activeNetworkInfo
        return wifiInfo != null && wifiInfo.isConnected
    }
}