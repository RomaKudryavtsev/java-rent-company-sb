package com.tel_ran.rent_company.service;

import com.tel_ran.rent_company.dto.model.ModelDto;

import java.util.List;

public interface IModelService {
    ModelDto addModel(ModelDto modelDto);

    List<ModelDto> getMostPopularModels(String fromDate, String toDate, Integer fromAge, Integer toAge, Integer from, Integer size);

    List<ModelDto> getMostProfitableModels(String fromDate, String toDate, Integer from, Integer size);
}
