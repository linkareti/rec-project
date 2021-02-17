package com.linkare.rec.labmanager.persistence;
import com.linkare.rec.labmanager.persistence.entities.LabManager;
import com.linkare.rec.labmanager.rest.LabManagerResource;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import liquibase.pro.packaged.L;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
public class LabManagerTest {

    @InjectMock
    LabManagerResource labManagerResource;

    @Test
    public void testGetLabManager() {

        // setup
        Long fakeID = 2L;

        LabManager fakeLabManager = new LabManager();
        fakeLabManager.setId(fakeID);

        Mockito.when(labManagerResource.get(fakeID)).thenReturn(fakeLabManager);

        // exercise
        LabManager labManager = labManagerResource.get(fakeID);

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).get(fakeID);
        Assertions.assertSame(fakeLabManager, labManager);
        Assertions.assertNull(labManagerResource.get(12L));
    }

    @Test
    public void testDeleteLabManager() {

        // setup
        Long fakeID = 10L;

        LabManager fakeLabManager = new LabManager();
        fakeLabManager.setId(fakeID);

        Mockito.when(labManagerResource.get(fakeID)).thenReturn(fakeLabManager);

        // exercise
        labManagerResource.delete(fakeID);

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).delete(fakeID);
    }

    @Test
    public void testAddLabManager() {

        // setup
        LabManager fakeLabManager = new LabManager();

        Mockito.when(labManagerResource.add(Mockito.any(LabManager.class))).thenReturn(fakeLabManager);

        // exercise
        LabManager labManager = labManagerResource.add(fakeLabManager);

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).add(Mockito.any(LabManager.class));
        Assertions.assertSame(fakeLabManager, labManager);
        Assertions.assertNotNull(labManager);
    }

    @Test
    public void testUpdateLabManager() {

        // setup
        Long fakeID = 10L;

        LabManager fakeLabManager = new LabManager();

        Mockito.when(labManagerResource.update(Mockito.anyLong(), Mockito.any(LabManager.class))).thenReturn(fakeLabManager);

        // exercise
        LabManager labManager = labManagerResource.update(fakeID, fakeLabManager);

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).update(Mockito.anyLong(), Mockito.any(LabManager.class));
        Assertions.assertEquals(fakeLabManager, labManager);
        Assertions.assertNotNull(labManager);
    }

    @Test
    public void testListAll() {

        // setup
        List<LabManager> fakeLabManagers = new ArrayList<>();

        Page fakePage = Page.of(0, 20);
        Sort fakeSort = Sort.by();

        Mockito.when(labManagerResource.list(fakePage, fakeSort)).thenReturn(fakeLabManagers);

        // exercise
        List<LabManager> labManagers = labManagerResource.list(fakePage, fakeSort);

        // verify
        Mockito.verify(labManagerResource, Mockito.times(1)).list(fakePage, fakeSort);
        Assertions.assertSame(fakeLabManagers, labManagers);
    }
}
