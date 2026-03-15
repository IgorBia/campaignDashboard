package com.campaign.demo.utility.dto;

import java.util.UUID;

public record TownResponse(
    UUID id,
    String name
) {
}