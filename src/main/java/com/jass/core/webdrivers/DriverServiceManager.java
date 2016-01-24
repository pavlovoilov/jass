package com.jass.core.webdrivers;

import org.openqa.selenium.remote.service.DriverService;

public class DriverServiceManager {

    private static DriverServiceContainer driverServiceContainer = new DriverServiceContainer();

    public static void setDriverService(DriverService driverService) {
        driverServiceContainer.setDriverService(driverService);
    }

    public static DriverService getDriverService() {
        return driverServiceContainer.getDriverService();
    }

    public static void stopService() {
        DriverService driverService = getDriverService();
        if (driverService != null)
            driverService.stop();
    }
}