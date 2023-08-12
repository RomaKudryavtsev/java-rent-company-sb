package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.ModelDto;
import com.tel_ran.rent_company.dto.StatistGetModelsDto;
import com.tel_ran.rent_company.service.IModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE)
@Api(tags = "Models")
public class ModelController {
    final static String MANAGER_MODEL_PATH = "/manager/model";
    final static String STATIST_MODEL_PATH = "/statist/model";
    @Autowired
    IModelService modelService;

    @ApiOperation(value = "Add new model")
    @PostMapping(value = MANAGER_MODEL_PATH)
    public ModelDto addModel(@Valid @RequestBody ModelDto modelDto) {
        return modelService.addModel(modelDto);
    }

    @ApiOperation(value = "Get most popular models")
    @GetMapping(value = STATIST_MODEL_PATH + "/popular")
    public List<ModelDto> getMostPopularModels(@Valid @RequestBody StatistGetModelsDto dto) {
        return modelService.getMostPopularModels(dto);
    }

    @ApiOperation(value = "Get most profitable models")
    @GetMapping(value = STATIST_MODEL_PATH + "/profitable")
    public List<ModelDto> getMostProfitableModels(@Valid @RequestBody StatistGetModelsDto dto) {
        return modelService.getMostProfitableModels(dto);
    }
}
