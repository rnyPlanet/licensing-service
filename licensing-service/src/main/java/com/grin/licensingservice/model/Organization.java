package com.grin.licensingservice.model;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Organization extends RepresentationModel<Organization> {

    private String id;
    private String name;
    private String contactName;
    private String contactEmail;
    private String contactPhone;

}