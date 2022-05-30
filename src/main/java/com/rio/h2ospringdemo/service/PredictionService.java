package com.rio.h2ospringdemo.service;

import com.rio.h2ospringdemo.model.PositionPrediction;
import com.rio.h2ospringdemo.model.Player;
import com.rio.h2ospringdemo.model.PricePrediction;
import hex.genmodel.GenModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.exception.PredictException;
import hex.genmodel.easy.prediction.MultinomialModelPrediction;
import hex.genmodel.easy.prediction.RegressionModelPrediction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    private final EasyPredictModelWrapper positionPredictionModelWrapper;
    private final EasyPredictModelWrapper pricePredictionModelWrapper;

    public PredictionService(EasyPredictModelWrapper positionPredictionModelWrapper, EasyPredictModelWrapper pricePredictionModelWrapper) {
        this.positionPredictionModelWrapper = positionPredictionModelWrapper;
        this.pricePredictionModelWrapper = pricePredictionModelWrapper;
    }

    public PositionPrediction predictPosition(Player player) throws PredictException {
        RowData rowData = buildPredictPositionRowData(player);
        MultinomialModelPrediction multinomialModelPrediction = positionPredictionModelWrapper.predictMultinomial(rowData);

        GenModel model = this.positionPredictionModelWrapper.getModel();
        String[] domainValues = model.getDomainValues(model.getResponseIdx());

        List<PositionPrediction.PositionProbability> positionProbabilities = new ArrayList<>(domainValues.length);

        for (int i = 0; i < domainValues.length; i++) {
            Player.Position position = Player.Position.valueOf(domainValues[i]);
            Double probability = multinomialModelPrediction.classProbabilities[i];
            var positionProbability = new PositionPrediction.PositionProbability(position, probability);

            positionProbabilities.add(positionProbability);
        }

        Player.Position position = Player.Position.valueOf(multinomialModelPrediction.label);

        return new PositionPrediction(positionProbabilities, position);
    }

    public PricePrediction predictPrice(Player player) throws PredictException {
        RowData rowData = buildPredictPriceRowData(player);
        RegressionModelPrediction regressionModelPrediction = pricePredictionModelWrapper.predictRegression(rowData);

        return new PricePrediction(BigDecimal.valueOf(regressionModelPrediction.value).setScale(2, RoundingMode.HALF_UP));
    }

    private RowData buildPredictPositionRowData(Player player) {
        var rowData = new RowData();
        populateCommonData(rowData, player);
        rowData.put("value_euro", (double) player.valueEuro());

        return rowData;
    }

    private RowData buildPredictPriceRowData(Player player) {
        var rowData = new RowData();
        populateCommonData(rowData, player);
        rowData.put("club_position", player.clubPosition().name());

        return rowData;
    }

    private void populateCommonData(RowData rowData, Player player) {
        rowData.put("name", player.name());
        rowData.put("full_name", player.fullName());
        rowData.put("birth_date", (double) player.birthDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        rowData.put("age", (double) player.age());
        rowData.put("height_cm", (double) player.heightCm());
        rowData.put("weight_kgs", (double) player.weightKgs());
        rowData.put("nationality", player.nationality());
        rowData.put("overall_rating", (double) player.overallRating());
        rowData.put("potential", (double) player.potential());
        rowData.put("wage_euro", (double) player.wageEuro());
        rowData.put("preferred_foot", player.preferredFoot().getValue());
        rowData.put("international_reputation", (double) player.internationalReputation());
        rowData.put("weak_foot", (double) player.weakFoot());
        rowData.put("skill_moves", (double) player.skillMoves());
        rowData.put("work_rate", player.workRate());
        rowData.put("body_type", player.bodyType());
        rowData.put("release_clause_euro", (double) player.releaseClauseEuro());
        rowData.put("club_team", player.clubTeam());
        rowData.put("club_rating", (double) player.clubRating());
        rowData.put("club_jersey_number", (double) player.clubJerseyNumber());
        rowData.put("club_join_date", (double) player.clubJoinDate().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
        rowData.put("contract_end_year", (double) player.contractEndYear());
        rowData.put("national_team", player.nationalTeam());
        rowData.put("national_rating", (double) player.nationalRating());
        rowData.put("national_team_position", player.nationalTeamPosition().name());
        rowData.put("national_jersey_number", (double) player.nationalJerseyNumber());
        rowData.put("crossing", (double) player.crossing());
        rowData.put("finishing", (double) player.finishing());
        rowData.put("heading_accuracy", (double) player.headingAccuracy());
        rowData.put("short_passing", (double) player.shortPassing());
        rowData.put("volleys", (double) player.volleys());
        rowData.put("dribbling", (double) player.dribbling());
        rowData.put("curve", (double) player.curve());
        rowData.put("freekick_accuracy", (double) player.freekickAccuracy());
        rowData.put("long_passing", (double) player.longPassing());
        rowData.put("ball_control", (double) player.ballControl());
        rowData.put("acceleration", (double) player.acceleration());
        rowData.put("sprint_speed", (double) player.sprintSpeed());
        rowData.put("agility", (double) player.agility());
        rowData.put("reactions", (double) player.reactions());
        rowData.put("balance", (double) player.balance());
        rowData.put("shot_power", (double) player.shotPower());
        rowData.put("jumping", (double) player.jumping());
        rowData.put("stamina", (double) player.stamina());
        rowData.put("strength", (double) player.strength());
        rowData.put("long_shots", (double) player.longShots());
        rowData.put("aggression", (double) player.aggression());
        rowData.put("interceptions", (double) player.interceptions());
        rowData.put("positioning", (double) player.positioning());
        rowData.put("vision", (double) player.vision());
        rowData.put("penalties", (double) player.penalties());
        rowData.put("composure", (double) player.composure());
        rowData.put("marking", (double) player.marking());
        rowData.put("standing_tackle", (double) player.standingTackle());
        rowData.put("sliding_tackle", (double) player.slidingTackle());
        rowData.put("GK_diving", (double) player.GKDiving());
        rowData.put("GK_handling", (double) player.GKHandling());
        rowData.put("GK_kicking", (double) player.GKKicking());
        rowData.put("GK_positioning", (double) player.GKPositioning());
        rowData.put("GK_reflexes", (double) player.GKReflexes());
    }
}
