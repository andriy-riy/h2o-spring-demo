package com.rio.h2ospringdemo.config;

import hex.genmodel.GenModel;
import hex.genmodel.MojoModel;
import hex.genmodel.easy.EasyPredictModelWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Configuration
public class ModelConfig {

    // POJO model: https://github.com/h2oai/h2o-3/blob/master/h2o-docs/src/product/pojo-quickstart.rst
    // MOJO model: https://github.com/h2oai/h2o-3/blob/master/h2o-docs/src/product/mojo-quickstart.rst

    @Bean
    public EasyPredictModelWrapper positionPredictionModelWrapper(@Value("classpath:XGBoost_1_AutoML_2_20220518_143427.zip") Resource positionPredictionMojoModel) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        GenModel model = MojoModel.load(positionPredictionMojoModel.getFile().getAbsolutePath());
        //GenModel model = (GenModel) Class.forName("XGBoost_1_AutoML_2_20220518_143427").getDeclaredConstructor().newInstance();

        return new EasyPredictModelWrapper(model);
    }

    @Bean
    public EasyPredictModelWrapper pricePredictionModelWrapper(@Value("classpath:GBM_2_AutoML_3_20220518_144001.zip") Resource pricePredictionMojoModel) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        GenModel model = MojoModel.load(pricePredictionMojoModel.getFile().getAbsolutePath());
        //GenModel model = (GenModel) Class.forName("GBM_2_AutoML_3_20220518_144001").getDeclaredConstructor().newInstance();

        return new EasyPredictModelWrapper(model);
    }
}
