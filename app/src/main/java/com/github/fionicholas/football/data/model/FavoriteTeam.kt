package com.github.fionicholas.football.data.model

data class FavoriteTeam(
    val id: Long?,
    val teamId: String?,
    val nameTeam: String?,
    val descTeam: String?,
    val yearTeam: String?,
    val sportTeam: String?,
    val countryTeam: String?,
    val badgeTeam: String?,
    val stadiumTeam: String?,
    val leagueTeam: String?
) {

    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val NAME_TEAM: String = "NAME_TEAM"
        const val DESC_TEAM: String = "DESC_TEAM"
        const val YEAR_TEAM: String = "YEAR_TEAM"
        const val SPORT_TEAM: String = "SPORT_TEAM"
        const val COUNTRY_TEAM: String = "COUNTRY_TEAM"
        const val BADGE_TEAM: String = "BADGE_TEAM"
        const val STADIUM_TEAM: String = "STADIUM_TEAM"
        const val LEAGUE_TEAM: String = "LEAGUE_TEAM"
    }
}