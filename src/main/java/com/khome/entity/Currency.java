package com.khome.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Currency {
    // ISO 4217
    USD(840), EUR(978), RUB(643), UAH(980);

    private final int id;
}
