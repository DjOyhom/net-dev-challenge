package com.rodrigo.gdc;

import org.springframework.web.client.RestTemplate;

public class discount {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {

	    final String uri = "http://apigdd:3000/" + id;
	     
	    RestTemplate restTemplate = new RestTemplate();
	    String result = restTemplate.getForObject(uri, String.class);
	     
	    System.out.println(result);
		this.id = result;
	}

	
}
