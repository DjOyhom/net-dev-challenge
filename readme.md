For run the application you have to use this command docker-compose up.
Make a POST http request to http://localhost:5000/insertdata to generate random data.

For list all product, you must to make a GET http request to http://localhost:5000/list.

To make a purchase, you must to make a POST http request to http://localhost:5000/buy with 2 params, idProduct and quantity.
For example: http://localhost:5000/buy?idProduct=4&quantity=2126
When you make a purchase, the application consult to another application if the product have discount, in this case, the discount is automatic.

For list all purchase, you have to repeat the same method to list all product but changing where make the http request to /purchase.

To change the category threshold, you must to make a PUT http request to http://localhost:5000/umbral with 2 params, id in the path and umbral like a request param.
For example: http://localhost:5000/umbral/12?umbral=5



