package com.campaign.demo.emerald_user.mapper;

import com.campaign.demo.emerald_user.dto.AccountResponse;
import com.campaign.demo.emerald_user.model.EmeraldAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmeraldAccountMapper {
    AccountResponse toResponse(EmeraldAccount account);
}
