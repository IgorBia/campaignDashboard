package com.campaign.demo.utility.service;

import com.campaign.demo.utility.dto.TownResponse;
import com.campaign.demo.utility.mapper.TownMapper;
import com.campaign.demo.utility.repository.TownRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownService {

    private final TownRepository townRepository;
    private final TownMapper townMapper;

    public TownService(TownRepository townRepository, TownMapper townMapper) {
        this.townRepository = townRepository;
        this.townMapper = townMapper;
    }

    public List<TownResponse> getAllTowns() {
        return townMapper.toResponseList(townRepository.findAll());
    }
}
