package com.ozruit;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LeagueTableTest {

    @Test
    public void loadJsonAndTestLeagueTable() throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        Path path = Paths.get(getClass().getClassLoader().getResource("matches.json").toURI());

        Stream<String> lines = Files.lines(path);
        String jsonInput = lines.collect(Collectors.joining(" "));
        lines.close();

        List<Match> matches = mapper.readValue(jsonInput, new TypeReference<List<Match>>() {
        });

        LeagueTable leagueTable = new LeagueTable(matches);
        List<LeagueTableEntry> tableEntries = leagueTable.getTableEntries();
        tableEntries.forEach(System.out::println);

        for (LeagueTableEntry entry: tableEntries) {
            Assert.assertEquals(entry.getPlayed(), entry.getWon() + entry.getLost() + entry.getDrawn());
            Assert.assertEquals(entry.getPoints(), entry.getWon() * 3 +  entry.getDrawn());
            Assert.assertEquals(entry.getGoalDifference(), entry.getGoalsFor() - entry.getGoalsAgainst());
        }
    }
}