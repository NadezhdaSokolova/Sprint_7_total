import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;


public class СourierCreatingTest {
    String login = "Nadezhda42" + Math.random();
    String password = "qwerty1232" + Math.random();


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @After

    public void deleteTestCourierSuccessWithoutDots(){
        CouriersPOJO courier = new CouriersPOJO(login,password,null);

        try  {
            CourierApi.loginCourier(courier);

            String id = CourierApi.loginCourier(courier).body().asString();
            String[] split = id.split(":");
            String idOfCourier = split[1].substring(0, split[1].length() - 1);
            System.out.println(idOfCourier);

            CourierApi.deleteCourier(courier, idOfCourier);
        }
        catch (Exception e){
            System.out.println ("Удалять нечего. Пользователь не прошел авторизацию.");
        }

    }


    @Test
    @Description("Check that possible to create of courier with correct data")
    public void checkSuccessfullyCreationOfCourier() {
        CouriersPOJO courier = new CouriersPOJO(login,password,"Надежда");

        CourierApi.createCourier(courier).then().statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }
    @Test
    @Description("Check that impossible to create of courier without an login")
    public void checkFailedWithoutLogin() {

        CouriersPOJO courier = new CouriersPOJO(null,password,"Надежда");

        CourierApi.createCourier(courier).then().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Check that impossible to create of courier without an password")
    public void checkFailedWithoutPassword() {

        CouriersPOJO courier = new CouriersPOJO(login,null,"Надежда");

        CourierApi.createCourier(courier).then().statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Check that impossible to create of courier is system already contains user with same data")
    public void checkFailedWithExistLogin() {
        //создадим пользователя
        String login1 = "Nadezhda44";

        CouriersPOJO courier = new CouriersPOJO(login1,password,"Надежда");

        CourierApi.createCourier(courier).then().statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется"));

    }


}




