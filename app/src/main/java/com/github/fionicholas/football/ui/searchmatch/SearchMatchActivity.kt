package com.github.fionicholas.football.ui.searchmatch

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.github.fionicholas.football.R
import com.github.fionicholas.football.ui.adapter.MatchAdapter
import com.github.fionicholas.football.data.model.Match
import com.github.fionicholas.football.ui.detailmatch.DetailMatchActivity
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.toast

class SearchMatchActivity : AppCompatActivity(), SearchMatchView,SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var adapterSearch: MatchAdapter
    private var leagues: MutableList<Match> = mutableListOf()
    private lateinit var idLeague: String
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        supportActionBar?.title = "Search Match"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        recyclerView = rv_searchmatch
        progressBar = loading

        idLeague = intent?.getStringExtra("ID_LEAGUE").toString()

        Log.d("mdkasmdkadkmad",idLeague)
        adapterSearch = MatchAdapter(leagues) {
            val intent = Intent(this, DetailMatchActivity::class.java)
            intent.putExtra("DETAIL_MATCH", it.idEvent)
            startActivity(intent)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterSearch
        }

    }


    override fun showSearchMatch(data: List<Match>) {
        idLeague = intent?.getStringExtra("ID_LEAGUE").toString()
        val dataSoccer = data.filter {
            it.sport == "Soccer" && it.idLeague == idLeague
        }

        Log.d("mdkasmdkadkmad123",idLeague)
        leagues.clear()
        leagues.addAll(dataSoccer)
        adapterSearch.notifyDataSetChanged()

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

                recyclerView.adapter = adapterSearch

            presenter = SearchMatchPresenter(
                this
            )

            presenter.getSearchMatch("")


        } else {
            recyclerView.adapter = adapterSearch

            presenter = SearchMatchPresenter(
                this
            )
            newText.trim { it <= ' ' }

            presenter.getSearchMatch(newText)

        }
        return true
    }

    override fun onResponseNull() {
        toast("The Match not found!")
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
