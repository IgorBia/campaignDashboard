package com.campaign.demo.utility.mapper;

import com.campaign.demo.utility.dto.KeywordResponse;
import com.campaign.demo.utility.model.Keyword;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface KeywordMapper {
    KeywordResponse toResponse(Keyword keyword);

    List<KeywordResponse> toResponseList(List<Keyword> keywords);
}
