package test.job.serverconnect

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager

import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.ImageViewCompat
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.*
import test.job.testparants.DogBreeds.Companion.Dbreeds
import test.job.testparants.DogBreeds.Companion.SQLDbreeds
import test.job.testparants.DogBreeds.Companion.clickImgNum
import test.job.testparants.DogBreeds.Companion.clickImgSubNum
import test.job.testparants.DogBreeds.Companion.listOpen


class ImageActivity : AppCompatActivity() {

    var numImg = 0
    var dataListDelete : ArrayList<String> = ArrayList()

    var width = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        var display = windowManager.defaultDisplay
        width = display.getWidth();

        if(listOpen) {
            var n = Dbreeds.get(clickImgNum).getName().capitalize()
            if (clickImgSubNum == -1) {
                txtHeader.text = n
            } else {
                var nS = Dbreeds.get(clickImgNum).getSubBreeds()[clickImgSubNum].getNameSub()
                    .capitalize()
                txtHeader.text = "${n} ${nS}"
            }
        } else {
            txtHeader.text = SQLDbreeds[clickImgSubNum].getName().capitalize()
        }

        imgMain.setOnTouchListener { v, event ->
            val action = event.action
            when(action){
                MotionEvent.ACTION_UP -> {
                    if(event.x.toInt()<width/2){
                        numImg--
                    } else {
                        numImg++
                    }
                    openImage()
                }
                else ->{ }
            }
            true
        }
        imgLike.setOnClickListener {
            likeChange()
        }
        lBack.setOnClickListener {
            onBackPressed()
        }

