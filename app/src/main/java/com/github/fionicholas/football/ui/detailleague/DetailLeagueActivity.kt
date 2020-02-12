package com.github.fionicholas.football.ui.detailleague

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.github.fionicholas.football.R
import com.github.fionicholas.football.ui.adapter.LeaguePagerAdapter
import com.github.fionicholas.football.data.model.DetailLeague
import com.github.fionicholas.football.data.model.League
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_league.*
import java.text.SimpleDateFormat

class DetailLeagueActivity : AppCompatActivity(), DetailLeagueView {


    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var nameLeague: TextView
    private lateinit var imageLeague: ImageView
    private lateinit var descLeague: TextView
    private lateinit var countryLeague: TextView
    private lateinit var eventLeague: TextView
    private lateinit var genderLeague: TextView
    private lateinit var progressBar: ProgressBar
    var idLeague: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)
        nameLeague = findViewById(R.id.name_league)
        descLeague = findViewById(R.id.description_league)
        imageLeague = findViewById(R.id.image_league)
        countryLeague = findViewById(R.id.tv_country)
        eventLeague = findViewById(R.id.tv_event)
        genderLeague = findViewById(R.id.tv_gender)
        progressBar = findViewById(R.id.progressbar_detailleague)

            val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.transparant)
        )

        val data = intent.extras
        val football = data.getParcelable<League>("DETAIL_LEAGUE")
        idLeague = football.id
        val nameLeague = football.name
        Log.d("TAG", football.id)

        supportActionBar?.title = nameLeague
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        presenter = DetailLeaguePresenter(this)
        presenter.getLeague(idLeague)

        //Tab Layout

        viewpager_main.adapter = LeaguePagerAdapter(supportFragmentManager)
        tabs_main.setupWithViewPager(viewpager_main)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.getItemId() === android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun showLeagueList(data: List<DetailLeague>) {
        for (i in 0 until data.size){
            val parser = SimpleDateFormat("yyyy-mm-dd")
            val formatter = SimpleDateFormat("dd-mm-yyyy")
            val date: String = formatter.format(parser.parse(data[i].firstEvent.toString()))
            nameLeague.text = data[i].name
            eventLeague.text = date
            countryLeague.text = data[i].country
            genderLeague.text = data[i].gender
            descLeague.text = data[i].description

            Picasso.get().load(data[i].image).into(imageLeague)

            Log.d("ksskskks",data[i].name)
        }

    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
