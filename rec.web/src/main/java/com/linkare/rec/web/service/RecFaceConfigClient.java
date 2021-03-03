package com.linkare.rec.web.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.bind.JAXBException;

import com.linkare.rec.web.config.ReCFaceConfig;

/**
 * Client for fetching and unmarshall Rec Face configs from remote services
 */
public final class RecFaceConfigClient {

    private RecFaceConfigClient() {

    }

    /**
     * Get the RecFaceConfig from  a remove service
     *
     * @param recConfigUrl url of the remove service
     * @return RecFaceConfig
     * @throws IOException if any communication problem happens
     * @throws JAXBException when it can't unmarshall the xml
     */
    public static ReCFaceConfig getRecFaceConfig(String recConfigUrl) throws IOException, JAXBException {
        URL url = new URL(recConfigUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/xml");

        InputStream xml = connection.getInputStream();
        return ReCFaceConfig.unmarshall(xml);
    }
}
