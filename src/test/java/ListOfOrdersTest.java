import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Description("Check that possible to take access to the all orders in the system")
    public void checkListOfOrdersReturnsCorrectly(){

        OrderApi.getListOfOrders().then().statusCode(200)
                .and()
                .assertThat().body("orders", notNullValue());
    }
}
