package com.grin.licensingservice.controller;

import com.grin.licensingservice.model.License;
import com.grin.licensingservice.service.LicenseService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

    @Setter(onMethod = @__({@Autowired}))
    private LicenseService licenseService;

    @GetMapping(value = "/{licenseId}")
    public ResponseEntity<License> getLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(
                licenseService.getLicense(licenseId, organizationId)
        );
    }

    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License license) {
        return ResponseEntity.ok(
                licenseService.updateLicense(license, organizationId)
        );
    }

    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License license) {
        return ResponseEntity.ok(
                licenseService.createLicense(license, organizationId)
        );
    }

    @DeleteMapping(value = "/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(
                licenseService.deleteLicense(licenseId, organizationId)
        );
    }
}
