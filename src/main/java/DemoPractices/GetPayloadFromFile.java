package DemoPractices;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import DataFile.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class GetPayloadFromFile {

	@Test
	public void addPlace() throws IOException {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		//Create Place
		String response = 
				given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				//get pay load from external file
				.body( new String( Files.readAllBytes(Paths.get("C:\\Users\\sathish.suresh\\Documents\\AddPlaceBody.txt"))))

				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200)
				.body("scope", equalTo("APP"))
				.extract().response().asString();

		JsonPath js = new JsonPath(response);

		String placeId = js.getString("place_id");
		System.out.println("Place Id : "+placeId);

	}
}
