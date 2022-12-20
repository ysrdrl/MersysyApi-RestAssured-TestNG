package _02_Setup.Parameters;

import _01_Login.Login;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import zz_Extra.RandomInformation;

import static io.restassured.RestAssured.*;

public class DocumentTypes extends Login {

    String id;

    @Test
    public void createDocumentTypes() {

        DocumentTypesInformation dti = new DocumentTypesInformation();
        String[] dizi = {"STUDENT_REGISTRATION", "EXAMINATION"};

        dti.setActive(false);
        dti.setAttachmentStages(dizi);
        dti.setDescription(RandomInformation.randomName() + "deneme");
        dti.setName(RandomInformation.randomName());
        dti.setRequired(false);
        dti.setSchoolId("6390f3207a3bcb6a7ac977f9");
        dti.setUseCamere(true);

        id =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(dti)
                        .log().body()


                        .when()
                        .post("attachments")


                        .then()
                        .statusCode(201)
                        .extract().path("id")

        ;


    }


    String name;
    String[] dizi = {"STUDENT_REGISTRATION", "EXAMINATION"};
    @Test(dependsOnMethods = "createDocumentTypes")
    public void updateDocumentTypes() {

        DocumentTypesInformation dti = new DocumentTypesInformation();


        dti.setActive(true);
        dti.setAttachmentStages(dizi);
        dti.setDescription(RandomInformation.randomName() + "deneme");
        dti.setName(RandomInformation.randomName());
        dti.setRequired(true);
        dti.setSchoolId("6390f3207a3bcb6a7ac977f9");
        dti.setUseCamere(false);
        dti.setId(id);

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .log().body()
                .body(dti)

                .when()
                .put("attachments")



                .then()
                .statusCode(200)
                ;


    }

    @Test(dependsOnMethods = "updateDocumentTypes")
    public void deleteDocumentTypes() {


        given()
                .cookies(cookies)
                .pathParam("documentId",id)

                .when()
                .delete("attachments/{documentId}")


                .then()
                .statusCode(200)
                ;




    }

    @Test(dependsOnMethods = "deleteDocumentTypes")
    public void deleteDocumentTypesNegative() {


        given()
                .cookies(cookies)
                .pathParam("documentId",id)

                .when()
                .delete("attachments/{documentId}")


                .then()
                .statusCode(400)
        ;




    }


    class DocumentTypesInformation {

        private boolean active;
        private String[] attachmentStages;
        private String description;
        private String name;
        private boolean required;
        private String schoolId;
        private String[] translateName;
        private boolean useCamere;

        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isUseCamere() {
            return useCamere;
        }

        public void setUseCamere(boolean useCamere) {
            this.useCamere = useCamere;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String[] getAttachmentStages() {
            return attachmentStages;
        }

        public void setAttachmentStages(String[] attachmentStages) {
            this.attachmentStages = attachmentStages;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isRequired() {
            return required;
        }

        public void setRequired(boolean required) {
            this.required = required;
        }

        public String getSchoolId() {
            return schoolId;
        }

        public void setSchoolId(String schoolId) {
            this.schoolId = schoolId;
        }

        public String[] getTranslateName() {
            return translateName;
        }

        public void setTranslateName(String[] translateName) {
            this.translateName = translateName;
        }
    }
}

