package DemoPractices;

import static io.restassured.RestAssured.given;

import java.util.Scanner;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

public class OAuthTest {

	@Test
	public void authenticationOAuth() throws InterruptedException {


		/*WebDriver driver;

		  //code for create instance of chrome driver using Web driver manager
		  WebDriverManager.chromedriver().setup();

		  //create new chrome driver instancce 
		  driver = new ChromeDriver();



		  driver.get(
		  "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		  ); driver.findElement(By.id("identifierId")).sendKeys(
		  "sathishsuresh984@gmail.com");
		  driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);

		  driver.findElement(By.cssSelector("[type='password']")).sendKeys("09840894");
		  driver.findElement(By.cssSelector("[type='password']")).sendKeys(Keys.ENTER);

		  Thread.sleep(3000); String url = driver.getCurrentUrl();


		 */
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the url for authentication: ");
		String url = sc.next();
		
		//String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AfJohXkaklhkjiwxc_Dd2Pmz-c-i6mgPcKXDVRd2NxVVgHsDqogNjoT28w_fOEspWSl7og&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
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
		given()
		.queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().assertThat().statusCode(200)
		.log().all().extract();
		
	}

}
