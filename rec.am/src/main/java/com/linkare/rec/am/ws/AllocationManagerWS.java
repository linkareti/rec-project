package com.linkare.rec.am.ws;

import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.model.LaboratoryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.ejb.Stateless;
import javax.jws.WebParam;

/**
 *
 * @author Joao
 */
@WebService(endpointInterface="com.linkare.rec.am.ws.AllocationManagerWSInterface")
@Stateless()
public class AllocationManagerWS {


    @EJB
    private LaboratoryFacade ejbRef;

    public int count(){
        return ejbRef.count();
    }

    /**
     * Web service operation
     */
    
    public String echo(@WebParam(name="name") String name) {
        return "Hello " + name;
    }

    /**
     * Web service operation
     */

    public String find(Long id) {
        return ejbRef.find(id).getName();
    }

    /**
     * Web service operation
     */

    public String findByName(String name) {
        return "Not implemented";
//        return ejbRef.findByName(name).getName();
    }

    /**
     * Web service operation
     */
    public List<Laboratory> getAllLaboratories() {
        return ejbRef.findAll();
    }
}
