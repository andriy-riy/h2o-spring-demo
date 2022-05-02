package com.rio.h2ospringdemo.controller;

import com.rio.h2ospringdemo.model.PositionPrediction;
import com.rio.h2ospringdemo.model.Player;
import com.rio.h2ospringdemo.model.PricePrediction;
import com.rio.h2ospringdemo.service.PredictionService;
import hex.genmodel.easy.exception.PredictException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fifa/players")
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict-position")
    public PositionPrediction predictPosition(@RequestBody Player player) throws PredictException {
        return predictionService.predictPosition(player);
    }

    @PostMapping("/predict-price")
    public PricePrediction predictPrice(@RequestBody Player player) throws PredictException {
        return predictionService.predictPrice(player);
    }
}
