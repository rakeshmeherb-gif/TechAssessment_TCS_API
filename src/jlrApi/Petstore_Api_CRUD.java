package jlrApi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Petstore_Api_CRUD {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("** Trying to automate Petstore API using all the methods - CRUD");

		Thread.sleep(5000);
		//***POST METHOD
		//Added REST ASSURED Dependency to Initializing Base URI
		RestAssured.baseURI = "https://petstore.swagger.io";

		//To access static given(), when(), then(), methods import >> import static io.restassured.RestAssured.*;
		//Getting all the inputs
		String response = given().log().all().header("accept", "application/json").header("Content-Type", "application/json").body("{\r\n"
				+ "  \"id\": 0,\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 0,\r\n"
				+ "    \"name\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \"PetName-Meher\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"string\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"available\"\r\n"
				+ "}")

				//Selecting the method with resource
				.when().post("/v2/pet")

				//Validating the response
				.then().log().all().assertThat().statusCode(200)

				//Added HAMCREST Dependency to Compare Pet_Name in Response
				.body("name", equalTo("PetName-Meher")).extract().response().asString();
		System.out.println("*** POST Method is successful and Pet Name got matched\n");	
		Thread.sleep(9000);

		//***GET METHOD
		//Parsing Json to get Pet_id
		JsonPath jpath = new JsonPath(response);
		Long getPetId = jpath.get("id");
		System.out.println(getPetId);

		given().log().all().header("accept", "application/json").when().get("/v2/pet/"+getPetId).then().log().all().assertThat().statusCode(200)
		.body("name", equalTo("PetName-Meher"));

		System.out.println("*** GET Method is successful and Pet Name got matched\n");
		Thread.sleep(5000);

		//***PUT METHOD
		//Updating pet name or other details with Pet_Id 		
		String UpdatePetName = "doggie123";
		String updatedResponse = given().log().all().header("accept", "application/json").header("Content-Type","application/json").body("{\r\n"
				+ "  \"id\": "+getPetId+",\r\n"
				+ "  \"category\": {\r\n"
				+ "    \"id\": 0,\r\n"
				+ "    \"name\": \"string\"\r\n"
				+ "  },\r\n"
				+ "  \"name\": \""+UpdatePetName+"\",\r\n"
				+ "  \"photoUrls\": [\r\n"
				+ "    \"string\"\r\n"
				+ "  ],\r\n"
				+ "  \"tags\": [\r\n"
				+ "    {\r\n"
				+ "      \"id\": 0,\r\n"
				+ "      \"name\": \"string\"\r\n"
				+ "    }\r\n"
				+ "  ],\r\n"
				+ "  \"status\": \"available\"\r\n"
				+ "}")
				.when().put("/v2/pet")
				.then().log().all().assertThat().statusCode(200).body("name", equalTo("doggie123")).extract().response().asString();

		JsonPath update = new JsonPath(updatedResponse);
		String actual_value = update.getString("name");

		assertEquals(actual_value, UpdatePetName);		

		System.out.println("*** PUT Method is successful and Pet Name got matched\n");
		Thread.sleep(5000);

		//***DELETE METHOD
		//Deleting pet name details with Pet_id
		given().header("accept", "application/json")
		.when().delete("/v2/pet/"+getPetId)
		.then().assertThat().statusCode(200);
		Thread.sleep(5000);

		//***GET METHOD
		//Confirming the deleted pet details
		given().log().all().header("accept", "application/json").when().get("/v2/pet/"+getPetId).then().log().all().assertThat().statusCode(404)
		.body("message", equalTo("Pet not found"));

		System.out.println("*** DELETE Method is successful and Pet Name got deleted\n");		

	}

}
