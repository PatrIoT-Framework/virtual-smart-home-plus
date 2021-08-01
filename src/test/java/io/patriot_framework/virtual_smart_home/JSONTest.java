package io.patriot_framework.virtual_smart_home;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = InitialApplication.class)
class JSONTest {

    @Test
    void AcceptingJSONGet() {
        given()
                .contentType(ContentType.JSON)
                .port(8080)
                .when().get("http://localhost:8080/fireplace")
                .then().statusCode(200);
    }

    @Test
    void JSONResponse() {
        Response response = given()
                .contentType(ContentType.JSON)
                .port(8080)
                .when().get("http://localhost:8080/fireplace")
                .then().contentType(ContentType.JSON).statusCode(200)
                .extract().response();

        String jsonAsString = response.asString();
        assertThat(jsonAsString, equalTo("{\"label\": \"fireplace\"}"));
    }

    @Test
    void AcceptingJSONPost() {
        given()
                .contentType(ContentType.JSON)
                .port(8080)
                .when().post("http://localhost:8080/fireplace")
                .then().statusCode(200);
    }

    @Test
    public void getResponseTime() {
        SpringApplication.run(InitialApplication.class);
        given()
                .when().get("http://localhost:8080/fireplace")
                .then().statusCode(200).time(lessThan(1000L));
    }
}
