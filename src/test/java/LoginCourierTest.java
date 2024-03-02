import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {

    String login = "ForLoginTest2";
    String password = "Log32123";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        CouriersPOJO courier = new CouriersPOJO(login, password, "Надежда");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier");

    }


    @Test

    @Step("Check that possible to take access to the site with correct login/password data")

    public void checkSuccessAutorization() {

        CouriersPOJO courier = new CouriersPOJO(login,password,null);
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
        response.then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

    }

    @Test

    @Step("Check that impossible to take access to the site with incorrect login")
    public void checkFailedAutorizationWithoutLogin() {

        CouriersPOJO courier = new CouriersPOJO(null,password,null);
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test

    @Step("Check that impossible to take access to the site with incorrect password")
    public void checkFailedAutorizationWithoutPassword() {

        CouriersPOJO courier = new CouriersPOJO(login,null,null);
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
        response.then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test

    @Step("Check that impossible to take access if login is absent in the system")
    public void checkFailedAutorizationWithoutExistLogin() {

        String login2 = login + Math.random();

        CouriersPOJO courier = new CouriersPOJO(login2,password,null);
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test

    @Step("Check that impossible to take access if password is absent in the system")
    public void checkFailedAutorizationWithoutExistPassword() {

        String password2 = password + Math.random();

        CouriersPOJO courier = new CouriersPOJO(login,password2,null);
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
        response.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @After

    public void deleteTestCourierSuccessWithoutDots(){

        CouriersPOJO courier = new CouriersPOJO(login,password,null);

        // логинимся
        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");

        // сохраним id озданного курьера для удаления после теста

        String id = response.body().asString();
        String[] split = id.split(":");
        String idOfCourier = split[1].substring(0, split[1].length() - 1);
        System.out.println(idOfCourier);

        Response response2 = given()
                .header("Content-type", "application/json")
                .body(courier)
                .delete("/api/v1/courier/" + idOfCourier);

        System.out.println("/api/v1/courier/" + idOfCourier);

        response2.then().statusCode(200)
                .and()
                .assertThat().body("ok", equalTo(true));

        Response response3 = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login");
        response3.then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

    }

}



