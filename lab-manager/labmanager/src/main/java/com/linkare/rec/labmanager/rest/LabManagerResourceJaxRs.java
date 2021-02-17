package com.linkare.rec.labmanager.rest;

import com.linkare.rec.labmanager.command.LabManagerDto;
import com.linkare.rec.labmanager.mapper.LabManagerMapper;
import com.linkare.rec.labmanager.persistence.entities.LabManager;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/api/labmanager")
@ApplicationScoped
public class LabManagerResourceJaxRs {

    @Inject
    LabManagerResource labManagerResource;

    @Inject
    LabManagerMapper labManagerMapper;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public LabManagerDto getLabManager(@PathParam Long id) {

        LabManager labManager = labManagerResource.get(id);

        if (labManager == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND.getStatusCode());
        }

        return labManagerMapper.toLabManagerDto(labManager);
    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("hasRole('admin'")
    public Response addLabManager(LabManagerDto labManagerDto) {

        LabManager labManager = labManagerMapper.toLabManager(labManagerDto);

        labManagerResource.add(labManager);

        return Response.status(Response.Status.CREATED).entity(labManager).build();
    }

    @Transactional
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @PreAuthorize("@securityService.hasUser(#id) or hasRole('admin')")
    public Response updateLabManager(@PathParam Long id, LabManagerDto labManagerDto) {

        if (labManagerResource.get(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LabManager labManager = labManagerResource.update(id, labManagerMapper.toLabManager(labManagerDto));

        return Response.status(Response.Status.CREATED).entity(labManager).build();
    }

    @Transactional
    @DELETE
    @Path("/{id}")
    @PreAuthorize("@securityService.hasUser(#id) or hasRole('admin')")
    public Response deleteLabManager(@PathParam("id") Long id) {

        boolean isDeleted = labManagerResource.delete(id);

        return isDeleted ? Response.status(Response.Status.NO_CONTENT).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll(@QueryParam("page") @DefaultValue("0") int pageIndex,
                            @QueryParam("size") @DefaultValue("20") int pageSize) {

        Page page = Page.of(pageIndex, pageSize);
        Sort sort = Sort.by();

        List<LabManagerDto> labManagerDtos = new ArrayList<>();

        for (LabManager labManager : labManagerResource.list(page, sort)) {
            labManagerDtos.add(labManagerMapper.toLabManagerDto(labManager));
        }

        return Response.status(Response.Status.OK).entity(labManagerDtos).build();
    }

    @GET
    @Path("/{id}/picture")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLabManagerPicture(@PathParam("id") Long id) {

        return labManagerResource.get(id).getPicture();
    }
}