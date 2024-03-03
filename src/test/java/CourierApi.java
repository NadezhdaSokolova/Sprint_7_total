import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CourierApi {

    final static String COURIER = "/api/v1/courier";
    @Step("Make post-request to create courier")
    public static Response createCourier(CouriersPOJO courier){

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post(COURIER);

        return response;

    }

    @Step("Make delete-request to delete of courier")
    public static Response deleteCourier(CouriersPOJO courier, String idOfCourier){

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .delete(COURIER + "/" + idOfCourier);

        System.out.println(COURIER + "/" + idOfCourier);

        return response;

    }

    @Step("Make post-request to login of courier")
    public static Response loginCourier(CouriersPOJO courier){

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");

        return response;

    }
}
