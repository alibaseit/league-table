package com.ozruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LeagueTable {

    private List<Match> matches;
    Map<String, LeagueTableEntry> leagueTableEntries = new HashMap<>();

    public LeagueTable(List<Match> matches) {
        this.matches = matches;
    }


    /**
     * Get the ordered list of league table entries for this league table.
     *
     * @return list of LeagueTableEntry
     */
    public List<LeagueTableEntry> getTableEntries() {
        matches.forEach(m -> {
            processHomeScores(m);
            processAwayScores(m);
        });

        return new ArrayList<>(leagueTableEntries.values());
    }

    private void processHomeScores(Match m) {
        String teamName = m.getHomeTeam();
        int won = m.getHomeScore() > m.getAwayScore() ? 1 : 0;
        int drawn = m.getHomeScore() == m.getAwayScore() ? 1 : 0;
        int lost = m.getHomeScore() < m.getAwayScore() ? 1 : 0;
        int goalsFor = m.getHomeScore();
        int goalsAgainst = m.getAwayScore();

        processScore(teamName, won, drawn, lost, goalsFor, goalsAgainst);
    }

    private void processAwayScores(Match m) {
        String teamName = m.getAwayTeam();
        int won = m.getAwayScore() > m.getHomeScore() ? 1 : 0;
        int drawn = m.getAwayScore() == m.getHomeScore() ? 1 : 0;
        int lost = m.getAwayScore() < m.getHomeScore() ? 1 : 0;
        int goalsFor = m.getAwayScore();
        int goalsAgainst = m.getHomeScore();

        processScore(teamName, won, drawn, lost, goalsFor, goalsAgainst);
    }

    private void processScore(String teamName, int won, int drawn, int lost, int goalsFor, int goalsAgainst) {
        int goalDifference = goalsFor - goalsAgainst;
        int points = 3 * won + drawn;

        LeagueTableEntry leagueTableEntry = leagueTableEntries.get(teamName);
        if (leagueTableEntry == null) {
            leagueTableEntry = new LeagueTableEntry(teamName, 1, won, drawn, lost, goalsFor, goalsAgainst, goalDifference, points);
        } else {
            leagueTableEntry.setPlayed(leagueTableEntry.getPlayed() + 1);
            leagueTableEntry.setWon(won + leagueTableEntry.getWon());
            leagueTableEntry.setDrawn(drawn + leagueTableEntry.getDrawn());
            leagueTableEntry.setLost(lost + leagueTableEntry.getLost());
            leagueTableEntry.setGoalsFor(goalsFor + leagueTableEntry.getGoalsFor());
            leagueTableEntry.setGoalsAgainst(goalsAgainst + leagueTableEntry.getGoalsAgainst());
            leagueTableEntry.setGoalDifference(goalDifference + leagueTableEntry.getGoalDifference());
            leagueTableEntry.setPoints(points + leagueTableEntry.getPoints());
        }
        leagueTableEntries.put(teamName, leagueTableEntry);
    }
}
