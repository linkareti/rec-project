package com.linkare.rec.am.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Joao
 */
@Stateless
public class ResourceAllocationFacade implements ResourceAllocationFacadeInterface {

    // Persistence
    @PersistenceContext(unitName = "AllocationManagerPU")
    private EntityManager em;

    @Override
    public List<String> getReservations(String laboratory, String experimentName, Date startDate,
            Date endDate) throws Exception {


        Laboratory lab = null;
        Experiment exp = null;


        // security method: laboratory not null and laboratory exists.
        // startDate not null and valid, endDate not null and valid, endDate >
        // startDate and experiment exists in laboratory
        //
        validaDados(laboratory, experimentName, startDate, endDate);

        // data aquisition:
        List<Reservation> validReserves = Reservation.findReservationsInLab(laboratory, experimentName, startDate, endDate, em);
        // TODO:for each reservation obtain users


        List<String> experienceNameList = new ArrayList<String>();
        for (Reservation reserv : validReserves) {
            experienceNameList.add(reserv.toString());
        }
        // TODO:create return Data


        return experienceNameList;

    }

    private void validaDados(String laboratory, String experiment, Date startDate,
            Date endDate) throws Exception {

        Experiment exp = null;
        Laboratory lab = null;

        lab = Laboratory.findByName(laboratory, em);

        if (lab == null) {
            throw new Exception("laboratory not referenced");
        }

        if (startDate == null) {
            throw new Exception("start date not provided");
        }

        if (endDate == null) {
            throw new Exception("end date not provided");
        }

        if (endDate.before(startDate)) {
            throw new Exception("start date after end date");
        }

        try {
            exp = Laboratory.findExperiment(laboratory, experiment, em);
        } catch (IndexOutOfBoundsException e) {
            throw new Exception("No laboratory with given experiment");
        }

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void initData() {
        UserPrincipal user = new UserPrincipal();
        user.setName("admin");
        em.persist(user);

        Laboratory lab = new Laboratory();
        lab.setName("ola");
        lab.setState(new State());
        lab.getState().setActive(true);
        lab.getState().setHelpMessage("help");
        lab.getState().setLabel("ola2");
        lab.getState().setUrl("url");

        em.persist(lab);

        Experiment exp = new Experiment();
        exp.setName("testExperiment");
        exp.setDescription("Test experiment");
        exp.setLaboratory(lab);
        exp.setState(new State());

        em.persist(exp);

        Reservation res = new Reservation();
        res.setExperiment(exp);
        Calendar begining = Calendar.getInstance();
        begining.set(2005, 11, 10);
        Calendar end = Calendar.getInstance();
        end.set(2005, 11, 11);
        res.setStartDate(begining.getTime());
        res.setEndDate(end.getTime());

        em.persist(res);
    }
}
