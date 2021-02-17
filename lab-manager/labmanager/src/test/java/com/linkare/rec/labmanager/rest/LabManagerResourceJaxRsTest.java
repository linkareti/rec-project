package com.linkare.rec.labmanager.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkare.rec.labmanager.command.LabManagerDto;
import com.linkare.rec.labmanager.mapper.LabManagerMapper;
import com.linkare.rec.labmanager.persistence.entities.LabManager;
import com.linkare.rec.labmanager.persistence.repos.LabManagerRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.rowset.serial.SerialBlob;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

@QuarkusTest
@Tag("integration")
public class LabManagerResourceJaxRsTest {

    @InjectMock
    LabManagerResource labManagerResource;

    @InjectMock
    LabManagerMapper labManagerMapper;

    @InjectMock
    LabManagerRepository labManagerRepository;

    @Test
    public void testGetLabManagerResource() throws JsonProcessingException {

        Long fakeID = 999L;

        LabManager fakeLabManager = getFakeLabManager();
        LabManagerDto fakeLabManagerDto = getFakeLabManagerDto();
        ObjectMapper mapper = new ObjectMapper();

        // setup
        Mockito.when(labManagerMapper.toLabManagerDto(fakeLabManager)).thenReturn(fakeLabManagerDto);
        Mockito.when(labManagerResource.get(fakeID)).thenReturn(fakeLabManager);

        // exercise
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", fakeID)
                .get("/api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.OK.getStatusCode());

        String jsonObject = mapper.writeValueAsString(response.extract().body().jsonPath().get());
        String fakeLabManagerDTOAsJson = mapper.writeValueAsString(fakeLabManagerDto);

        // verify
        Mockito.verify(labManagerMapper, Mockito.times(1)).toLabManagerDto(fakeLabManager);
        Mockito.verify(labManagerResource, Mockito.times(1)).get(fakeID);
        Assertions.assertEquals(jsonObject, fakeLabManagerDTOAsJson);
    }

    @Test
    public void testGetLabManagerResourceWithInvalidLabManager() {

        Long invalidID = 888L;

        // setup
        Mockito.when(labManagerResource.get(invalidID)).thenReturn(null);

        // exercise
        ValidatableResponse response = given()
                .contentType(ContentType.JSON)
                .pathParam("id", invalidID)
                .get("/api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).get(invalidID);
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testAddLabManagerResource() throws JsonProcessingException {

        LabManager fakeLabManager = getFakeLabManager();
        LabManagerDto fakeLabManagerDto = getFakeLabManagerDto();
        ObjectMapper mapper = new ObjectMapper();

        // setup
        Mockito.when(labManagerMapper.toLabManager(fakeLabManagerDto)).thenReturn(fakeLabManager);
        Mockito.when(labManagerResource.add(fakeLabManager)).thenReturn(fakeLabManager);

        // exercise
        ValidatableResponse response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(fakeLabManagerDto))
                .post("/api/labmanager")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        // verify
        Mockito.verify(labManagerMapper, Mockito.times(1)).toLabManager(fakeLabManagerDto);
        Mockito.verify(labManagerResource, Mockito.times(1)).add(fakeLabManager);
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.CREATED.getStatusCode());
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteLabManagerResource() {

        Long fakeID = 999L;

        // setup
        Mockito.when(labManagerResource.delete(fakeID)).thenReturn(true);

        // exercise
        ValidatableResponse response = given()
                .pathParam("id", fakeID)
                .delete("api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).delete(fakeID);
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testDeleteLabManagerResourceWithInvalidLabManager() {

        Long invalidID = 888L;

        // setup
        Mockito.when(labManagerResource.get(invalidID)).thenReturn(null);

        // exercise
        ValidatableResponse response = given()
                .pathParam("id", invalidID)
                .delete("api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).delete(invalidID);
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.NOT_FOUND.getStatusCode());
    }

    @Test
    @TestSecurity(user = "Rui")
    public void testDeleteLabManagerResourceWithUserOnTheList() {

        Long fakeID = 999L;
        String editUsers = "Rui, Pedro, Joao";

        LabManager fakeLabManager = getFakeLabManager();
        fakeLabManager.setId(fakeID);
        fakeLabManager.setEditUsers(editUsers);

        // setup
        Mockito.when(labManagerRepository.findById(fakeID)).thenReturn(fakeLabManager);
        Mockito.when(labManagerResource.delete(fakeID)).thenReturn(true);

        // exercise
        ValidatableResponse response = given()
                .pathParam("id", fakeID)
                .delete("api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.NO_CONTENT.getStatusCode());

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).delete(fakeID);
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    @TestSecurity(user = "Rui")
    public void testDeleteLabManagerResourceWithoutUserOnTheList() {

        Long fakeID = 999L;
        String editUsers = null;

        LabManager fakeLabManager = getFakeLabManager();
        fakeLabManager.setId(fakeID);
        fakeLabManager.setEditUsers(editUsers);

        // setup
        Mockito.when(labManagerRepository.findById(fakeID)).thenReturn(fakeLabManager);

        // exercise
        ValidatableResponse response = given()
                .pathParam("id", fakeID)
                .delete("api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.FORBIDDEN.getStatusCode());

        // verify
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.FORBIDDEN.getStatusCode());
    }

    @Test
    public void testListAllResource() {

        LabManager fakeLabManager = getFakeLabManager();
        LabManagerDto fakeLabManagerDto = getFakeLabManagerDto();

        List<LabManager> fakeLabManagers = new ArrayList<>();
        fakeLabManagers.add(fakeLabManager);

        // setup
        Mockito.when(labManagerMapper.toLabManagerDto(fakeLabManager)).thenReturn(fakeLabManagerDto);
        Mockito.when(labManagerResource.list(Mockito.any(Page.class), Mockito.any(Sort.class))).thenReturn(fakeLabManagers);

        // exercise
        ValidatableResponse response = given()
                                    .get("api/labmanager/list")
                                    .then()
                                    .statusCode(Response.Status.OK.getStatusCode());

        // verify
        Mockito.verify(labManagerMapper, Mockito.times(1)).toLabManagerDto(fakeLabManager);
        Mockito.verify(labManagerResource, Mockito.times(1)).list(Mockito.any(Page.class), Mockito.any(Sort.class));
        Assertions.assertEquals(1, fakeLabManagers.size());
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.OK.getStatusCode());
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateLabManagerResource() throws JsonProcessingException {

        Long fakeID = 999L;

        LabManager fakeLabManager = getFakeLabManager();
        LabManagerDto fakeLabManagerDto = getFakeLabManagerDto();
        ObjectMapper mapper = new ObjectMapper();

        // setup
        Mockito.when(labManagerMapper.toLabManager(fakeLabManagerDto)).thenReturn(fakeLabManager);
        Mockito.when(labManagerResource.update(fakeID, fakeLabManager)).thenReturn(fakeLabManager);
        Mockito.when(labManagerResource.get(fakeID)).thenReturn(fakeLabManager);

        // exercise
        ValidatableResponse response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(mapper.writeValueAsString(fakeLabManagerDto))
                .pathParam("id", fakeID)
                .put("/api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode());

        // verify
        Mockito.verify(labManagerMapper, Mockito.times(1)).toLabManager(fakeLabManagerDto);
        Mockito.verify(labManagerResource, Mockito.times(1)).update(fakeID, fakeLabManager);
        Mockito.verify(labManagerResource, Mockito.times(1)).get(fakeID);
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.CREATED.getStatusCode());
    }

    @Test
    @TestSecurity(user = "admin", roles = {"admin"})
    public void testUpdateLabManagerResourceWithInvalidLabManager() throws JsonProcessingException {

        Long invalidID = 888L;

        // setup
        Mockito.when(labManagerResource.get(invalidID)).thenReturn(null);

        // exercise
        ValidatableResponse response = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("id", invalidID)
                .put("/api/labmanager/{id}")
                .then()
                .statusCode(Response.Status.NOT_FOUND.getStatusCode());

        // verify
        Assertions.assertEquals(response.extract().statusCode(), Response.Status.NOT_FOUND.getStatusCode());
    }

    private LabManager getFakeLabManager() {

        Long fakeID = 999L;
        String name = "TestLabManager";
        String description = "Just a test";
        SerialBlob picture = null;
        String coordinates = "12.3,12.4";
        String editUsers = "Rui, Pedro, Joao";
        String url = null;
        boolean deleted = false;

        LabManager fakeLabManager = new LabManager();
        fakeLabManager.setId(fakeID);
        fakeLabManager.setName(name);
        fakeLabManager.setDescription(description);
        fakeLabManager.setPicture(picture);
        fakeLabManager.setCoordinates(coordinates);
        fakeLabManager.setEditUsers(editUsers);
        fakeLabManager.setUrl(url);
        fakeLabManager.setDeleted(deleted);

        return fakeLabManager;
    }

    private LabManagerDto getFakeLabManagerDto() {

        Long fakeID = 999L;
        String name = "TestLabManager";
        String description = "Just a test";
        SerialBlob picture = null;
        String coordinates = "12.3,12.4";
        String editUsers = "Rui, Pedro, Joao";
        String url = null;
        boolean deleted = false;

        LabManagerDto fakeLabManagerDto = new LabManagerDto();
        fakeLabManagerDto.setId(fakeID);
        fakeLabManagerDto.setName(name);
        fakeLabManagerDto.setDescription(description);
        fakeLabManagerDto.setPicture(picture);
        fakeLabManagerDto.setCoordinates(coordinates);
        fakeLabManagerDto.setEditUsers(editUsers);
        fakeLabManagerDto.setUrl(url);
        fakeLabManagerDto.setDeleted(deleted);

        return fakeLabManagerDto;
    }
}