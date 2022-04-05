package com.rio.h2ospringdemo.model;

import java.util.List;

public record SeasonWinPrediction(
        List<PositionProbability> positionProbabilities,
        Byte decision
) {

    public record PositionProbability(
            Byte position,
            Double probability
    ) {
    }
}
