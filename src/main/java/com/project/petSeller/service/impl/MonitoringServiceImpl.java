package com.project.petSeller.service.impl;

import com.project.petSeller.service.MonitoringService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MonitoringServiceImpl implements MonitoringService {

    private Logger LOGGER = LoggerFactory.getLogger(MonitoringServiceImpl.class);

    private final Counter offerSearches;

    public MonitoringServiceImpl(MeterRegistry meterRegistry) {
        offerSearches = Counter
                .builder("ad_search_counter")
                .description("How many searches were made on the ads")
                .register(meterRegistry);
    }

    @Override
    public void logOfferSearch() {

        LOGGER.info("Ad search.");

        offerSearches.increment();
    }
}
