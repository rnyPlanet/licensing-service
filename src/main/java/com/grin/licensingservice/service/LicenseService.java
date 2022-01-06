package com.grin.licensingservice.service;

import com.grin.licensingservice.model.License;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Locale;
import java.util.Random;

@Service
public class LicenseService {

    @Setter(onMethod = @__({@Autowired}))
    private MessageSource messageSource;

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
                                String organizationId,
                                Locale locale) {
        String responseMessage = null;

        if (license != null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messageSource.getMessage("license.message.create", null, locale),
                    license);
        }

        return responseMessage;
    }

    public String updateLicense(License license,
                                String organizationId) {
        String responseMessage = null;
        if (!ObjectUtils.isEmpty(license)) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format(
                    messageSource.getMessage("license.update.message", null, null),
                    license);
        }

        return responseMessage;
    }

    public String deleteLicense(String licenseId,
                                String organizationId) {
        return String.format(
                messageSource.getMessage("license.delete.message", null, null),
                licenseId,
                organizationId);
    }
}
