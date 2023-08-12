package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.ModelDto;
import com.tel_ran.rent_company.dto.StatistGetModelsDto;
import com.tel_ran.rent_company.entity.Model;
import com.tel_ran.rent_company.exception.ModelExistsException;
import com.tel_ran.rent_company.repo.ModelRepo;
import com.tel_ran.rent_company.service.IModelService;
import com.tel_ran.rent_company.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelServiceImpl implements IModelService {
    @Autowired
    ModelRepo modelRepo;

    private void checkIfModelExist(String modelName) {
        if(modelRepo.existsByModelName(modelName)) {
            throw new ModelExistsException("Model already exists");
        }
    }

    @Transactional
    @Override
    public ModelDto addModel(ModelDto modelDto) {
        checkIfModelExist(modelDto.getModelName());
        Model modelToBeAdded = ModelMapper.dtoToEntity(modelDto);
        modelToBeAdded.setCars(new ArrayList<>());
        return ModelMapper.entityToDto(modelRepo.save(modelToBeAdded));
    }

    @Override
    public List<ModelDto> getMostPopularModels(StatistGetModelsDto dto) {
        //TODO: to implement after records
        return null;
    }

    @Override
    public List<ModelDto> getMostProfitableModels(StatistGetModelsDto dto) {
        //TODO: to implement after records
        return null;
    }
}
