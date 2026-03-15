package com.campaign.demo.utility.mapper;

import com.campaign.demo.utility.dto.TownResponse;
import com.campaign.demo.utility.model.Town;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TownMapper {
    TownResponse toResponse(Town town);

    List<TownResponse> toResponseList(List<Town> towns);
}
