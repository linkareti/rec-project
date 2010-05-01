package com.linkare.rec.am.ws;

import com.linkare.rec.am.model.Laboratory;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Joao
 */
@WebService
public interface AllocationManagerWSInterface {

    public String echo(String name);

    public int count();

    public String find(Long id);

    public String findByName(String name);

    public List<Laboratory> getAllLaboratories();

}
