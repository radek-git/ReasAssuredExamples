import favourites.DeleteImageByFavouriteIdResponse;
import favourites.GetListOfFavouritesResponse;
import favourites.SaveImageAsFavouriteRequest;
import favourites.SaveImageAsFavouriteResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class DogFavouritesAPITest {

    private final String header = "x-api-key";
    private final String api_key = "0486514b-5c09-4a38-905a-6421d86ddf0c";
    private final String firstImage = "_LWIQOpXG";
    private final String secondImage = "bfTfztTZt";
    private int firstFavouriteId;
    private int secondFavouriteId;



    @Test(priority = 0)
    public void shouldGetEmptyListOfFavourites() {
        GetListOfFavouritesResponse[] responses = given().header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/favourites")
                .then()
                .extract()
                .as(GetListOfFavouritesResponse[].class);

        assertEquals(responses.length, 0);
    }

    @Test(priority = 1)
    public void shouldPostFirstImageToFavouritesList() {
        // https://api.thedogapi.com/v1/images/search - wyszukiwanie id które można wysłać
        SaveImageAsFavouriteRequest request = new SaveImageAsFavouriteRequest(firstImage, "user_dupa");
        SaveImageAsFavouriteResponse response = given()
                .header(header, api_key)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://api.thedogapi.com/v1/favourites")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(SaveImageAsFavouriteResponse.class);

        assertEquals(response.getMessage(), "SUCCESS");
        firstFavouriteId = response.getId();
    }

    @Test(priority = 2)
    public void shouldReturnStatus400WhenFirstImageIsSentAgain() {
        // https://api.thedogapi.com/v1/images/search - wyszukiwanie id które można wysłać
        SaveImageAsFavouriteRequest request = new SaveImageAsFavouriteRequest(firstImage, "user_dupa");
        Response response = given()
                .header(header, api_key)
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("https://api.thedogapi.com/v1/favourites");

        assertEquals(response.getStatusCode(), 400);
    }


    @Test(priority = 3)
    public void shouldPostSecondImageToFavourites() {
        SaveImageAsFavouriteRequest request = new SaveImageAsFavouriteRequest(secondImage, "user_cosstam");

        SaveImageAsFavouriteResponse response = given().contentType(ContentType.JSON)
                .header(header, api_key)
                .body(request)
                .when()
                .post("https://api.thedogapi.com/v1/favourites")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(SaveImageAsFavouriteResponse.class);

        assertEquals(response.getMessage(), "SUCCESS");
        secondFavouriteId = response.getId();
    }


    @Test(priority = 4)
    public void shouldReturnStatus400WhenSecondImageIsSentAgain() {
        SaveImageAsFavouriteRequest request = new SaveImageAsFavouriteRequest(secondImage, "user_cosstam");

        Response response = given().contentType(ContentType.JSON)
                .header(header, api_key)
                .body(request)
                .when()
                .post("https://api.thedogapi.com/v1/favourites");

        assertEquals(response.getStatusCode(), 400);
    }


    @Test(priority = 5)
    public void shouldGetListOfFavourites() {
        GetListOfFavouritesResponse[] responses = given().header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/favourites")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(GetListOfFavouritesResponse[].class);

        assertEquals(responses.length, 2);
    }

    @Test(priority = 6)
    public void shouldDeleteFirstImageFromFavourites() {
        System.out.println(firstFavouriteId);
        DeleteImageByFavouriteIdResponse response = given().header(header, api_key)
                .when()
                .delete("https://api.thedogapi.com/v1/favourites/" + firstFavouriteId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract()
                .as(DeleteImageByFavouriteIdResponse.class);

        assertEquals(response.getMessage(), "SUCCESS");
    }

    @Test(priority = 7)
    public void shouldCheckIfFirstElementExistsInFavourites() {
        given().header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/favourites/" + firstFavouriteId)
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test(priority = 8)
    public void shouldDeleteSecondImageFromFavourites() {
        DeleteImageByFavouriteIdResponse response = given().header(header, api_key)
                .when()
                .delete("https://api.thedogapi.com/v1/favourites/" + secondFavouriteId)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract().as(DeleteImageByFavouriteIdResponse.class);

        assertEquals(response.getMessage(), "SUCCESS");
    }




}
