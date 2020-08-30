package test.job.serverconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import test.job.testparants.DogBreeds.Companion.clickImgNum
import test.job.testparants.DogBreeds.Companion.listOpen

class MainActivity : AppCompatActivity() {

    lateinit var fL: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        var fF: Fragment = ListFragment()
        if(listOpen) {
            fF = ListFragment()
            bottomNV.selectedItemId = R.id.menuList
        } else {
            fF = FavouriteFragment()
            bottomNV.selectedItemId = R.id.menuFav
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fF)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNV.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menuList -> {
                    fF = ListFragment()
                }
                R.id.menuFav -> {
                    fF = FavouriteFragment()
                }
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fF)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
            true
        }

    }

    override fun onBackPressed() {

        super.onBackPressed()

    }
}