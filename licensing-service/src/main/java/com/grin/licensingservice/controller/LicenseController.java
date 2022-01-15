package com.grin.licensingservice.controller;

import com.grin.licensingservice.model.License;
import com.grin.licensingservice.service.LicenseService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

    @Setter(onMethod = @__({@Autowired}))
    private LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {

        License license = licenseService.getLicense(licenseId, organizationId, "");
        license.add(
                linkTo(methodOn(LicenseController.class).getLicense(organizationId, license.getLicenseId())).withSelfRel(),
                linkTo(methodOn(LicenseController.class).createLicense(organizationId, license, null)).withRel("createLicense"),
                linkTo(methodOn(LicenseController.class).updateLicense(license)).withRel("updateLicense"),
                linkTo(methodOn(LicenseController.class).deleteLicense(license.getLicenseId())).withRel("deleteLicense")
        );

        return ResponseEntity.ok(
                license
        );
    }

    @RequestMapping(value = "/{licenseId}/{clientType}", method = RequestMethod.GET)
    public License getLicensesWithClient(@PathVariable("organizationId") String organizationId,
                                         @PathVariable("licenseId") String licenseId,
                                         @PathVariable("clientType") String clientType) {

        return licenseService.getLicense(licenseId, organizationId, clientType);
    }

    @PutMapping
    public ResponseEntity<License> updateLicense(
            @RequestBody License license) {
        return ResponseEntity.ok(
                licenseService.updateLicense(license)
        );
    }

    @PostMapping
    public ResponseEntity<License> createLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License license,
            @RequestHeader(value = "Accept-Language", required = false) Locale locale) {
        return ResponseEntity.ok(
                licenseService.createLicense(organizationId, license)
        );
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(
                licenseService.deleteLicense(licenseId)
        );
    }

    @GetMapping(value = "/")
    public List<License> getLicenses(@PathVariable("organizationId") String organizationId) {
        return licenseService.getLicensesByOrganization(organizationId);
    }
}
