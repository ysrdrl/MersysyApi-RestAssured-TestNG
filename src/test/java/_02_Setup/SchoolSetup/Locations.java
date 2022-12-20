package _02_Setup.SchoolSetup;

import _01_Login.Login;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Locations extends Login {
    String id;

    @Test
    public void createLocation() {
        HashMap<String,String> map=new HashMap<>();
        map.put("id", "6343bf893ed01f0dc03a509a");

        Locations.LocationInformation locationInformation=new Locations().new LocationInformation();
        locationInformation.setActive(true);
        locationInformation.setCapacity(61);
        locationInformation.setName("Bahamalar");
        locationInformation.setShortName("BHM");
        locationInformation.setType("CLASS");
        locationInformation.setSchool(map);


id=
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(locationInformation)

                .when()
                .post("location")

                .then()
                .statusCode(201)
                .log().body()
                .extract().path("id");
        ;

    }

    String locationName;
    String locationShortName;
    @Test(dependsOnMethods = "createLocation")
    public void updateLocation() {
        HashMap<String,String> map=new HashMap<>();
        map.put("id", "6343bf893ed01f0dc03a509a");

        LocationInformation locationInformation=new Locations().new LocationInformation();
        locationInformation.setSchool(map);
        locationInformation.setId(id);
        locationInformation.setName("Karayipler");
        locationInformation.setShortName("KRYP");
        locationInformation.setCapacity(16);
        locationInformation.setActive(false);
        locationInformation.setType("CLASS");

Response response=
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(locationInformation)

                .when()
                .put("location")

                .then()
                .statusCode(200)
                .log().body()
                .extract().response()
                ;
        locationName = response.path("name");
        locationShortName = response.path("shortName");
    }

    @Test(dependsOnMethods = "updateLocation")
    public void createLocationNegative() {

        HashMap<String,String> map=new HashMap<>();
        map.put("id", "6343bf893ed01f0dc03a509a");

        LocationInformation locationInformation=new Locations().new LocationInformation();
        locationInformation.setSchool(map);
        locationInformation.setName("Karayipler");
        locationInformation.setShortName("KRYP");
        locationInformation.setCapacity(16);
        locationInformation.setType("CLASS");
        locationInformation.setActive(false);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(locationInformation)

                .when()
                .post("location")

                .then()
                .statusCode(400)

                ;

    }

    @Test(dependsOnMethods = "createLocationNegative")
    public void deleteLocation() {

        given()
                .cookies(cookies)
                .pathParam("locationId",id)

                .when()
                .delete("location/{locationId}")

                .then()
                .statusCode(200)
                ;


    }

    @Test(dependsOnMethods = "deleteLocation")
    public void deleteLocationNegative() {


        given()
                .cookies(cookies)
                .pathParam("locationId",id)

                .when()
                .delete("location/{locationId}")
                .then()
                .statusCode(400)
                ;



    }

    public class LocationInformation {

        private boolean active;
        private int capacity;
        private String id;
        private String name;
        private HashMap<String ,String > school;
        private String shortName;
        private String type;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
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


        public HashMap<String, String> getSchool() {
            return school;
        }

        public void setSchool(HashMap<String, String> school) {
            this.school = school;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

}
