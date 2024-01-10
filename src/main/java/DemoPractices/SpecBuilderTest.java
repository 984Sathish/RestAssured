package DemoPractices;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import DataFile.PayLoad;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderTest {

	@Test
	public void specBuild() {

		/*Note:
		 * request and response spec builder is used for add the repeated action.
		 * And use it in rest assured code.
		 * ex: spec()  -> it used to get the req or res builder.
		 */

		//request spec builder 
		RequestSpecification requestSpecBuilder = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();

		//response spec builder
		ResponseSpecification responseSpecBuilder = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		//Create Place

		//request specification
		RequestSpecification res= given().spec((requestSpecBuilder))
				.log().all()
				.body(PayLoad.addPlacePayload());

		String response = res.when().post("maps/api/place/add/json")
				.then().spec((responseSpecBuilder) )

				.body("scope", equalTo("APP"))
				.extract().response().asString();

		JsonPath js = new JsonPath(response);

		String placeId = js.getString("place_id");
		System.out.println("Place Id : "+placeId);
	}

}
