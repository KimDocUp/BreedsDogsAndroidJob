package test.job.serverconnect

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import test.job.testparants.DogBreeds
import test.job.testparants.DogBreeds.Companion.SQLDbreeds
import test.job.testparants.DogBreeds.Companion.listOpen

class FavouriteFragment : Fragment() {

    var dataList = ArrayList<String>()
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_favourite, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        listOpen = false
        val dbHandler = DBHelper(context)
        SQLDbreeds = dbHandler.getAllName()

        recyclerView.adapter = DataAdapterSQL(SQLDbreeds, context!!)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        return view
    }

}