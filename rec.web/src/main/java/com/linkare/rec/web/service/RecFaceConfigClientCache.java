package com.linkare.rec.web.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Singleton;
import javax.xml.bind.JAXBException;

import com.linkare.rec.web.config.ReCFaceConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for caching the requests of Remote rec configs
 * Deletes de cache every 5 minutes
 */
@Singleton
public class RecFaceConfigClientCache {

    private static final Logger LOG = LoggerFactory.getLogger(RecFaceConfigClientCache.class);

    private Map<String, ReCFaceConfig> configs;
    private final Object lockObject = new Object();
    private Date date;

    public RecFaceConfigClientCache() {
        this.configs = new ConcurrentHashMap<>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        date = c.getTime();
    }

    /**
     * Find a RecFaceConfig from the cache or try to fetch it from the remove service
     *
     * @param url Url of the remote service with the Rec Config
     * @return ReCFaceConfig or null if not found.
     */
    public ReCFaceConfig getConfig(String url) {
        synchronized (lockObject) {
            cleanUp();
            if (!configs.containsKey(url)) {
                try {
                    configs.put(url, RecFaceConfigClient.getRecFaceConfig(url));
                } catch (IOException e) {
                    LOG.warn("Problem communicating with URL {}", url, e);
                } catch (JAXBException e) {
                    LOG.warn("Unable to unmarshall information coming from URL {}", url, e);
                }
            }
        }
        return configs.get(url);
    }

    private void cleanUp() {
        if (date.getTime() < System.currentTimeMillis()) {
            configs = new ConcurrentHashMap<>();

            Calendar c = Calendar.getInstance();
            c.add(Calendar.MINUTE, 5);
            date = c.getTime();
        }
    }

}
