package com.grin.licensingservice.service;

import com.grin.licensingservice.config.ServiceConfig;
import com.grin.licensingservice.model.License;
import com.grin.licensingservice.model.Organization;
import com.grin.licensingservice.repository.LicenseRepository;
import com.grin.licensingservice.service.client.OrganizationDiscoveryClient;
import com.grin.licensingservice.service.client.OrganizationFeignClient;
import com.grin.licensingservice.service.client.OrganizationRestTemplateClient;
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

    @Setter(onMethod = @__({@Autowired}))
    OrganizationFeignClient organizationFeignClient;
    @Setter(onMethod = @__({@Autowired}))
    OrganizationRestTemplateClient organizationRestClient;
    @Setter(onMethod = @__({@Autowired}))
    OrganizationDiscoveryClient organizationDiscoveryClient;

    public License getLicense(String licenseId,
                              String organizationId,
                              String clientType) {
        License license = licenseRepository
                .findLicenseByLicenseId(licenseId);

        if (license == null) {
            throw new IllegalArgumentException(
                    String.format(messageSource.getMessage(
                      "license.message.search.error", null, null),
                            licenseId, organizationId)
            );
        }

        Organization organization = retrieveOrganizationInfo(organizationId, clientType);
        if (null != organization) {
            license.setOrganization(organization);
        }

        return license.withComment(config.getProperty());
    }

    private Organization retrieveOrganizationInfo(String organizationId, String clientType) {
        Organization organization;

        switch (clientType) {
            case "feign":
                System.out.println("I am using the feign client");
                organization = organizationFeignClient.getOrganization(organizationId);
                break;
            case "rest":
                System.out.println("I am using the rest client");
                organization = organizationRestClient.getOrganization(organizationId);
                break;
            case "discovery":
                System.out.println("I am using the discovery client");
                organization = organizationDiscoveryClient.getOrganization(organizationId);
                break;
            default:
                organization = organizationRestClient.getOrganization(organizationId);
        }

        return organization;
    }

    public License createLicense(String organizationId, License license) {
        Organization organization = retrieveOrganizationInfo(organizationId, "");

        if (null == organization) {
            throw new IllegalArgumentException(
                    String.format(messageSource.getMessage(
                            "license.message.create.organizationId.error", null, null),
                            organizationId)
            );
        }

        license.setOrganizationId(organization.getId());
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
