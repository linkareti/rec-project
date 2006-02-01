package com.linkare.rec.repository;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.UserInfo;

public interface RepositoryManagerOperations
{
    DataProducer getDataProducer(UserInfo user, String id);
    DataProducerConfig[] listDataProducers(UserInfo user, HardwareAcquisitionConfigSearch[] search_params);
} // interface RepositoryManagerOperations
