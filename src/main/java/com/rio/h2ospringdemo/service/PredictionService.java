package com.rio.h2ospringdemo.service;

import com.rio.h2ospringdemo.model.SeasonWinPrediction;
import com.rio.h2ospringdemo.model.SeasonWinProbabilityRequest;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    private final MojoModel mojoModel;

    public PredictionService(@Value("classpath:model.zip") Resource model) throws IOException {
        this.mojoModel = MojoModel.load(model.getFile().getAbsolutePath());
    }

    public SeasonWinPrediction predict(SeasonWinProbabilityRequest seasonWinProbabilityRequest) throws PredictException {
        var rowData = new RowData();
        rowData.put("Season", seasonWinProbabilityRequest.season());
        rowData.put("Team", seasonWinProbabilityRequest.team());
        rowData.put("Pld", (double) seasonWinProbabilityRequest.gamesPlayed());
        rowData.put("W", (double) seasonWinProbabilityRequest.wins());
        rowData.put("D", (double) seasonWinProbabilityRequest.draws());
        rowData.put("L", (double) seasonWinProbabilityRequest.loses());
        rowData.put("GF", (double) seasonWinProbabilityRequest.goalsScored());
        rowData.put("GA", (double) seasonWinProbabilityRequest.goalsConceded());
        rowData.put("GD", (double) seasonWinProbabilityRequest.goalsScored() - seasonWinProbabilityRequest.goalsConceded());
        rowData.put("Pts", (double) seasonWinProbabilityRequest.wins() * 3 + seasonWinProbabilityRequest.draws());

        var easyPredictModelWrapper = new EasyPredictModelWrapper(mojoModel);
        MultinomialModelPrediction multinomialModelPrediction = (MultinomialModelPrediction) easyPredictModelWrapper.predict(rowData);

        String[] domainValues = mojoModel.getDomainValues(mojoModel.getResponseName());
        List<SeasonWinPrediction.PositionProbability> positionProbabilities = new ArrayList<>(domainValues.length);

        for (int i = 0; i < domainValues.length; i++) {
            Byte position = Byte.parseByte(domainValues[i]);
            Double probability = multinomialModelPrediction.classProbabilities[i];
            var positionProbability = new SeasonWinPrediction.PositionProbability(position, probability);

            positionProbabilities.add(positionProbability);
        }

        Byte position = Byte.parseByte(multinomialModelPrediction.label);

        return new SeasonWinPrediction(positionProbabilities, position);
    }
}
