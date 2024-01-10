package DemoPractices;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import DataFile.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


public class MapAPIs {

	
	@Test
	public void mapAPI() {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		//given - all input data
		//when - submit API
		//Then - validate the response

		//Create Place
		String response = given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(PayLoad.addPlacePayload())
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200)
				.body("scope", equalTo("APP"))
				.extract().response().asString();

		JsonPath js = new JsonPath(response);

		String placeId = js.getString("place_id");
		System.out.println("Place Id : "+placeId);


		//Update Place
		String newAddress = "70 Summer walk, USA";
		given().queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));


		//get place
		String getPlaceResponse = given()
				.queryParam("key", "qaclick123")
				.queryParam("place_id", placeId).log().all()
				.when().get("maps/api/place/get/json")
				.then().assertThat().statusCode(200).extract().response().asString().toString();


		System.out.println(getPlaceResponse);
		JsonPath js1 = new JsonPath(getPlaceResponse);
		String actualAddress = js1.getString("address");
		System.out.println("New address : "+actualAddress);

		Assert.assertEquals(actualAddress, newAddress);
	}


}
