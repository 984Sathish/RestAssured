package DemoPractices;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class RestAPIWithExcel {

	@Test
	public void getExcelDateInAPI() throws IOException {
		
		ReadExcel excel = new ReadExcel();
		ArrayList<String> data=excel.getData("RestAddbook","RestAssured");
		
		
		HashMap<String, Object>  map = new HashMap<>();
		map.put("name", data.get(1));
		map.put("isbn", data.get(2));
		map.put("aisle", data.get(3));
		map.put("author", data.get(4));
		
	/*	HashMap<String, Object>  map2 = new HashMap<>();
		map.put("lat", "12");
		map.put("lng", "34");
		map.put("location", map2);*/
		

		   RestAssured.baseURI = "https://rahulshettyacademy.com";	
			String response = given()
					.queryParam("Content-Type", "application/json")
					.body(map)
					.when().post("Library/Addbook.php")
					.then().assertThat().log().all().statusCode(200).extract().response().asString();

			JsonPath js = new JsonPath(response);
			String id = js.getString("ID");
			System.out.println("Book Id: "+id);
		
		
	
		
	}
}
