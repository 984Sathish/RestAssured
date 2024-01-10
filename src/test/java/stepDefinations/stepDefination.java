package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import Resource.EnumClass;
import Resource.TestData;
import Resource.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class stepDefination extends Utils{

	 RequestSpecification req;
	 Response response;
	 TestData testData = new TestData();
	 static String place_id;
	 
	 @Given("Add Place payload with {string} {string} {string} {string}")
	 public void add_place_payload_with(String address, String phoneNumber, String name, 
			 										String language) throws IOException {	
		
	
		 req = given()
				.spec(getRequestSpecification())
				.body(testData.addPlacePayload(address, phoneNumber, name, language));
						
	}
	
	
	@When("Call {string} with {string} http request")
	public void call_with_http_request(String resource , String method) {
		
			//enum class constructor creation with 'resource' value.
			EnumClass enumRes = EnumClass.valueOf(resource);
			
			//get resource value
			String resourcePath = enumRes.getResource();
			
			if(method.equalsIgnoreCase("POST"))	
				response =  req.when().post(resourcePath);
			else if (method.equalsIgnoreCase("GET")) {
				
				response =  req.when().get(resourcePath);
			}
			
	}
	
	
	@Then("API call get status code {int}")
	public void api_call_get_status_code(Integer int1) {
		
		assertEquals(response.getStatusCode(), 200);
	}
	
	
	@Then("{string} of response body is {string}")
	public void of_response_body_is(String key, String value) {
		
		assertEquals(getJsonPath(response, key), value);	
	}
	
	@Then("Verify placeId created map to {string} using {string}")
	public void verify_place_id_created_map_to_using(String expectedName, String resource) throws IOException {
	    
		place_id = getJsonPath(response, "place_id");
		 req = given()
		.spec(getRequestSpecification())
		.queryParam("place_id", place_id);
		
		call_with_http_request(resource ,"GET");
		
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	}
	
	@Given("add delete place payload")
	public void add_delete_place_payload() throws IOException {
	   
		req = given()
		.spec(getRequestSpecification())
		.body(testData.deletePlacePayload(place_id));
	}

}
