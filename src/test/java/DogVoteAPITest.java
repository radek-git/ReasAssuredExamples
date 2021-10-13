import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import votes.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class DogVoteAPITest {

    private final String header = "x-api-key";
    private final String api_key = "0486514b-5c09-4a38-905a-6421d86ddf0c";
    private int firstVoteId;
    private int secondVoteId;


    @Test(priority = 0)
    public void shouldGetEmptyListOfVotes() {
        List list = given().header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/votes")
                .then()
                .extract().as(List.class);

        assertTrue(list.isEmpty());
    }

    @Test(priority = 1)
    public void shouldCreateFirstVote() {
        CreateVoteRequest request = new CreateVoteRequest("qwerty", "sub_id1", 6);
        CreateVoteResponse response = given().contentType(ContentType.JSON)
                .header(header, api_key)
                .body(request)
                .when()
                .post("https://api.thedogapi.com/v1/votes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(CreateVoteResponse.class);

        assertEquals(response.getMessage(), "SUCCESS");
        firstVoteId = response.getId();
    }

    @Test(priority = 1)
    public void shouldNotCreateFirstVoteWhenApiKeyIsIncorrect() {
        CreateVoteRequest request = new CreateVoteRequest("qwerty", "sub_id1", 6);
        CreateVoteResponse response = given().contentType(ContentType.JSON)
                .header(header, "dupa")
                .body(request)
                .when()
                .post("https://api.thedogapi.com/v1/votes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(CreateVoteResponse.class);

        System.out.println(response.getMessage());
    }

    @Test(priority = 2)
    public void numberOfVotesShouldEqual1() {
        List list = given().header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/votes")
                .then()
                .extract().as(List.class);

        assertEquals(list.size(), 1);
    }

    @Test(priority = 3)
    public void shouldCreateSecondVote() {
        CreateVoteRequest request = new CreateVoteRequest("asdfg", "qwer", 3);
        CreateVoteResponse response = given().contentType(ContentType.JSON)
                .header(header, api_key)
                .body(request)
                .when()
                .post("https://api.thedogapi.com/v1/votes")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract().as(CreateVoteResponse.class);

        assertEquals(response.getMessage(), "SUCCESS");
        secondVoteId = response.getId();
    }

    @Test(priority = 4)
    public void shouldFindVoteById() {
        GetVoteByIdRequest request = new GetVoteByIdRequest(String.valueOf(firstVoteId));
        GetVoteByIdResponse response = given().contentType(ContentType.JSON.getAcceptHeader())
                .header(header, api_key)
//                .body(request)
                .when()
                .get("https://api.thedogapi.com/v1/votes/" + firstVoteId)
                .then()
                .extract().as(GetVoteByIdResponse.class);

        assertEquals(response.getId(), firstVoteId);
    }


    @Test(priority = 5)
    public void shouldDeleteFirstVoteById() {
        given().header(header, api_key)
                .when()
                .delete("https://api.thedogapi.com/v1/votes/" + firstVoteId)
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test(priority = 6)
    public void shouldDeleteSecondVoteById() {
        given().header(header, api_key)
                .when()
                .delete("https://api.thedogapi.com/v1/votes/" + secondVoteId)
                .then().statusCode(HttpStatus.SC_OK);
    }

    @Test(priority = 7)
    public void shouldConfirmThatAllVotesAreDeleted() {
        List list = given().header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/votes")
                .then()
                .extract().as(List.class);

        assertTrue(list.isEmpty());

    }


}
