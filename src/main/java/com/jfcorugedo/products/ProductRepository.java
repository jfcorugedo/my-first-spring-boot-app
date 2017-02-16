package com.jfcorugedo.products;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

	public Product findByNameAndPriceGreaterThan(String name, double price);
}
