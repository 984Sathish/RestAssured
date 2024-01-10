package DemoPractices;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraAPI {

	public static void main(String[] args) {

		
		RestAssured.baseURI = "http://localhost:8080";

		//created session - it add the cookies details
		SessionFilter session =  new SessionFilter(); 

		//authorization
		given()
		.header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"username\": \"sathishsuresh984\",\r\n"
				+ "    \"password\": \"Satz@984\"\r\n"
				+ "}")
		.filter(session).when().post("rest/auth/1/session")
		.then().log().all().extract().response().asString();
		
		//create issue
		String issueResponse = given()
		.header("Content-Type", "application/json")
		.filter(session)
		.body("{\r\n"
				+ "    \"fields\": {\r\n"
				+ "        \"project\": \r\n"
				+ "        {\r\n"
				+ "            \"key\": \"REST\"\r\n"
				+ "        },\r\n"
				+ "        \"summary\": \"New defect summary3\",\r\n"
				+ "        \"description\": \"New defect description\",\r\n"
				+ "        \"issuetype\": {\r\n"
				+ "            \"name\": \"Bug\"\r\n"
				+ "        }\r\n"
				+ "        \r\n"
				+ "    }\r\n"
				+ "}   ")
		.when().post("rest/api/2/issue")
		.then().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(issueResponse);
		String id = js.getString("id");

		//Add new comment

		String comment = "Automation comment of REST";
		String commentResponse = given()
				.header("Content-Type", "application/json")
				.pathParam("key", id)
				.body("{\r\n"
						+ "    \"body\": \""+ comment +"\",\r\n"
						+ "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n"
						+ "        \"value\": \"Administrators\"\r\n"
						+ "    }\r\n"
						+ "}")
				.filter(session)
				.when().post("rest/api/2/issue/{key}/comment")
				.then().log().all().assertThat().statusCode(201).extract().response().asString();

		JsonPath js1 = new JsonPath(commentResponse);
		String newCommentId = js1.getString("id");

		//Add attachment to the issue
		given()
		.header("X-Atlassian-Token", "no-check")
		.header("Content-Type", "multipart/form-data")
		.pathParam("key", id)
		.filter(session)
		//Attach the file here -->
		.multiPart("file", new File("jiraFile.txt"))
		.when().post("rest/api/2/issue/{key}/attachments")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();

		//get issue
		String response = given()
				//get specified response by giving query parameter	

				.queryParam("fields", "comment")		
				.pathParam("key", id)
				.filter(session)
				.when().get("rest/api/2/issue/{key}")
				.then().log().all().extract().response().asString();

		System.out.println(response);

		JsonPath json = new JsonPath(response);

		int commentCount = json.getInt("fields.comment.comments.size()");
		System.out.println(commentCount);

		for (int i = 0; i < commentCount; i++) {

			//get comment id
			String commentId = json.get("fields.comment.comments["+i+"].id").toString();

			//System.out.println(commentId);
			
			if(commentId.equals(newCommentId) ) {
				System.out.println("expect: "+newCommentId);
				System.out.println("Actual: "+commentId);
				//get comment body
				String commentBody = json.getString("fields.comment.comments["+i+"].body");

				//Assertion
				Assert.assertEquals(commentBody, comment);

			}
		}


		//Note:
		
		//given().relaxedHTTPSValidation()..other same..

		//relaxedHTTPSValidation() - It is used to bypass https site.

	
	
	
	}
	
	



}
