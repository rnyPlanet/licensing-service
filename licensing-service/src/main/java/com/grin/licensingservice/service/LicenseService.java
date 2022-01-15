package com.grin.licensingservice.service;

import com.grin.licensingservice.config.ServiceConfig;
import com.grin.licensingservice.model.License;
import com.grin.licensingservice.model.Organization;
import com.grin.licensingservice.repository.LicenseRepository;
import com.grin.licensingservice.service.client.OrganizationDiscoveryClient;
import com.grin.licensingservice.service.client.OrganizationFeignClient;
import com.grin.licensingservice.service.client.OrganizationRestTemplateClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Service
@Slf4j
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

    @SneakyThrows
    @CircuitBreaker(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    @RateLimiter(name = "licenseService", fallbackMethod = "buildFallbackLicenseList")
    @Retry(name = "retryLicenseService", fallbackMethod = "buildFallbackLicenseList")
    @Bulkhead(name = "bulkheadLicenseService", type= Bulkhead.Type.THREADPOOL, fallbackMethod = "buildFallbackLicenseList")
    public List<License> getLicensesByOrganization(String organizationId) {
        randomlyRunLong();
        return licenseRepository.findLicenseByOrganizationId(organizationId);
    }

    @SuppressWarnings("unused")
    private List<License> buildFallbackLicenseList(String organizationId, Throwable t){
        List<License> fallbackList = new ArrayList<>();
        License license = new License();
        license.setLicenseId("0000000-00-00000");
        license.setOrganizationId(organizationId);
        license.setProductName("Sorry no licensing information currently available");
        fallbackList.add(license);
        return fallbackList;
    }

    private void randomlyRunLong() throws TimeoutException {
        Random rand = new Random();
        int randomNum = rand.nextInt((3 - 1) + 1) + 1;
        if (randomNum == 3) sleep();
    }

    private void sleep() throws TimeoutException {
        try {
            System.out.println("Sleep");
            Thread.sleep(5000);
            throw new TimeoutException();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
