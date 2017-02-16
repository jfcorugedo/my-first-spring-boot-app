package com.jfcorugedo.products;

import static com.lordofthejars.nosqlunit.mongodb.MongoDbRule.MongoDbRuleBuilder.newMongoDbRule;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.lordofthejars.nosqlunit.annotation.UsingDataSet;
import com.lordofthejars.nosqlunit.core.LoadStrategyEnum;
import com.lordofthejars.nosqlunit.mongodb.MongoDbRule;

import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductsControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(ProductsController.class);

	@Autowired
	private ApplicationContext applicationContext;
	
	@Rule
	public MongoDbRule mongoDbRule = newMongoDbRule().defaultSpringMongoDb("test");
	
	@LocalServerPort
	private int port;
	
	private String endpoint;
	
	@Before
	public void setUp() {
		this.endpoint = String.format("http://localhost:%d/v1/products", port);
	}
	
	@Test
	public void create() {
		
		given().
			contentType(ContentType.JSON).
			body(new Product().setName("iPhone 7").setPrice(700.0)).
		when().
			post(endpoint).
		then().
			statusCode(201).
			body("id", notNullValue()).
			body("name", equalTo("iPhone 7")).
			body("price", equalTo(700.0f));
			
	}
	
	@Test
	@UsingDataSet(locations="initialData.json", loadStrategy=LoadStrategyEnum.CLEAN_INSERT)
	public void getAll() {
		
		given().
			contentType(ContentType.JSON).
		when().
			get(endpoint).
		then().
			statusCode(200).
			body("id", notNullValue()).
			body("name", contains(" ", "Product1", "Product2")).
			body("price", contains(0f, 100.0f, 200.0f));
	}
}
