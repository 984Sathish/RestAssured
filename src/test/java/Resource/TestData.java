package Resource;

import java.util.Arrays;

import Framework.Pojo.GetPlace;
import Framework.Pojo.Location;

public class TestData {

	public GetPlace addPlacePayload(String address, String phoneNumber, String name, String language) {
		
		GetPlace getPlace = new GetPlace();

		getPlace.setAccuracy(50);
		getPlace.setAddress(address); 
		getPlace.setPhone_number(phoneNumber);
		getPlace.setName(name);
		getPlace.setWebsite("http://google.com");
		getPlace.setLanguage(language);
		//create list with value and send as parameter
		getPlace.setTypes(Arrays.asList("shoe park","shop"));

		//set lat and lng using location class object 
		Location loc = new Location();
		loc.setLat("-38.383494");
		loc.setLng("33.427362");
		
		//set location by pass object
		getPlace.setLocation(loc);
		
		return getPlace;

	}
	
	public String deletePlacePayload(String placeId)
	{
		return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}";
	}
}
