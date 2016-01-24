package com.jass.core.webdrivers;

import org.openqa.selenium.remote.service.DriverService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DriverServiceContainer {

    private Map<Long, DriverService> THREAD_DRIVER_SERVICE = new ConcurrentHashMap<>();

    public void setDriverService(DriverService driverService) {
        THREAD_DRIVER_SERVICE.put(Thread.currentThread().getId(), driverService);
    }

    public DriverService getDriverService() {
        return THREAD_DRIVER_SERVICE.get(Thread.currentThread().getId());
    }
}