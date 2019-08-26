package com.rodrigo.gdc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class product {
	
	@Id
	private Integer Id;
	
	@Column(name = "Name", length = 45)
	private String Name;
	
	@Column(name = "Price", length = 8)
	private int Price;
	
	@Column(name = "Stock", length = 8)
	private int Stock;
	 
    @Column(name = "Category")
	private int Category;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public int getCategory() {
		return Category;
	}

	public void setCategory(int category) {
		Category = category;
	} 

    

	
	
}
