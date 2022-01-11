package com.grin.organizationservice.controller;

import com.grin.organizationservice.model.Organization;
import com.grin.organizationservice.service.OrganizationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization")
public class OrganizationController {

    @Setter(onMethod = @__({@Autowired}))
    private OrganizationService organizationService;

    @GetMapping(value = "/{organizationId}")
    public ResponseEntity<Organization> getOrganization(
            @PathVariable("organizationId") String organizationId) {

        return ResponseEntity.ok(
                organizationService.findById(organizationId)
        );
    }

    @PutMapping
    public void updateOrganization(@RequestBody Organization organization) {
        organizationService.update(organization);
    }

    @PostMapping
    public ResponseEntity<Organization> saveOrganization(@RequestBody Organization organization) {
        return ResponseEntity.ok(
                organizationService.create(organization)
        );
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrganization(@RequestBody Organization organization) {
        organizationService.delete(organization);
    }
}
