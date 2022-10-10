package _02_Setup.Parameters;

import _01_Login.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import zz_Extra.RandomInformation;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class States extends Login {

    @BeforeClass
    public void setup() {
        baseURI = "https://demo.mersys.io/school-service/api/";
    }

    String id;

    @Test
    public void createCountry() {

        Countries.CountryInformation countryInformation = new Countries().new CountryInformation();
        countryInformation.setName(RandomInformation.randomName());
        countryInformation.setCode(RandomInformation.randomNumber());
        countryInformation.setHasState("true");

        id =

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(countryInformation)


                        .when()
                        .post("countries")

                        .then()
                        .statusCode(201)
                        .log().body()
                        .extract().path("id")
        ;

    }
String stateId;
    @Test(dependsOnMethods = "createCountry")
    public void createState() {
        Map<String,String> country=new HashMap<>();
        country.put("id",id);
        StatesInformation si = new States().new StatesInformation();
        si.setCountry(country);
        si.setName(RandomInformation.randomName());
        si.setShortName(RandomInformation.randomName());

stateId=
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(si)

                .when()
                .post("states")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")

        ;

    }
    String stateName;
    String stateShortName;
    @Test(dependsOnMethods = "createState")
    public void updateState() {
        Map<String,String> country=new HashMap<>();
        country.put("id", id);
        StatesInformation si=new States().new StatesInformation();
        si.setCountry(country);
        si.setId(stateId);
        si.setName(RandomInformation.randomName());
        si.setShortName(RandomInformation.randomName());
        Response response=
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(si)

                .when()
                .put("states")

                .then()
                .statusCode(200)
                .extract().response()

                ;
        stateName = response.path("name");
        stateShortName = response.path("shortName");

    }

    @Test(dependsOnMethods = "updateState")
    public void createStateNegative() {
        Map<String,String> country=new HashMap<>();
        country.put("id",id);
        StatesInformation si = new States().new StatesInformation();
        si.setCountry(country);
        si.setName(stateName);
        si.setShortName(stateShortName);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(si)

                .when()
                .post("states")

                .then()
                .statusCode(400)

                ;

    }

    @Test(dependsOnMethods = "createStateNegative")
    public void deleteState() {

        given()
                .cookies(cookies)
                .pathParam("stateId",stateId)


                .when()
                .delete("states/{stateId}")

                .then()
                .statusCode(200)

                ;
    }

    @Test(enabled = false) // Bug Var...
    public void deleteStateNegative() {

        given()
                .cookies(cookies)
                .pathParam("stateId",stateId)

                .when()
                .delete("states/{stateId}")

                .then()
                .statusCode(400)
                ;

    }

    @Test(dependsOnMethods = "deleteState")
    public void deleteCountry() {

        given()
                .cookies(cookies)
                .pathParam("countryId",id)

                .when()
                .delete("countries/{countryId}")

                .then()
                .statusCode(200)

                ;

    }


    public class StatesInformation {
       private Map<String ,String> country;
        private String id;
        private String name;
        private String shortName;

        public Map<String, String> getCountry() {
            return country;
        }

        public void setCountry(Map<String, String> country) {
            this.country = country;
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

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }


}
