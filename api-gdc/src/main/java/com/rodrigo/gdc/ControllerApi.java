package com.rodrigo.gdc;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


@Controller
public class ControllerApi{

	private final static String QUEUE_NAME = "Low Stock";
	
	private String server_mq = "mqservice";
	
	@Autowired
	private purchaseI purchaseI;
	
	@Autowired
	private productI productI;
	
	@Autowired
	private categoryI categoryI;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Iterable<product> List(Model model) {
	   return  productI.findAll();
	}
	
	@RequestMapping(value = "/mqtest", method = RequestMethod.GET)
	public @ResponseBody String MqTest(Model model) throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost(server_mq);
	    factory.setPort(5672);
	    try (Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel()) {
	    	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	    	String message = "Testting MQ";
	    	channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
	        System.out.println(" [x] Sent '" + message + "'");
    	}
		return  "ss";
	}
	
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public String buy(Model model,
			@RequestParam(name="quantity", required=true) int quantity,
			@RequestParam(name="idProduct", required=true) int idProduct
			) throws Exception {
		
    	String responsemsg = "as";
    	
    	Optional<product> prodBefore = productI.findById(idProduct);
    	
    	System.out.println(prodBefore.get().getStock() );
    	System.out.println(quantity);
    	
    	if((prodBefore.get().getStock() - quantity) > 0) {
    		
        	String prodName = prodBefore.get().getName();
        	int prodStock = prodBefore.get().getStock() - quantity;
    		int prodCategory = prodBefore.get().getCategory();
        	int prodPrice = prodBefore.get().getPrice();
  	
        	product produc = new product();
        	produc.setId(idProduct);
        	produc.setCategory(prodCategory);
        	produc.setName(prodName);
        	produc.setStock(prodStock);
        	produc.setPrice(prodPrice);
        	productI.save(produc);
        	
        	discount s = new discount();
    		s.setId(Integer.toString(idProduct));
    		int price = prodPrice - Integer.parseInt(s.getId());
        	
        	Date date = new Date();
        	purchase p = new purchase();
        	int id = purchaseI.findAll().size();
    		
        	p.setId(id);
        	p.setPrice(price);
        	p.setQuantity(quantity);
        	p.setIdProduct(idProduct);
        	p.setDate(date);
        	
        	purchaseI.save(p);
        	responsemsg = "Successful purchase";
        	
        	Optional<category> cate = categoryI.findById(prodCategory);
        	if(prodStock < cate.get().getUmbral()) {
        		
        		ConnectionFactory factory = new ConnectionFactory();
        	    factory.setHost(server_mq);
        	    factory.setPort(5672);
        	    try (Connection connection = factory.newConnection();
        	    Channel channel = connection.createChannel()) {
        	    	channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        	    	String message = "Low Stock in " + cate.get().getName();
        	    	channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        	        System.out.println(" [x] Sent '" + message + "'");
            	}
        	}
        	
        	
    	}
    	
    			
		model.addAttribute("response", responsemsg);
		return "response";
	}
	
	@RequestMapping(value = "/purchases", method = RequestMethod.GET)
	public  @ResponseBody Iterable<purchase> Purchases(Model model) {
	  return purchaseI.findAll();
	}
	
	@RequestMapping(value = "/umbral/{id}", method = RequestMethod.PUT)
	public String umbral(Model model,
			@PathVariable("id") int id, 
    		@RequestParam(name="umbral", required=true) int umbral
			) {
		
		category c = new category();
    	Optional<category> cate = categoryI.findById(id);
    	
    	String name = cate.get().getName();
    	
    	c.setName(name);
    	c.setId(id);
    	c.setUmbral(umbral);
    	categoryI.save(c);
		
		model.addAttribute("response", "Succesful modification");
		return "greeting";
	}
	
	@RequestMapping(value = "/insertdata", method = RequestMethod.POST)
	public String insertdata(Model model) {

		for(int i = 0; i < 10; i++) {
			Random rand = new Random(); 
			
	    	category cat = new category();
	    	int catId = categoryI.findAll().size() + 1;
	    	cat.setId(catId);
	    	cat.setName("Category" + Integer.toString(catId));
	    	cat.setUmbral(25 + rand.nextInt(100));
			categoryI.save(cat);
				
	    	product pro = new product();
	    	int proId = productI.findAll().size() + 1;
	    	int proPrice = rand.nextInt(9000);
	    	pro.setId(proId);
	    	pro.setCategory(catId);
	    	pro.setPrice(proPrice + 1);
	    	pro.setName("Product" + Integer.toString(proId));
	    	pro.setStock(1000 + rand.nextInt(2000));
	    	productI.save(pro);
	    	
	    	purchase pur = new purchase();
	    	int purId = purchaseI.findAll().size() + 1;
			pur.setId(purId);
			pur.setIdProduct(proId);
			pur.setPrice(proPrice);
			pur.setQuantity(rand.nextInt(100) + 1);
			purchaseI.save(pur);
		}
		model.addAttribute("response", "Successful data upload");
		return "response";
	}
}

