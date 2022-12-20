package _02_Setup.Parameters;

import _01_Login.Login;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import zz_Extra.RandomInformation;

import static io.restassured.RestAssured.*;

public class BankAccounts extends Login {

    String id;

    @Test
    public void createBankAccount() {

        BankAccountInformation bai = new BankAccounts().new BankAccountInformation();
        bai.setActive(false);
        bai.setCurrency("USD");
        bai.setIban("TR" + RandomInformation.randomNumber(20));
        bai.setIntegrationCode(RandomInformation.randomNumber(2));
        bai.setName(RandomInformation.randomName());
        bai.setSchoolId("6390f3207a3bcb6a7ac977f9");

        id =

                given()
                        .cookies(cookies)
                        .log().body()
                        .contentType(ContentType.JSON)
                        .body(bai)

                        .when()
                        .post("bank-accounts")


                        .then()
                        .statusCode(201)
                        .log().body()
                        .extract().path("id")

        ;

    }

    String name;
    String integrationCode;
    String iban;

    @Test(dependsOnMethods = "createBankAccount")
    public void updateBankAccount() {
        BankAccountInformation bai = new BankAccountInformation();
        bai.setActive(true);
        bai.setCurrency("TRY");
        bai.setIban("TR" + RandomInformation.randomNumber(20));
        bai.setId(id);
        bai.setIntegrationCode(RandomInformation.randomNumber(3));
        bai.setName(RandomInformation.randomName());
        bai.setSchoolId("6390f3207a3bcb6a7ac977f9");
        Response response =

                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(bai)
                        .log().body()


                        .when()
                        .put("bank-accounts")


                        .then()
                        .statusCode(200)
                        .extract().response();

        ;
        name = response.path("name");
        integrationCode = response.path("integrationCode");
        iban = response.path("iban");
    }

    @Test(dependsOnMethods = "updateBankAccount")
    public void createBankAccountNegative() {
        BankAccountInformation bai = new BankAccountInformation();
        bai.setActive(true);
        bai.setCurrency("TRY");
        bai.setIban(iban);
        bai.setIntegrationCode(integrationCode);
        bai.setName(name);
        bai.setSchoolId("6390f3207a3bcb6a7ac977f9");

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(bai)

                .when()
                .post("bank-accounts")

                .then()
                .statusCode(400)
        ;

    }

    @Test(dependsOnMethods = "createBankAccountNegative")
    public void deleteBankAccount() {

        given()
                .cookies(cookies)
                .pathParam("accountId", id)


                .when()
                .delete("bank-accounts/{accountId}")

                .then()
                .statusCode(200)
        ;


    }

    @Test(dependsOnMethods = "deleteBankAccount")
    public void deleteBankAccountNegative() {

        given()
                .cookies(cookies)
                .pathParam("accountId", id)

                .when()
                .delete("bank-accounts/{accountId}")

                .then()
                .statusCode(400)

        ;


    }

    @Test(dependsOnMethods = "deleteBankAccountNegative")
    public void updateBankAccountNegative() {
        BankAccountInformation bai = new BankAccountInformation();
        bai.setActive(true);
        bai.setCurrency("TRY");
        bai.setIban("TR" + RandomInformation.randomNumber(20));
        bai.setId(id);
        bai.setIntegrationCode(RandomInformation.randomNumber(3));
        bai.setName(RandomInformation.randomName());
        bai.setSchoolId("6390f3207a3bcb6a7ac977f9");

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(bai)
                .log().body()


                .when()
                .put("bank-accounts")


                .then()
                .statusCode(400)


        ;


    }


    public class BankAccountInformation {

        private boolean active;
        private String currency;
        private String iban;
        private String id;
        private String integrationCode;
        private String name;
        private String schoolId;


        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }


        public String getIban() {
            return iban;
        }

        public void setIban(String iban) {
            this.iban = iban;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntegrationCode() {
            return integrationCode;
        }

        public void setIntegrationCode(String integrationCode) {
            this.integrationCode = integrationCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }
    }

}
