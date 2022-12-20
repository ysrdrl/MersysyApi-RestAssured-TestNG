package _03_HumanResources.Setup;

import _01_Login.Login;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import zz_Extra.RandomInformation;

import static io.restassured.RestAssured.*;
public class Positions extends Login {

    @Test
    public void createPosition() {

        PositionsInformation pi=new PositionsInformation();
        pi.setActive(true);
        pi.setName(RandomInformation.randomName());
        pi.setShortName(RandomInformation.randomName());
        pi.setTenantId("5fe0786230cc4d59295712cf");
        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .log().body()
                .body(pi)


                .when()
                .post("employee-position")


                .then()
                .statusCode(201)

                ;



    }

    public class PositionsInformation {

        private boolean active;
        private String id;
        private String name;
        private String shortName;
        private String tenantId;
        private String[] translateName;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
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

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

        public String[] getTranslateName() {
            return translateName;
        }

        public void setTranslateName(String[] translateName) {
            this.translateName = translateName;
        }
    }




}
