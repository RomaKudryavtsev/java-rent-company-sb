package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.ModelDto;
import com.tel_ran.rent_company.dto.StatistGetModelsDto;
import com.tel_ran.rent_company.service.IModelService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelServiceImpl implements IModelService {
    @Override
    public ModelDto addModel(ModelDto modelDto) {
        return null;
    }

    @Override
    public List<ModelDto> getMostPopularModels(StatistGetModelsDto dto) {
        return null;
    }

    @Override
    public List<ModelDto> getMostProfitableModels(StatistGetModelsDto dto) {
        return null;
    }
}
