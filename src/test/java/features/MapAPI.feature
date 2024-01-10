Feature: Validating Map API's

@AddPlace
Scenario Outline: Verify if place is created using AddPlaceAPI


	Given Add Place payload with "<Address>" "<PhoneNumber>" "<name>" "<language>"
	When Call "addPlaceApi" with "POST" http request
	Then API call get status code 200
	And "status" of response body is "OK"
	Then Verify placeId created map to "<name>" using "getPlaceApi"

	Examples:
	
	| 				Address 					| PhoneNumber 			| name						| language 	|
	| 29, side layout, cohen 09	| (+91) 983 893 3937| Frontline house	| French-IN |
	| 39 March center, jain			| (+91) 403 733 937 | Backlight house	| Englist 	|
	
	@DeletePlace
	Scenario: Verify if place is deleted using deletePlaceAPI
	
	Given add delete place payload
	When Call "deletePlaceApi" with "POST" http request
	Then API call get status code 200
	And "status" of response body is "OK"	