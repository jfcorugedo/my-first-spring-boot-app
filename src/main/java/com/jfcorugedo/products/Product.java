package com.jfcorugedo.products;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain=true)
public class Product {

	private String id;
	private String name;
	private Double price;
		
}
