package com.github.fionicholas.football.ui.searchteam

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.github.fionicholas.football.R
import com.github.fionicholas.football.data.model.Team
import com.github.fionicholas.football.ui.adapter.TeamAdapter
import com.github.fionicholas.football.ui.detailteam.DetailTeamActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_team.*
import org.jetbrains.anko.toast

class SearchTeamActivity : AppCompatActivity(), SearchTeamView, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: SearchTeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var progressBar: ProgressBar
    private var leagues: MutableList<Team> = mutableListOf()
    private lateinit var idLeague: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)
        supportActionBar?.title = "Search Team"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        progressBar = loading
        recyclerView = rv_searchteam
        idLeague = intent?.getStringExtra("ID_LEAGUE").toString()

        adapter = TeamAdapter(leagues) {
            val intent = Intent(this, DetailTeamActivity::class.java)
            intent.putExtra("ID_TEAM", it.idTeam)
            startActivity(intent)
        }

        recyclerView.adapter = adapter
    }

    override fun showSearchTeam(data: List<Team>) {

        val dataSoccer = data.filter {
            it.strSport == "Soccer" && it.idLeague == idLeague
        }

        Log.d("mdkasmdkadkmad", idLeague)
        leagues.clear()
        leagues.addAll(dataSoccer)
        adapter.notifyDataSetChanged()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_search, menu);

        val searchView = menu?.findItem(R.id.search_menubar)?.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)
        return true
    }


    override fun onQueryTextSubmit(newText: String): Boolean {
        if (newText == "") {

            recyclerView.adapter = adapter
            presenter = SearchTeamPresenter(
                this
            )


            presenter.getSearchTeam("")


        } else {
            recyclerView.adapter = adapter
            presenter = SearchTeamPresenter(
                this
            )
            newText.trim { it <= ' ' }

            presenter.getSearchTeam(newText)

        }
        return true
    }

    override fun onResponseNull() {
        toast("The Team  not found!")
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onQueryTextChange(newText: String): Boolean {

        return true
    }


    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.getItemId() === android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}

