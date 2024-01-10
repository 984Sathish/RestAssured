package DemoPractices;

import org.testng.Assert;
import org.testng.annotations.Test;

import DataFile.PayLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPractice {

	@Test
	public void getComplexJson() {

		JsonPath json = new JsonPath(PayLoad.coursePrice());

		int numOfCourse = json.getInt("courses.size()");

		System.out.println("Number of Course: "+numOfCourse);

		int purchaseAmount = json.getInt("dashboard.purchaseAmount");

		System.out.println("Purchase Amount: "+purchaseAmount);

		String firstCourse = json.get("courses[0].title");

		System.out.println("First Course Title: "+firstCourse);	

		for (int i = 0; i < numOfCourse; i++) {

			String title = json.get("courses["+i+"].title");
			System.out.println("Course Title: "+title);

			int price = json.get("courses["+i+"].price");
			System.out.println("Course Price: "+price);

		}

		for (int i = 0; i < numOfCourse; i++) {

			String title = json.get("courses["+i+"].title");
			if(title.equals("RPA")) {
				int copies = json.get("courses["+i+"].copies");
				System.out.println("Number of Copies of 'RPA': "+copies);
				break;
			}
		}

		int totalAmount = 0;
		for (int i = 0; i < numOfCourse; i++) {

			int price = json.getInt("courses["+i+"].price");
			int copies = json.getInt("courses["+i+"].copies");
			int amount = price * copies;
			totalAmount = totalAmount + amount;
		}

		System.out.println("Total sum amount: "+totalAmount);

		int purchseAmount = json.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase amount: "+purchseAmount);

		//Assertion
		Assert.assertEquals(purchseAmount, totalAmount);

	}

}
