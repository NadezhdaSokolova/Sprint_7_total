import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {

    final static String ORDER = "/api/v1/orders";

    @Step("Make post-request to create order")
    public static Response createOrder(OrderPoJO order) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .post(ORDER);

        return response;
    }

    @Step("Make get-request to get list of orders")
    public static Response getListOfOrders() {
        Response response = given()
                .get(ORDER);

        return response;
    }
}
