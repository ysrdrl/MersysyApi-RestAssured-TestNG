package _01_Login;

import _02_Setup.Parameters.Countries;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import zz_Extra.RandomInformation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Login {


     public static Cookies cookies;

    @BeforeClass
    public void login() {
        baseURI = "https://test.mersys.io/school-service/api/";

        LoginInformation login = new Login().new LoginInformation();
        login.setUsername("turkeyts");
        login.setPassword("TechnoStudy123");
        login.setRememberMe("true");

        cookies =


                given()
                        .contentType(ContentType.JSON)
                        .body(login)

                        .when()
                        .post("https://test.mersys.io/auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()

        ;
    }


    public class LoginInformation {
        private String username;
        private String password;
        private String rememberMe;

        public LoginInformation() {

        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRememberMe() {
            return rememberMe;
        }

        public void setRememberMe(String rememberMe) {
            this.rememberMe = rememberMe;
        }
    }


}
