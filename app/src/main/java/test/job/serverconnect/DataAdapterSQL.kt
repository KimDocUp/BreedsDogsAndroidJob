package test.job.serverconnect

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import test.job.testparants.DBreed
import test.job.testparants.DogBreeds


class DataAdapterSQL(private var dataList: ArrayList<SQLDogBreeds>, private val context: Context) : RecyclerView.Adapter<DataAdapterSQL.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_home, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dataL= dataList[position]
        val countSub = dataL.getUrls().size
        holder.nameBreed.text = dataL.getName().capitalize()
        var tWord = "photo"
        if(countSub>1)
            tWord = "photos"
        if (countSub != 0)
            holder.countSubBreeds.text = "($countSub $tWord)"
        else
            holder.countSubBreeds.text = ""

        holder.cardView.setOnClickListener {

            DogBreeds.clickImgSubNum = position
            (context as Activity).finish()
            context.startActivity(Intent(context, ImageActivity::class.java))

        }

    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        lateinit var nameBreed: TextView
        lateinit var countSubBreeds: TextView
        lateinit var cardView: CardView
        init {
            nameBreed=itemLayoutView.findViewById(R.id.nameBreed)
            countSubBreeds=itemLayoutView.findViewById(R.id.countSubBreeds)
            cardView=itemLayoutView.findViewById(R.id.cardView)
        }

    }

}
