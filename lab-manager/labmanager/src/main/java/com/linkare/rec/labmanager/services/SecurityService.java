package com.linkare.rec.labmanager.services;

import com.linkare.rec.labmanager.persistence.entities.LabManager;
import com.linkare.rec.labmanager.persistence.repos.LabManagerRepository;
import io.quarkus.security.identity.SecurityIdentity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Service
public class SecurityService {

    @Inject
    SecurityIdentity identity;

    @Inject
    LabManagerRepository labManagerRepository;

    public boolean hasUser(Long id) {

        LabManager labManager = labManagerRepository.findById(id);

        if (labManager == null) {
            return false;
        }

        String userName = identity.getPrincipal().getName();

        if (labManager.getEditUsers() == null) {
            return false;
        }

        List<String> users = Arrays.asList(labManager.getEditUsers().split(","));

        return users.contains(userName);
    }
}
