package com.github.fionicholas.football.data.model

data class FavoriteMatch(val id: Long?,val eventId: String?, val nameLeague: String?, val homeTeam: String?, val awayTeam: String?,
                    val homeScore: String?, val awayScore: String?) {

    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_PREVIOUS_MATCH"
        const val ID: String = "ID_"
        const val MATCH_ID: String = "MATCH_ID"
        const val NAME_LEAGUE: String = "NAME_LEAGUE"
        const val HOME_TEAM: String = "HOME_TEAM"
        const val AWAY_TEAM: String = "AWAY_TEAM"
        const val HOME_SCORE: String = "HOME_SCORE"
        const val AWAY_SCORE: String = "AWAY_SCORE"
    }
}