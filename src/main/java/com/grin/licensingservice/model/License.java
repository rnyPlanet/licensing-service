package com.grin.licensingservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class License {
    private int id;
    private String licenseId;
    private String description;
    private String organizationId;
    private String productName;
    private String licenseType;
}
