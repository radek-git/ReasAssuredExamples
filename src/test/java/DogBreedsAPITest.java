import breeds.GetBreedByNameRequest;
import breeds.GetListOfBreedsResponse;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class DogBreedsAPITest {

    private final String header = "x-api-key";
    private final String api_key = "0486514b-5c09-4a38-905a-6421d86ddf0c";


    @Test
    public void shouldReturnNumberOfBreeds() {
        List list = given().header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/breeds")
                .then()
                .extract()
                .as(List.class);

        assertEquals(list.size(), 172);
    }

    @Test
    public void shouldGetBreedByName() {
        GetBreedByNameRequest request = new GetBreedByNameRequest("Affenpinscher");
        GetListOfBreedsResponse response = given()
                .contentType(ContentType.JSON)
                .header(header, api_key)
                .when()
                .get("https://api.thedogapi.com/v1/breeds/search?q" + "=air")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .extract().as(GetListOfBreedsResponse.class);

        System.out.println(response.getId());

    }


}
