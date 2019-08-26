package com.rodrigo.gdc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class purchase {
	@Id
	private Integer Id;
	
	@Column(name = "Price")
	private int Price;
	
	@Column(name = "Quantity")
	private int Quantity;
	
	@Column(name = "Date")
	private Date Date = new Date();

	@Column(name = "IdProduct")
	private int IdProduct;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public int getPrice() {
		return Price;
	}

	public void setPrice(int price) {
		Price = price;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public int getIdProduct() {
		return IdProduct;
	}

	public void setIdProduct(int idProduct) {
		IdProduct = idProduct;
	}
	
}
