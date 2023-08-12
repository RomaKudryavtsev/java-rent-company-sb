package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.model.ModelDto;
import com.tel_ran.rent_company.dto.model.StatistGetModelsDto;

import java.util.List;

public interface IModelService {
    ModelDto addModel(ModelDto modelDto);

    List<ModelDto> getMostPopularModels(StatistGetModelsDto dto);

    List<ModelDto> getMostProfitableModels(StatistGetModelsDto dto);
}
