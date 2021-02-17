package com.linkare.rec.labmanager.rest;

import com.linkare.rec.labmanager.persistence.entities.LabManager;
import com.linkare.rec.labmanager.persistence.repos.LabManagerRepository;
import io.quarkus.hibernate.orm.rest.data.panache.PanacheRepositoryResource;

public interface LabManagerResource extends PanacheRepositoryResource<LabManagerRepository, LabManager, Long> {

}
