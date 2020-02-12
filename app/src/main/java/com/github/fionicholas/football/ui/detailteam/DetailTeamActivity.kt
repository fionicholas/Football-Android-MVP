package com.github.fionicholas.football.ui.detailteam

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.helper.database
import com.github.fionicholas.football.data.model.FavoriteTeam
import com.github.fionicholas.football.data.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.android.synthetic.main.activity_detail_match.loading
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {

    private lateinit var presenter: DetailTeamPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var idTeam: String
    private lateinit var team: Team

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)

        collapsingToolbarLayout.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        collapsingToolbarLayout.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.transparant)
        )

        idTeam = intent?.getStringExtra("DETAIL_TEAM").toString()
        val nameTeam = intent?.getStringExtra("NAME_TEAM").toString()

        supportActionBar?.title = nameTeam
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        progressBar = loading

        favoriteState()

        presenter = DetailTeamPresenter(
            this)
        presenter.getDetailTeam(idTeam)

    }

    override fun showDetailTeam(data: List<Team>) {
        team = Team(
            data[0].idTeam,
            data[0].strTeam,
            data[0].intFormedYear,
            data[0].strSport, data[0].strDescriptionEN,
            data[0].strCountry, data[0].strTeamBadge, data[0].strStadium, data[0].strLeague,
            data[0].idLeague
        )

        for (i in 0 until data.size) {

            txt_name_team.text = data[i].strTeam.toString().trim()
            txt_league_name.text = data[i].strLeague.toString().trim()
            txt_country_team.text = data[i].strCountry.toString().trim()
            txt_sport_team.text = data[i].strSport.toString().trim()
            txt_stadium.text = data[i].strStadium.toString().trim()
            txt_year_team.text = data[i].intFormedYear.toString().trim()
            txt_deskripsi_team.text = data[i].strDescriptionEN.toString().trim()

            Picasso.get().load(team.strTeamBadge).into(img_item_photo)
        }
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    // Favorite
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {

            android.R.id.home -> {
                finish()
                return true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()
                true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to team.idTeam,
                    FavoriteTeam.NAME_TEAM to team.strTeam,
                    FavoriteTeam.DESC_TEAM to team.strDescriptionEN,
                    FavoriteTeam.YEAR_TEAM to team.intFormedYear,
                    FavoriteTeam.SPORT_TEAM to team.strSport,
                    FavoriteTeam.COUNTRY_TEAM to team.strCountry,
                    FavoriteTeam.BADGE_TEAM to team.strTeamBadge,
                    FavoriteTeam.STADIUM_TEAM to team.strStadium,
                    FavoriteTeam.LEAGUE_TEAM to team.strLeague
                )
            }
            Toast.makeText(this, "Added to Favorite Team", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to idTeam)
            }
            Toast.makeText(this, "Removed from Favorite Team", Toast.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_fill)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to idTeam)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    //END FAVORITE
}
