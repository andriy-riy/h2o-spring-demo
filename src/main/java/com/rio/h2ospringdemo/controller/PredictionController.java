package com.rio.h2ospringdemo.controller;

import com.rio.h2ospringdemo.model.SeasonWinPrediction;
import com.rio.h2ospringdemo.model.SeasonWinProbabilityRequest;
import com.rio.h2ospringdemo.service.PredictionService;
import hex.genmodel.easy.exception.PredictException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public SeasonWinPrediction predict(@RequestBody SeasonWinProbabilityRequest seasonWinProbabilityRequest) throws PredictException {
        return predictionService.predict(seasonWinProbabilityRequest);
    }
}
