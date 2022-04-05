package com.rio.h2ospringdemo.model;

public record SeasonWinProbabilityRequest(
        String season,
        String team,
        Byte gamesPlayed,
        Byte wins,
        Byte draws,
        Byte loses,
        Short goalsScored,
        Short goalsConceded
) {
}
