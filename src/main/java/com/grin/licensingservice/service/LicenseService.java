package com.grin.licensingservice.service;

import com.grin.licensingservice.model.License;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Random;

@Service
public class LicenseService {
    public License getLicense(String licenseId,
                              String organizationId) {
        return License.builder()
                .id(new Random().nextInt(100))
                .licenseId(licenseId)
                .organizationId(organizationId)
                .description("Software product")
                .productName("Ostock")
                .licenseType("full")
                .build();
    }

    public String createLicense(License license,
                                String organizationId) {
        String responseMessage = null;

        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format("This is the post and the " +
                    "object is: %s", license);
        }

        return responseMessage;
    }

    public String updateLicense(License license,
                                String organizationId) {
        String responseMessage = null;
        if (!ObjectUtils.isEmpty(license)) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format("This is the put and the " +
                    "object is: %s", license);
        }

        return responseMessage;
    }

    public String deleteLicense(String licenseId,
                                String organizationId) {
        return String.format("Deleting license with id %s for " +
                "the organization %s", licenseId, organizationId);
    }
}
