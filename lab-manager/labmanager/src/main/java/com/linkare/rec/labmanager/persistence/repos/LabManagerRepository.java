package com.linkare.rec.labmanager.persistence.repos;

import com.linkare.rec.labmanager.persistence.entities.LabManager;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LabManagerRepository implements PanacheRepository<LabManager> {
}
