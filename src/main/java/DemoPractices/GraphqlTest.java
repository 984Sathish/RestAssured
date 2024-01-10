package DemoPractices;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GraphqlTest {

	
	@Test(enabled = false)
	public void graphQLTest() {
		
		//query
		int characterId = 2140;
		
		String queryResponse = given().log().all()
		.header("Content-Type", "application/json")
		.body("{\"query\":\"query($characterId: Int!)\\n{\\n  character(characterId: $characterId) {\\n    name\\n    gender\\n    status\\n    id\\n  }\\n  location(locationId:2508) {\\n    name\\n    id\\n    type\\n    dimension\\n  }\\n  characters(filters: {name: \\\"Rahul\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n    }\\n  }\\n  episodes(filters: {episode: \\\"Rahul\\\"}) {\\n    result {\\n      name\\n      id\\n      air_date\\n      episode\\n    }\\n  }\\n}\\n\",\"variables\":{\"characterId\":"+characterId+"}}")
		.when().post("https://rahulshettyacademy.com/gq/graphql")
		.then().extract().response().asString();
		
		System.out.println(queryResponse);
		JsonPath json = new JsonPath(queryResponse);
		String charName = json.getString("data.character.name");
		
		Assert.assertEquals(charName, "NewDom");
		
		
		//mutation
		
		String characterName = "DomWick";
		String episodeName = "FF12";
		
		String mutationResponse = given().log().all()
				.header("Content-Type", "application/json")
				.body("{\"query\":\"mutation($characterName: String!, $EpisodeName: String!)\\n{\\n  createCharacter(character: {name: $characterName, type: \\\"Student\\\", status: \\\"true\\\", species: \\\"true\\\", gender: \\\"male\\\", image: \\\"true\\\", originId: 2508, locationId: 2508}) {\\n    id\\n  }\\n  createEpisode(episode: {name: $EpisodeName, air_date: \\\"1234\\\", episode: \\\"23\\\"}) {\\n    id\\n  }\\n}\\n\",\"variables\":{\"characterName\":\""+characterName+"\",\"EpisodeName\":\""+episodeName+"\"}}")
				.when().post("https://rahulshettyacademy.com/gq/graphql")
				.then().extract().response().asString();
		
		JsonPath js = new JsonPath(mutationResponse);
		int charId = js.getInt("data.createCharacter.id");
		int episodeId = js.getInt("data.createEpisode.id");
		
		System.out.println("Character Id: "+characterId);
		System.out.println("Episode Id: "+episodeId);
	}
}
