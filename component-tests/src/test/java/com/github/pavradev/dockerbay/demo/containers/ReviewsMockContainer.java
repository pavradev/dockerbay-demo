package com.github.pavradev.dockerbay.demo.containers;

import com.github.pavradev.dockerbay.ContainerConfig;

import java.util.Arrays;

public class ReviewsMockContainer {

    public static String CONTAINER_NAME = "reviews-mock";

    public static ContainerConfig getConfig(){
        return ContainerConfig.builder()
                .withImage("ekino/wiremock:2.1.11")
                .withName(CONTAINER_NAME)
                .withExposedTcpPort(8083)
                .withCmd(Arrays.asList("--port", "8083", "--verbose"))
                .build();
    }
}
