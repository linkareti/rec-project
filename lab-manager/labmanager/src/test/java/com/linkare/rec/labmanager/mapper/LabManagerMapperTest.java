/*package com.linkare.rec.labmanager.mapper;

import com.linkare.rec.labmanager.command.LabManagerDto;
import com.linkare.rec.labmanager.persistence.entities.LabManager;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.sql.rowset.serial.SerialBlob;

@QuarkusTest
public class LabManagerMapperTest {

    @InjectMock
    LabManagerMapper labManagerMapper;

    @Test
    public void toLabManagerDtoTest() {

        Long fakeID = 999L;
        String name = "TestLabManager";
        String description = "Just a test";
        SerialBlob picture = null;
        String coordinates = "12.3,12.4";
        String editUsers = "Rui, Pedro, Joao";
        String url = null;
        boolean deleted = false;

        LabManager fakeLabManager = Mockito.spy(LabManager.class);
        fakeLabManager.setId(fakeID);
        fakeLabManager.setName(name);
        fakeLabManager.setDescription(description);
        fakeLabManager.setPicture(picture);
        fakeLabManager.setCoordinates(coordinates);
        fakeLabManager.setEditUsers(editUsers);
        fakeLabManager.setUrl(url);
        fakeLabManager.setDeleted(deleted);

        System.out.println(fakeLabManager);

        LabManagerDto labManagerDto;

        Mockito.when(labManagerMapper.toLabManagerDto(fakeLabManager)).thenReturn(Mockito.any(LabManagerDto.class));

        labManagerDto = labManagerMapper.toLabManagerDto(fakeLabManager);

        System.out.println("###################################################################");
        System.out.println(labManagerDto);

        Assertions.assertSame(labManagerDto.getId(), fakeID);
        Assertions.assertSame(labManagerDto.getName(), name);
        Assertions.assertSame(labManagerDto.getDescription(), description);
        Assertions.assertSame(labManagerDto.getPicture(), picture);
        Assertions.assertSame(labManagerDto.getCoordinates(), coordinates);
        Assertions.assertSame(labManagerDto.getEditUsers(), editUsers);
        Assertions.assertSame(labManagerDto.getUrl(), url);
    }
}*/