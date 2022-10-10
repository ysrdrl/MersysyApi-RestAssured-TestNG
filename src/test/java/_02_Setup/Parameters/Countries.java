package _02_Setup.Parameters;

import _01_Login.Login;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.apache.commons.math3.analysis.function.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import zz_Extra.RandomInformation;

import static io.restassured.RestAssured.*;

public class Countries extends Login {

    @BeforeClass
    public void setup() {

        baseURI = "https://demo.mersys.io/school-service/api/";


    }



    String id;

    @Test
    public void createCountry() {

        CountryInformation countryInformation = new Countries().new CountryInformation();
        countryInformation.setName(RandomInformation.randomName());
        countryInformation.setCode(RandomInformation.randomNumber());

        id =

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(countryInformation)


                        .when()
                        .post("countries")

                        .then()
                        .statusCode(201)
                        .extract().path("id")
        ;

    }

    String countryName;
    String countryCode;

    @Test(dependsOnMethods = "createCountry")
    public void updateCountry() {

        CountryInformation countryInformation = new Countries().new CountryInformation();
        countryInformation.setId(id);
        countryInformation.setName(RandomInformation.randomName());
        countryInformation.setCode(RandomInformation.randomNumber());

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .cookies(cookies)
                        .body(countryInformation)

                        .when()
                        .put("countries")

                        .then()
                        //.log().body()
                        .statusCode(200)
                        .extract().response();
        countryName = response.path("name");
        countryCode = response.path("code");
    }

    @Test(dependsOnMethods = "updateCountry")
    public void createCountryNegative() {

        CountryInformation ci = new Countries().new CountryInformation();
        ci.setName(countryName);
        ci.setCode(countryCode);


        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(ci)


                .when()
                .post("countries")

                .then()
                .statusCode(400)

        ;
    }

    @Test(dependsOnMethods = "createCountryNegative")
    public void deleteCountry() {

        given()
                .pathParam("globalId", id)
                .cookies(cookies)


                .when()
                .delete("countries/{globalId}")

                .then()
                .statusCode(200)

        ;

    }

    @Test(dependsOnMethods = "deleteCountry", priority = 1)
    public void deleteCountryNegative() {

        given()
                .cookies(cookies)
                .pathParam("globalId", id)


                .when()
                .delete("countries/{globalId}")

                .then()
                .statusCode(200)

        ;
    }

    @Test(dependsOnMethods = "deleteCountry", priority = 2)
    public void updateCountryNegative() {

        CountryInformation ci = new Countries().new CountryInformation();
        ci.setId(id);
        ci.setName(countryName);
        ci.setCode(countryCode);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(ci)

                .when()
                .put("countries")

                .then()
                .statusCode(200)
        ;

    }


    public class CountryInformation {
        private String id;
        private String name;
        private String code;
        private String hasState;

        public String getHasState() {
            return hasState;
        }

        public void setHasState(String hasState) {
            this.hasState = hasState;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

    }

}
