package com.tel_ran.rent_company.util;

import com.tel_ran.rent_company.dto.ModelDto;
import com.tel_ran.rent_company.entity.Model;

public class ModelMapper {
    public static Model dtoToEntity(ModelDto modelDto) {
        Model model = new Model();
        model.setModelName(modelDto.getModelName());
        model.setCompany(modelDto.getCompany());
        model.setCountry(modelDto.getCountry());
        model.setGasTank(modelDto.getGasTank());
        model.setDayPrice(modelDto.getDayPrice());
        return model;
    }

    public static ModelDto entityToDto(Model model) {
        return ModelDto.builder()
                .modelId(model.getId())
                .company(model.getCompany())
                .country(model.getCountry())
                .modelName(model.getModelName())
                .gasTank(model.getGasTank())
                .dayPrice(model.getDayPrice())
                .build();
    }
}
