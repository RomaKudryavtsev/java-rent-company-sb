package com.tel_ran.rent_company.service.impl;

import com.tel_ran.rent_company.dto.model.ModelDto;
import com.tel_ran.rent_company.entity.Model;
import com.tel_ran.rent_company.exception.model.ModelExistsException;
import com.tel_ran.rent_company.repo.ModelRepo;
import com.tel_ran.rent_company.service.IModelService;
import com.tel_ran.rent_company.util.DateUtil;
import com.tel_ran.rent_company.util.ModelMapper;
import com.tel_ran.rent_company.util.PageUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ModelServiceImpl implements IModelService {
    DateTimeFormatter formatter;
    ModelRepo modelRepo;

    @Transactional
    @Override
    public ModelDto addModel(ModelDto modelDto) {
        checkIfModelExist(modelDto.getModelName());
        Model modelToBeAdded = ModelMapper.dtoToEntity(modelDto);
        modelToBeAdded.setCars(new ArrayList<>());
        return ModelMapper.entityToDto(modelRepo.save(modelToBeAdded));
    }

    @Override
    public List<ModelDto> getMostPopularModels(String fromDate, String toDate, Integer fromAge, Integer toAge, Integer from, Integer size) {
        LocalDate[] dates = DateUtil.parseDates(fromDate, toDate, formatter);
        int now = LocalDate.now().getYear();
        int fromBirthYear = now - fromAge;
        int toBirthYear = now - toAge;
        Pageable pageRequest = PageUtil.makePageRequest(from, size);
        return modelRepo.findMostPopularModels(dates[0], dates[1], fromBirthYear, toBirthYear, pageRequest).stream()
                .map(ModelMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ModelDto> getMostProfitableModels(String fromDate, String toDate, Integer from, Integer size) {
        LocalDate[] dates = DateUtil.parseDates(fromDate, toDate, formatter);
        Pageable pageRequest = PageUtil.makePageRequest(from, size);
        return modelRepo.findMostProfitableModels(dates[0], dates[1], pageRequest).stream()
                .map(ModelMapper::entityToDto)
                .collect(Collectors.toList());
    }

    private void checkIfModelExist(String modelName) {
        if (modelRepo.existsByModelName(modelName)) {
            throw new ModelExistsException("Model already exists");
        }
    }
}
