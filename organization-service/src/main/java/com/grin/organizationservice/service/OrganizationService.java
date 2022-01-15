package com.grin.organizationservice.service;

import com.grin.organizationservice.model.Organization;
import com.grin.organizationservice.repository.OrganizationRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrganizationService {

    @Setter(onMethod = @__({@Autowired}))
    private OrganizationRepository organizationRepository;

    public Organization findById(String organizationId) {
        Optional<Organization> opt = organizationRepository.findById(organizationId);
        return opt.orElse(null);
    }

    public List<Organization> findAll() {
        return organizationRepository.findAll();
    }

    public Organization create(Organization organization) {
        organization.setId(UUID.randomUUID().toString());
        organization = organizationRepository.save(organization);
        return organization;
    }

    public void update(Organization organization) {
        organizationRepository.save(organization);
    }

    public void delete(Organization organization) {
        organizationRepository.deleteById(organization.getId());
    }

}
