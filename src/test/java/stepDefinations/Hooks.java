package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		
		stepDefination stepDef = new stepDefination();
		stepDef.add_place_payload_with("New street", "9129303012", "Sideline house", "Tamil");
		
		stepDef.call_with_http_request("addPlaceApi", "POST");
		stepDef.verify_place_id_created_map_to_using("Sideline house","getPlaceApi");
		
	}

}
