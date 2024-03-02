import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)

public class OrderCreatingTest {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public OrderCreatingTest (String firstName, String lastName, String address, String metroStation, String phone,
                              int rentTime, String deliveryDate, String comment, String[] color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters

    public static Object[][] makeOrder() {
        return new Object [][] {
                {"Naruto","Uchiha","Konoha, 142 apt.","4", "+7 800 355 35 35", 5, "2024-03-01", "Order with black", new String[] {"BLACK"}},
                {"Nino","Babariha","Happiness, 100 apt.","3", "+7 900 355 35 35", 6, "2024-03-02", "Order with grey",new String[] {"GREY"}},
                {"Nadezhda","Luchano","Green Street, 142 apt.","2", "+7 800 355 35 55", 7, "2024-03-03", "Order with both of colors", new String[] {"BLACK", "GREY"}},
                {"Net","Shtolc","London,  apt. 1","1", "+7 800 755 35 35", 4, "2024-03-04", "Order without color", null},
        };
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test

    @Step("Check that possible to create orders with correct data")

    public void checkPossibilityToCreateOrderCorrectly(){
        OrderPoJO order = new OrderPoJO (firstName,lastName,address,metroStation, phone, rentTime, deliveryDate, comment, color);

        Response response = given()
                .header("Content-type", "application/json")
                .body(order)
                .post("/api/v1/orders");
        response.then().statusCode(201)
                .and()
                .assertThat().body("track", notNullValue());;
    }

}


