import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    String login = "FL1";
    String password = "LG1";

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
        CouriersPOJO courier = new CouriersPOJO(login, password, "Надежда");

        Response testCourierForLogin = CourierApi.createCourier(courier);
    }

    @After
    public void deleteTestCourierSuccessWithoutDots(){

        CouriersPOJO courier = new CouriersPOJO(login,password,null);

        String id = CourierApi.loginCourier(courier).body().asString();
        String[] split = id.split(":");
        String idOfCourier = split[1].substring(0, split[1].length() - 1);
        System.out.println(idOfCourier);

        CourierApi.deleteCourier(courier, idOfCourier).then().statusCode(200)
                .and()
                .assertThat().body("ok", equalTo(true));

        CourierApi.loginCourier(courier).then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));

    }


    @Test
    @Description("Check that possible to take access to the site with correct login/password data")
    public void checkSuccessAutorization() {

        CouriersPOJO courier = new CouriersPOJO(login,password,null);
        CourierApi.loginCourier(courier).then().statusCode(200)
                .and()
                .assertThat().body("id", notNullValue());

    }

    @Test
    @Description("Check that impossible to take access to the site with incorrect login")
    public void checkFailedAutorizationWithoutLogin() {

        CouriersPOJO courier = new CouriersPOJO(null,password,null);
        CourierApi.loginCourier(courier).then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Check that impossible to take access to the site with incorrect password")
    public void checkFailedAutorizationWithoutPassword() {

        CouriersPOJO courier = new CouriersPOJO(login,null,null);
        CourierApi.loginCourier(courier).then().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Check that impossible to take access if login is absent in the system")
    public void checkFailedAutorizationWithoutExistLogin() {

        String login2 = login + Math.random();

        CouriersPOJO courier = new CouriersPOJO(login2,password,null);
        CourierApi.loginCourier(courier).then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Check that impossible to take access if password is absent in the system")
    public void checkFailedAutorizationWithoutExistPassword() {

        String password2 = password + Math.random();

        CouriersPOJO courier = new CouriersPOJO(login,password2,null);
        CourierApi.loginCourier(courier).then().statusCode(404)
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }


}



