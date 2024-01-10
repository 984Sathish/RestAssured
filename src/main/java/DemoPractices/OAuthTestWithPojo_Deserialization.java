package DemoPractices;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.Test;

import PoojoIntegration.GetCourses;
import PoojoIntegration.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class OAuthTestWithPojo_Deserialization {

	@Test
	public void OAuthWithDeserialize() throws InterruptedException {

		//To get this url, u can navigate "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		//Note: In automation we can manullay login to google but that will not longer support by google. So we manually navigate to above url and get redirected url to get token.
		//After loading the url - It will load new url( Show nothing in UI)
		//Copy the url and paste it.
		
		
//		Scanner sc = new Scanner(System.in);
//		System.out.println("Enter the url for authentication: ");
//		String url = sc.next();
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXnfck8pgfIXH8ZGcuhaS93yzO0Rk5Jp5g2ZbXguT8Xc2YXTLttd1_ysgMCeyQ5NGQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
		String AuthCode = url.split("code=")[1].split("&scope")[0];
		System.out.println(AuthCode);


		//get access token
		String response = given().urlEncodingEnabled(false)
				.queryParam("code", AuthCode)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code")
				.when().post("https://www.googleapis.com/oauth2/v4/token")
				.then().log().all().extract().response().asString();

		JsonPath json = new JsonPath(response);

		String accessToken = json.getString("access_token");
		System.out.println("Access Token : "+accessToken);


		//get course
		GetCourses getCourse = given()
		.queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourses.class);
		
		
		/*Note:
		1.expect().defaultParser(Parser.JSON) -> 
						It used to REST will expect Json format respone. 
		
		2.as(GetCourses.class)->
				Used to get the response and send to this class.
		*/
		
		System.out.println("Instructor: "+getCourse.getInstructor()); 
		System.out.println("Expertise: "+getCourse.getExpertise()); 
		System.out.println("LinkedIn: "+getCourse.getLinkedIn()); 
		System.out.println("Services: "+getCourse.getServices()); 
		System.out.println("Url: "+getCourse.getUrl()); 
		
		//get array format response
		String courseTitleWeb = getCourse.getCourses().getWebAutomation().get(0).getCourseTitle();
		System.out.println("First course in WebAutomation: "+courseTitleWeb);
		
		String courseTitleApi = getCourse.getCourses().getApi().get(0).getCourseTitle();
		System.out.println("First course in API: "+courseTitleApi);
		
		String courseTitleMob = getCourse.getCourses().getMobile().get(0).getCourseTitle();
		System.out.println("First course in Mobile: "+courseTitleMob);
		
		//Assertion
		
		//String[] expectedWebCourse = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
		ArrayList<String> expectedWebCourse = new ArrayList<String>
					(Arrays.asList("Selenium Webdriver Java", "Cypress", "Protractor"));
		
		
		List<WebAutomation> webAutomationList = getCourse.getCourses().getWebAutomation();
		ArrayList<String> webAutomationCourselist = new ArrayList<String>();
		
		for (int i = 0; i < webAutomationList.size(); i++) {
			webAutomationCourselist.add(getCourse.getCourses().getWebAutomation().get(i).getCourseTitle());
		}
		
		System.out.println("Actual web course: "+webAutomationCourselist);
		System.out.println("Expected web course: "+expectedWebCourse);
		
		Assert.assertTrue(expectedWebCourse.equals(webAutomationCourselist));
		
		






	}

}
