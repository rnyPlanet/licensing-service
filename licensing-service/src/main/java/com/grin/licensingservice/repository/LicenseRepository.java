package com.grin.licensingservice.repository;

import com.grin.licensingservice.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, String> {
    List<License> findLicenseByOrganizationId(String organizationId);

    License findLicenseByOrganizationIdAndLicenseId(String organizationId, String licenseId);

    License findLicenseByLicenseId(String licenseId);
}
