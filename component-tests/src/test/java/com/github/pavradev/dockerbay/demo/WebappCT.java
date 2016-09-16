package com.github.pavradev.dockerbay.demo;

import com.github.pavradev.dockerbay.DockerRule;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import com.github.pavradev.dockerbay.demo.containers.PostgresContainer;
import com.github.pavradev.dockerbay.demo.containers.ReviewsMockContainer;
import com.github.pavradev.dockerbay.demo.containers.WebappContainer;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

public class WebappCT {

    private WireMock reviewsMock;
    private RequestSpecification reqSpec;

    private Response resp;

    @Rule
    public DockerRule dockerRule = DockerRule.getDefault()
            .addContainer(PostgresContainer.getConfig())
            .addContainer(ReviewsMockContainer.getConfig())
            .addContainer(WebappContainer.getConfig());

    @Before
    public void before() {
        Integer webappPort = dockerRule.getEnvironment().getAllocatedPort(WebappContainer.CONTAINER_NAME);
        reqSpec = new RequestSpecBuilder().setBaseUri("http://localhost").setPort(webappPort).setBasePath("/").build();
        Integer mockPort = dockerRule.getEnvironment().getAllocatedPort(ReviewsMockContainer.CONTAINER_NAME);
        reviewsMock = new WireMock("localhost", mockPort);
    }

    @Test
    public void shouldGetHotelWithReviews() {
        //given
        dummyHotelExists();
        //and
        dummyHotelHasReviews();
        //when
        getDummyHotel();
        //then
        assertDummyHotelReturned();
        //and
        assertHotelReviewsNotEmpty();
    }

    @Test
    public void shouldUpdateHotel() {
        //given
        dummyHotelExists();
        //when
        updateDummyHotelAddress();
        //and
        getDummyHotel();
        //then
        assertHotelAddressUpdated();
    }

    @Test
    public void shouldDeleteHotel() {
        //given
        dummyHotelExists();
        //when
        deleteDummyHotel();
        //and
        getDummyHotel();
        //then
        assertNothingReturned();
    }

    private void assertHotelReviewsNotEmpty() {
        resp.then()
                .assertThat()
                .body("reviews.size()", CoreMatchers.equalTo(1));
    }

    private void assertDummyHotelReturned() {
        resp.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("name", CoreMatchers.equalTo("Dummy hotel"));
    }

    private void getDummyHotel() {
        resp = reqSpec.get("/api/hotels/1");
    }

    private void dummyHotelHasReviews() {
        reviewsMock.register(get(urlEqualTo("/api/reviews?hotelId=1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"rating\":\"AVERAGE\",\"checkInDate\":\"null\",\"title\":\"I liked it\",\"details\":\"null\"}]")));
    }

    private void dummyHotelExists() {
        reqSpec.contentType(ContentType.JSON)
                .body("{\"name\":\"Dummy hotel\",\"address\":\"Dummy address\",\"zip\":\"4001\"}")
                .post("/api/hotels");
    }

    private void assertHotelAddressUpdated() {
        resp.then()
                .assertThat()
                .body(containsString("Updated address"));
    }

    private void updateDummyHotelAddress() {
        reqSpec.contentType(ContentType.JSON)
                .body("{\"address\":\"Updated address\",\"zip\":\"4004\"}")
                .put("/api/hotels/1");
    }

    private void assertNothingReturned() {
        resp.then()
                .assertThat()
                .body(Matchers.isEmptyString());
    }

    private void deleteDummyHotel() {
        reqSpec.delete("/api/hotels/1");
    }
}
