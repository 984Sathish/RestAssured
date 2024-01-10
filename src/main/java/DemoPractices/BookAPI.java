package DemoPractices;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import DataFile.PayLoad;

public class BookAPI {


	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";	
		String response = given()
				.queryParam("Content-Type", "application/json")
				.body(PayLoad.addBookPayload(isbn, aisle))
				.when().post("Library/Addbook.php")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();

		JsonPath js = new JsonPath(response);
		String id = js.getString("ID");
		System.out.println("Book Id: "+id);
	}

	//Data provider 
	@DataProvider(name = "BookData")
	public Object[][] getData() {

		return new Object[][] {
			{"bcd", "2926"},
			{"acb", "3401"},
			{"xyz", "1340"},
			{"acb", "2763"}
		};

	}



}

