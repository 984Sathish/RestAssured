package DemoPractices;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.annotations.Test;

import DataFile.PayLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class HashMapInteg {
	
	@Test
	public void hashMapIntegration() {
		
		
			//create hashmap
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("name", "Learn Appium Automation with Java");
			hashMap.put("isbn", "abcd");
			hashMap.put("aisle", "1234");
			hashMap.put("author", "johnDom");
		

			RestAssured.baseURI = "https://rahulshettyacademy.com";	
			String response = given()
					.queryParam("Content-Type", "application/json")
					.body(hashMap).log().all()
					.when().post("Library/Addbook.php")
					.then().assertThat().log().all().statusCode(200).extract().response().asString();

			JsonPath js = new JsonPath(response);
			String id = js.getString("ID");
			System.out.println("Book Id: "+id);
		
	}

}
