package com.github.fionicholas.football.ui.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.helper.database
import com.github.fionicholas.football.data.model.FavoriteMatch
import com.github.fionicholas.football.data.model.Match
import com.github.fionicholas.football.data.model.Team
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.text.SimpleDateFormat

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var idMatch: String
    private lateinit var match: Match
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.title = "Detail Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        idMatch = intent?.getStringExtra("DETAIL_MATCH").toString()

        progressBar = loading
        swipeRefresh = swipe_layout

        favoriteState()

        presenter = DetailMatchPresenter(
            this)
        presenter.getDetailMatch(idMatch)

        mHandler = Handler()
        swipeRefresh.setOnRefreshListener {
            mRunnable = Runnable {
                presenter.getDetailMatch(idMatch)
                swipeRefresh.isRefreshing = false
            }
            mHandler.postDelayed(mRunnable, 500)
        }
    }

    override fun showDetailMatch(data: List<Match>) {
        match = Match(data[0].idEvent,
            data[0].nameLeague,
            data[0].homeTeam,data[0].awayTeam,data[0].homeScore,data[0].awayScore,data[0].dateEvent,
            data[0].time,data[0].eventName,data[0].teamBadge,data[0].sport,data[0].idTeam,
            data[0].idHomeTeam, data[0].homeShoot,data[0].awayShoot,data[0].homeFormation,data[0].awayFormation,
            data[0].homeGoalDetail, data[0].awayGoalDetail,data[0].idAwayTeam, data[0].idLeague
        )
        swipeRefresh.isRefreshing = false
        for (i in 0 until data.size) {

            presenter.getHomeBadge(data[i].idHomeTeam.toString().trim())
            presenter.getAwayBadge(data[i].idAwayTeam.toString().trim())

            tv_team_home.text = data[i].homeTeam.toString().trim()
            league_name.text = data[i].nameLeague.toString().trim()
            tv_team_away.text = data[i].awayTeam.toString().trim()

            if(data[i].homeScore.toString().trim().equals("null")){
                tv_score_home.text = "-"
                score_home.text = "-"
            }else{
                tv_score_home.text = data[i].homeScore.toString().trim()
                score_home.text = data[i].homeScore.toString().trim()
            }

            if(data[i].awayScore.toString().trim().equals("null")){
                tv_score_away.text = "-"
                score_away.text = "-"
            }else{
                tv_score_away.text = data[i].awayScore.toString().trim()
                score_away.text = data[i].awayScore.toString().trim()
            }

            //Detail
            if(data[i].homeShoot.toString().trim().equals("null")){
                shoot_home.text = "-"
            }else{
                shoot_home.text = data[i].homeShoot.toString().trim()
            }

            if(data[i].awayShoot.toString().trim().equals("null")){
                shoot_away.text = "-"
            }else{
                shoot_away.text = data[i].awayShoot.toString().trim()
            }

            if(data[i].homeFormation.toString().trim().equals("null")){
                formation_home.text = "-"
            }else{
                formation_home.text = data[i].homeFormation.toString()
            }

            if(data[i].awayFormation.toString().trim().equals("null")){
                formation_away.text = "-"
            }else{
                formation_away.text = data[i].awayFormation.toString()
            }

            name_match.text = data[i].eventName.toString().trim()

            val parser = SimpleDateFormat("yyyy-mm-dd")
            val formatter = SimpleDateFormat("dd-mm-yyyy")
            val date: String = formatter.format(parser.parse(data[i].dateEvent.toString()))
            date_match.text = date


            if(data[i].homeGoalDetail.toString().trim().equals("null")){
                detail_goal_home.text = "-"
            }else{
                detail_goal_home.text = data[i].homeGoalDetail.toString().trim()
            }
            if(data[i].awayGoalDetail.toString().trim().equals("null")){
                detail_goal_away.text = "-"
            }else{
                detail_goal_away.text = data[i].awayGoalDetail.toString().trim()
            }


        }
    }

    override fun showLoading() {
       progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showHomeBadge(data: List<Team>) {
        for (i in 0 until data.size) {
            Picasso.get().load(data[i].strTeamBadge).into(img_badge_home)
        }
    }

    override fun showAwayBadge(data: List<Team>) {
        for (i in 0 until data.size) {
            Picasso.get().load(data[i].strTeamBadge).into(img_badge_away)
        }
    }

    //FAVORITE

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
                    FavoriteMatch.TABLE_FAVORITE_MATCH,
                    FavoriteMatch.MATCH_ID to match.idEvent,
                    FavoriteMatch.NAME_LEAGUE to match.nameLeague,
                    FavoriteMatch.HOME_TEAM to match.homeTeam,
                    FavoriteMatch.AWAY_TEAM to match.awayTeam,
                    FavoriteMatch.HOME_SCORE to match.homeScore,
                    FavoriteMatch.AWAY_SCORE to match.awayScore
                )
            }
            Snackbar.make(swipeRefresh,"Added to favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(swipeRefresh, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoriteMatch.TABLE_FAVORITE_MATCH, "(MATCH_ID = {id})",
                    "id" to idMatch)
            }
            Snackbar.make(swipeRefresh,"Removed from favorite", Snackbar.LENGTH_SHORT).show()
        } catch (e: SQLiteConstraintException){
            Snackbar.make(swipeRefresh, e.localizedMessage, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_favorite)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE_MATCH)
                .whereArgs("(MATCH_ID = {id})",
                    "id" to idMatch)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
}