        imgShare.setOnClickListener {

            var sBody = ""
            if(listOpen){
                if (clickImgSubNum == -1) {
                    sBody = Dbreeds.get(clickImgNum).getUrls().get(numImg).getUrl()
                } else {
                    sBody = Dbreeds.get(clickImgNum)
                        .getSubBreeds()[clickImgSubNum].getUrls()[numImg].getUrl()
                }
            } else {
                sBody = SQLDbreeds.get(clickImgSubNum).getUrls()[numImg].getUrl()
            }

            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.setType("text/plain")
            var sSubject = "Dog Image"

            sharingIntent.putExtra(Intent.EXTRA_TEXT, sBody)
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, sSubject)
            startActivity(Intent.createChooser(sharingIntent, "Share Image"))
        }

        openImage()

    }
    fun likeCheck(){
        if(listOpen) {
            if (clickImgSubNum == -1) {
                if (Dbreeds.get(clickImgNum).getUrls().get(numImg).getLike())
                    ImageViewCompat.setImageTintList(
                        imgLike,
                        ColorStateList.valueOf(Color.parseColor("#FF0000"))
                    )
                else
                    ImageViewCompat.setImageTintList(
                        imgLike,
                        ColorStateList.valueOf(Color.parseColor("#aaaaaa"))
                    )
            } else {
                if (Dbreeds.get(clickImgNum).getSubBreeds()[clickImgSubNum].getUrls()[numImg].getLike())
                    ImageViewCompat.setImageTintList(
                        imgLike, ColorStateList.valueOf(
                            Color.parseColor(
                                "#FF0000"
                            )
                        )
                    )
                else
                    ImageViewCompat.setImageTintList(
                        imgLike, ColorStateList.valueOf(
                            Color.parseColor(
                                "#aaaaaa"
                            )
                        )
                    )
            }
        } else {
            if (SQLDbreeds[clickImgSubNum].getUrls()[numImg].getLike()) {
                ImageViewCompat.setImageTintList(
                    imgLike,
                    ColorStateList.valueOf(Color.parseColor("#FF0000"))
                )
            } else {
                ImageViewCompat.setImageTintList(
                    imgLike,
                    ColorStateList.valueOf(Color.parseColor("#aaaaaa"))
                )
            }

        }
    }
    fun likeChange(){
        if(listOpen) {
            if (clickImgSubNum == -1) {
                if (Dbreeds.get(clickImgNum).getUrls().get(numImg).getLike()) {
                    Dbreeds.get(clickImgNum).getUrls().get(numImg).setLike(false)
                    val dbHandler = DBHelper(this)
                    dbHandler.delLike(Dbreeds.get(clickImgNum).getUrls().get(numImg).getUrl())

                } else {
                    Dbreeds.get(clickImgNum).getUrls().get(numImg).setLike(true)
                    val dbHandler = DBHelper(this)
                    dbHandler.addLike(
                        Dbreeds.get(clickImgNum).getName(),
                        Dbreeds.get(clickImgNum).getUrls().get(numImg).getUrl()
                    )
                }
            } else {
                if (Dbreeds.get(clickImgNum)
                        .getSubBreeds()[clickImgSubNum].getUrls()[numImg].getLike()
                ) {
                    Dbreeds.get(clickImgNum)
                        .getSubBreeds()[clickImgSubNum].getUrls()[numImg].setLike(false)

                    val dbHandler = DBHelper(this)
                    dbHandler.delLike(
                        Dbreeds.get(clickImgNum)
                            .getSubBreeds()[clickImgSubNum].getUrls()[numImg].getUrl()
                    )
                } else {
                    Dbreeds.get(clickImgNum)
                        .getSubBreeds()[clickImgSubNum].getUrls()[numImg].setLike(true)

                    val dbHandler = DBHelper(this)
                    dbHandler.addLike(
                        Dbreeds.get(clickImgNum).getName().capitalize() + " " + Dbreeds.get(
                            clickImgNum
                        ).getSubBreeds()[clickImgSubNum].getNameSub().capitalize(),
                        Dbreeds.get(clickImgNum)
                            .getSubBreeds()[clickImgSubNum].getUrls()[numImg].getUrl()
                    )
                }
            }
        } else {
            if(SQLDbreeds[clickImgSubNum].getUrls()[numImg].getLike()) {
                SQLDbreeds[clickImgSubNum].getUrls()[numImg].setLike(false)
                if(!dataListDelete.contains(SQLDbreeds[clickImgSubNum].getUrls()[numImg].getUrl())) {
                    dataListDelete.add(SQLDbreeds[clickImgSubNum].getUrls()[numImg].getUrl())
                }
            } else {
                SQLDbreeds[clickImgSubNum].getUrls()[numImg].setLike(true)
                if(dataListDelete.contains(SQLDbreeds[clickImgSubNum].getUrls()[numImg].getUrl())) {
                    dataListDelete.remove(SQLDbreeds[clickImgSubNum].getUrls()[numImg].getUrl())
                }
            }
        }
        likeCheck()
    }
    fun openImage(){
        if (listOpen) {
            if (clickImgSubNum == -1) {
                if (numImg >= Dbreeds.get(clickImgNum).getUrls().size) numImg = 0
                if (numImg < 0) numImg = Dbreeds.get(clickImgNum).getUrls().size-1
                Picasso.get().load(Dbreeds.get(clickImgNum).getUrls().get(numImg).getUrl()).into(
                    imgMain
                )
            } else {
                if (numImg >= Dbreeds.get(clickImgNum).getSubBreeds()[clickImgSubNum].getUrls().size) numImg = 0
                if (numImg < 0) numImg = Dbreeds.get(clickImgNum).getSubBreeds()[clickImgSubNum].getUrls().size-1
                Picasso.get().load(
                    Dbreeds.get(clickImgNum)
                        .getSubBreeds()[clickImgSubNum].getUrls()[numImg].getUrl()
                ).into(imgMain)
            }
        } else {
            if (numImg >= SQLDbreeds.get(clickImgSubNum).getUrls().size) numImg = 0
            if (numImg < 0) numImg = SQLDbreeds.get(clickImgSubNum).getUrls().size-1
            Picasso.get().load(SQLDbreeds.get(clickImgSubNum).getUrls()[numImg].getUrl()).into(
                imgMain
            )
        }
        likeCheck()
    }

    override fun onBackPressed() {
        if(clickImgSubNum==-1) {
            clickImgNum = -1
        } else {
            clickImgSubNum = -1
        }
        if(!listOpen) {
            if(!dataListDelete.isEmpty()){
                for(url in dataListDelete) {
                    val dbHandler = DBHelper(this)
                    dbHandler.delLike(url)
                }
            }
        }
        startActivity(Intent(this, MainActivity::class.java))
        super.onBackPressed()
    }
}