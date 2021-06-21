package patriot.virtualsmarthomeplus;

import io.restassured.http.ContentType;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import patriot.virtualsmarthomeplus.routes.FirstRoute;
import static io.restassured.RestAssured.given;

@ContextConfiguration(classes = FirstRoute.class)
@SpringBootTest(classes = InitialApplication.class)
@RunWith(CamelSpringBootRunner.class)
class InitialApplicationTests {

	@BeforeEach
	void startSpring() {
		SpringApplication.run(InitialApplication.class);
	}

	@Test
	void firstTest() throws InterruptedException {
		given()
				.contentType(ContentType.TEXT)
				.port(8080)
				.when().get("http://localhost:8080/data")
				.then().statusCode(200);
	}

}
