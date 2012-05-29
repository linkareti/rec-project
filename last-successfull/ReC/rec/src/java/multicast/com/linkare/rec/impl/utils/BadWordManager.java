/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.impl.utils;

import com.linkare.rec.am.RecServiceRemote;
import com.linkare.rec.am.repository.BadWordDTO;
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.utils.locator.BusinessServiceEnum;
import com.linkare.rec.impl.utils.locator.BusinessServiceLocator;
import com.linkare.rec.impl.utils.locator.BusinessServiceLocatorException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public final class BadWordManager {

    private BadWordManager() {
        ExecutorScheduler.scheduleAtFixedRate(new BadWordManagerRefreshScheduledUnit(), 0, BADWORD_REFRESH_TIME_LAP_MINUTES, TimeUnit.MINUTES);
    }
    private static volatile BadWordManager instance;

    public static BadWordManager getInstance() {
        if (instance == null) {
            synchronized (BadWordManager.class) {
                if (instance == null) {
                    instance = new BadWordManager();
                }
            }
        }
        return instance;
    }
    public String REPLACE = "***";
    private RecServiceRemote recServiceRemote = null;
    private static final Logger log = Logger.getLogger(BadWordManager.class.getName());
    public static final String SYSPROP_BADWORD_REFRESH_TIME_LAP_MINUTES = "ReC.MultiCast.BadWordManager.Refresh.Lap.Time.Minutes";
    private static final int BADWORD_REFRESH_TIME_LAP_MINUTES = Defaults.defaultIfEmpty(
			System.getProperty(SYSPROP_BADWORD_REFRESH_TIME_LAP_MINUTES), 1440);
    private List<BadWordDTO> badWordDTO = null;

    public String filterBadWord(String message) {
        Pattern pat;
        Matcher matcher = null;
        for (BadWordDTO badword : badWordDTO) {
            pat = Pattern.compile("\\b" + badword.getRegex() + "\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            matcher = pat.matcher(message);
            message = matcher.replaceAll(REPLACE);
        }
        return message;
    }

    public void refreshBadWordList() {
        try {
            recServiceRemote = BusinessServiceLocator.getInstance().getBusinessInterface(
                    BusinessServiceEnum.REC_SERVICE);
            badWordDTO = recServiceRemote.getAllBadWordRegex();
        } catch (BusinessServiceLocatorException be) {
            if (BadWordManager.log.isLoggable(Level.FINE)) {
                BadWordManager.log.log(Level.FINE, "BadWordManager - Error load bad words list! {0}", be);
            }
        } catch (RemoteException re) {
            if (BadWordManager.log.isLoggable(Level.FINE)) {
                BadWordManager.log.log(Level.FINE, "BadWordManager - Error load bad words list! {0}", re);
            }
        }
    }

    private class BadWordManagerRefreshScheduledUnit extends ScheduledWorkUnit {

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            BadWordManager.log.log(Level.INFO, "BadWordManagerRefreshScheduledUnit - Going to refresh the bad words list.");
            refreshBadWordList();
        }

        @Override
        public void logThrowable(String message, Throwable throwable) {
            BadWordManager.log.log(Level.SEVERE, message, throwable);
        }
    }
}
