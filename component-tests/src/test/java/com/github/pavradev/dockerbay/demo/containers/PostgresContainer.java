package com.github.pavradev.dockerbay.demo.containers;

import com.github.pavradev.dockerbay.ContainerConfig;

public class PostgresContainer {

    public static String CONTAINER_NAME = "postgres";

    public static ContainerConfig getConfig() {
        return ContainerConfig.builder()
                .withImage("postgres:9")
                .withName(CONTAINER_NAME)
                .waitForLogEntry("database system is ready to accept connections")
                .build();
    }

}
