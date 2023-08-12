package com.tel_ran.rent_company.controller;

import com.tel_ran.rent_company.dto.model.ModelDto;
import com.tel_ran.rent_company.service.IModelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<ModelDto> getMostPopularModels(@RequestParam(name = "from_date") String fromDate,
                                               @RequestParam(name = "to_date") String toDate,
                                               @RequestParam(name = "from_age") Integer fromAge,
                                               @RequestParam(name = "to_age") Integer toAge,
                                               @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return modelService.getMostPopularModels(fromDate, toDate, fromAge, toAge, from, size);
    }

    @ApiOperation(value = "Get most profitable models")
    @GetMapping(value = STATIST_MODEL_PATH + "/profitable")
    public List<ModelDto> getMostProfitableModels(@RequestParam(name = "from_date") String fromDate,
                                                  @RequestParam(name = "to_date") String toDate,
                                                  @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                  @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return modelService.getMostProfitableModels(fromDate, toDate, from, size);
    }
}
