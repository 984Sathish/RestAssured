package Resource;

public enum EnumClass {

	addPlaceApi("maps/api/place/add/json"),
	getPlaceApi("maps/api/place/get/json"),
	deletePlaceApi("maps/api/place/delete/json"),
	updatePlaceApi("maps/api/place/update/json");
	
	private String resource;
	
	EnumClass(String resource){
		this.resource = resource;
	}
	
	public String getResource() {
		return resource;
	}
	
}
