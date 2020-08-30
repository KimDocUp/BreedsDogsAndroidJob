package test.job.serverconnect


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import test.job.testparants.DBreed
import test.job.testparants.DSubBreed
import test.job.testparants.DogBreeds
import test.job.testparants.DogBreeds.Companion.clickImgNum
import test.job.testparants.DogBreeds.Companion.clickImgSubNum

class DataAdpterSub(private var dataList: ArrayList<DSubBreed>, private val context: Context) : RecyclerView.Adapter<DataAdpterSub.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataL= dataList[position]
        holder.countSubBreeds.visibility = View.INVISIBLE
        holder.nameBreed.text = dataL.getNameSub().capitalize()


        holder.cardView.setOnClickListener {
            clickImgSubNum = position
            (context as Activity).finish()
            context.startActivity(Intent(context, ImageActivity::class.java))
        }

    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        lateinit var nameBreed:TextView
        lateinit var countSubBreeds:TextView
        lateinit var cardView:CardView
        init {
            nameBreed=itemLayoutView.findViewById(R.id.nameBreed)
            countSubBreeds=itemLayoutView.findViewById(R.id.countSubBreeds)
            cardView=itemLayoutView.findViewById(R.id.cardView)
        }

    }


}
