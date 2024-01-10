package DemoPractices;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Arrays;

import javax.tools.DocumentationTool.Location;

import org.testng.annotations.Test;

import DataFile.PayLoad;
import PoojoIntegration.GetPlace;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class MapApiWithSerialization {


	@Test
	public void mapSerialization()  {

		GetPlace getPlace = new GetPlace();
		
		getPlace.setAccuracy(50); 
		getPlace.setAddress("29, side layout, cohen 09"); 
		getPlace.setPhone_number("(+91) 983 893 3937");
		getPlace.setName("Frontline house");
		getPlace.setWebsite("http://google.com");
		getPlace.setLanguage("French-IN");
		//create list with value and send as parameter
		getPlace.setTypes(Arrays.asList("shoe park","shop"));
		
		//set lat and lng using location class object 
		PoojoIntegration.Location loc = new PoojoIntegration.Location();
		loc.setLat("-38.383494");
		loc.setLng("33.427362");
		
		//set location by pass object
		getPlace.setLocation(loc);
		
		
		//Create Place
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(getPlace)
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200)
				.body("scope", equalTo("APP"))
				.extract().response().asString();

		JsonPath js = new JsonPath(response);

		String placeId = js.getString("place_id");
		System.out.println("Place Id : "+placeId);



	}
}
