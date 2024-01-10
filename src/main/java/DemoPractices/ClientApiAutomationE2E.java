package DemoPractices;

import PoojoIntegration.GetOrders;
import PoojoIntegration.LoginRequestPayload;
import PoojoIntegration.LoginResponse;
import PoojoIntegration.OrderDetails;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ClientApiAutomationE2E {

	@Test
	public void clientAPIEnd2End() {
		
		//get RequestSpecBuilder
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
		
		RequestSpecification loginBuilder = requestSpecBuilder
		.setBaseUri("https://rahulshettyacademy.com")
		.setContentType(ContentType.JSON).build();
		
		//RequestSpecification request = builder.build();
		
		//get login credentials from POJO class
		LoginRequestPayload loginCredentials = new LoginRequestPayload();
		loginCredentials.setUserEmail("sathishsuresh984@gmail.com");
		loginCredentials.setUserPassword("Satz@984");
		
		//******login to client application******
		LoginResponse loginResponse = given()
		.spec(loginBuilder)
		.body(loginCredentials)
		.when().post("api/ecom/auth/login")
		.then().extract().response().as(LoginResponse.class);
		
		//get token from response
		String token = loginResponse.getToken();
		String userId = loginResponse.getUserId();
		System.out.println("Token: "+token);
		System.out.println("userId: "+userId);
		
		RequestSpecification productBuilder = requestSpecBuilder
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.setContentType(ContentType.MULTIPART)
				.build();
		
		//*****create product*****
		
		RequestSpecification createProdReq = given().spec(productBuilder).log().all()
		//Add form-data by param() as a header
		.param("productName", "Laptop")
		.param("productAddedBy", userId)
		.param("productCategory", "Electronics")
		.param("productSubCategory", "Mobile")
		.param("productPrice", "23000")
		.param("productDescription", "Asus")
		.param("productFor", "Women")
		//add image file by multiPart()
		.multiPart("productImage", new File("C:/Users/sathish.suresh/Downloads/Laptop.png"));
		
		String createProductResponse = createProdReq.when().post("api/ecom/product/add-product")
		.then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = new JsonPath(createProductResponse);
		String productId = js.getString("productId");
		
		System.out.println("Created product id: "+productId);
		
		
		//******Order product******
		RequestSpecification orderBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
		.addHeader("Authorization", token)
		.setContentType(ContentType.JSON).build();
		
		//set country and id by using pojo class
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		
		//create list to pass order details
		List<OrderDetails> Orderlist = new ArrayList<OrderDetails>();
		Orderlist.add(orderDetails);
		
		//call the pojo class
		GetOrders getOrder = new GetOrders();
		getOrder.setOrders(Orderlist);
		
		RequestSpecification createProductReq = given()
		.spec(orderBuilder)
		.body(getOrder).log().all();
		
		String orderResponse = createProductReq.when().post("api/ecom/order/create-order")
		.then().assertThat().statusCode(201)
		.log().all()
		.extract().response().asString();
		
		JsonPath json = new JsonPath(orderResponse);
		String orderId =  json.getString("orders[0]");
		
		
		//Get order details
		RequestSpecification getorderBuilder = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token)
				.build();
		
		given()
		.spec(getorderBuilder)
		.queryParam("id", orderId).log().all()
		.when().get("api/ecom/order/get-orders-details")
		.then().log().all().assertThat().statusCode(200).extract().response();
		
		
		//create response spec builder
		ResponseSpecification deleteProdRes = new ResponseSpecBuilder().expectStatusCode(200).build();
		
		
		//delete product
		given()
		.spec(getorderBuilder)
		.pathParam("productId", productId)
		.when().delete("api/ecom/product/delete-product/{productId}")
		.then().log().all().spec(deleteProdRes)
		.extract().response();
		
		
		
	}
}
