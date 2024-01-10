package Resource;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {

	public static RequestSpecification reqSpec;
	public RequestSpecification getRequestSpecification() throws IOException {
		
		if(reqSpec == null) {
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		 reqSpec = new RequestSpecBuilder()
				.setBaseUri(getGlobalProperty("baseUrl"))
				.addQueryParam("key", "qaclick123")
				
				//request filter
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				//response filter
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				
				.setContentType(ContentType.JSON).build();
		 return reqSpec;
		}
		 return reqSpec;
	}
	
	public String getGlobalProperty(String key) throws IOException {
		Properties property = new Properties();
		//input to golbal property file
		FileInputStream fileInput = new FileInputStream(
				"C:\\Users\\sathish.suresh\\eclipse-workspace\\RestApiFramwork\\src\\test\\java\\Resource\\global.properties"
				);
		property.load(fileInput);
		
		return property.getProperty(key);	
				
	}
	
	public String getJsonPath(Response response, String key) {
		
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		return js.get(key).toString();
	}
	
	
}
