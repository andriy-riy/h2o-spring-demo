package com.rio.h2ospringdemo.model;

import java.util.List;

public record PositionPrediction(
        List<PositionProbability> positionProbabilities,
        Player.Position decision
) {

    public record PositionProbability(
            Player.Position position,
            Double probability
    ) {
    }
}
