package com.rodrigo.gdc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class category {

	@Id
	private Integer Id;
	
	@Column(name = "Name", length = 45)
	private String Name;
	
	@Column(name = "Umbral")
	private int Umbral;

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

	public int getUmbral() {
		return Umbral;
	}

	public void setUmbral(int umbral) {
		Umbral = umbral;
	}



}
