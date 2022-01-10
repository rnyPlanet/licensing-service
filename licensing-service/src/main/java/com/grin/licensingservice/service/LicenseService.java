package com.grin.licensingservice.service;

import com.grin.licensingservice.config.ServiceConfig;
import com.grin.licensingservice.model.License;
import com.grin.licensingservice.repository.LicenseRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Service
public class LicenseService {

    @Setter(onMethod = @__({@Autowired}))
    private MessageSource messageSource;
    @Setter(onMethod = @__({@Autowired}))
    private LicenseRepository licenseRepository;
    @Setter(onMethod = @__({@Autowired}))
    private ServiceConfig config;


    public License getLicense(String licenseId,
                              String organizationId) {
        License license = licenseRepository
                .findLicenseByOrganizationIdAndLicenseId(organizationId, licenseId);

        if (license == null) {
            throw new IllegalArgumentException(
                    String.format(messageSource.getMessage(
                      "license.message.search.error", null, null),
                            licenseId, organizationId)
            );
        }

        return license.withComment(config.getProperty());
    }

    public License createLicense(License license) {
        license.setLicenseId(UUID.randomUUID().toString());
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public License updateLicense(License license) {
        licenseRepository.save(license);

        return license.withComment(config.getProperty());
    }

    public String deleteLicense(String licenseId) {
        licenseRepository.delete(licenseRepository.findLicenseByLicenseId(licenseId));

        return String.format(
                messageSource.getMessage("license.delete.message", null, null),
                licenseId);
    }
}
