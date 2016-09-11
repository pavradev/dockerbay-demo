package com.github.pavradev.dockerbay.demo.containers;

import com.github.pavradev.dockerbay.ContainerConfig;

public class WebappContainer {

    public static String CONTAINER_NAME = "webapp";

    public static ContainerConfig getConfig() {
        return ContainerConfig.builder()
                .withImage("demo/webapp")
                .withName(CONTAINER_NAME)
                .waitForUrl("/api/hotels")
                .withExposedTcpPort(8082)
                .build();
    }
}
