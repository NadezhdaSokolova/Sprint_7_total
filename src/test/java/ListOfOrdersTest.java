import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test

    @Step("Check that possible to take access to the all orders in the system")
    public void checkListOfOrdersReturnsCorrectly(){
        Response response = given()
                .get("/api/v1/orders");
        response.then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
