package com.linkare.rec.am.model;

import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Joao
 */
@Remote
public interface ResourceAllocationFacadeInterface {

    List<String> getReservations(String laboratory, String experimentName, Date startDate, Date endDate) throws Exception;

    void initData();
}
