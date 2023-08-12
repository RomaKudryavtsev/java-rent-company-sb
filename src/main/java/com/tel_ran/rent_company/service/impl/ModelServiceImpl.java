package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.ModelDto;
import com.tel_ran.rent_company.dto.StatistGetModelsDto;
import com.tel_ran.rent_company.entity.Model;
import com.tel_ran.rent_company.exception.ModelExistsException;
import com.tel_ran.rent_company.repo.ModelRepo;
import com.tel_ran.rent_company.service.IModelService;
import com.tel_ran.rent_company.util.DateUtil;
import com.tel_ran.rent_company.util.ModelMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModelServiceImpl implements IModelService {
    @Value("${rent.date.format}")
    String format;
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
    @Autowired
    ModelRepo modelRepo;

    private void checkIfModelExist(String modelName) {
        if (modelRepo.existsByModelName(modelName)) {
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
        LocalDate[] dates = DateUtil.parseDates(dto.getFromDate(), dto.getToDate(), formatter);
        return modelRepo.findMostPopularModels(dates[0], dates[1], dto.getFromAge(), dto.getToAge()).stream()
                .map(ModelMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelDto> getMostProfitableModels(StatistGetModelsDto dto) {
        LocalDate[] dates = DateUtil.parseDates(dto.getFromDate(), dto.getToDate(), formatter);
        return modelRepo.findMostProfitableModels(dates[0], dates[1]).stream()
                .map(ModelMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
