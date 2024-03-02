import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class СourierCreatingTest {

    String login = "Nadezhda42" + Math.random();
    String password = "qwerty1232" + Math.random();


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test

    @Step("Check that possible to create of courier with correct data")
    public void checkSuccessfullyCreationOfCourier() {

        CouriersPOJO courier = new CouriersPOJO(login,password,"Надежда");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        response.then().statusCode(201)
                .and()
                .body("ok", equalTo(true));

    }
    @Test

    @Step("Check that impossible to create of courier without an login")
    public void checkFailedWithoutLogin() {

        CouriersPOJO courier = new CouriersPOJO(null,password,"Надежда");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        response.then().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test

    @Step("Check that impossible to create of courier without an password")
    public void checkFailedWithoutPassword() {

        CouriersPOJO courier = new CouriersPOJO(login,null,"Надежда");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        response.then().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test

    @Step("Check that impossible to create of courier is system already contains user with same data")
    public void checkFailedWithExistLogin() {
        //создадим пользователя
        String login1 = "Nadezhda44";

        CouriersPOJO courier1 = new CouriersPOJO(login1,password,"Надежда");

        Response response1 = given()
                .header("Content-type", "application/json")
                .body(courier1)
                .post("/api/v1/courier");

        // попытаемся создать с таким же логином

        CouriersPOJO courier = new CouriersPOJO(login1,password,"Надежда");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");
        response.then().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));


    }



}




