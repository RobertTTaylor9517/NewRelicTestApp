# Shopper Java
***
Simple E-Commerce Back-end Integrated with New Relic APM for monitoring

### See this [project](https://immense-eyrie-31349.herokuapp.com/)

### Methods Used
+ uses JWT for authentication
+ uses New Relic APM to collect usage data
+ JPA Hibernate

### Technologies
+ Java
+ Spring Boot
+ New Relic

## Project Description
***

##### Overview:
E-commerce back-end created to test implementing New Relic APM software to record usage data

## Endpoints
***

##### /products - GET
List available products

##### /login - POST
required params - username, password

##### /signup - POST
required params - username, password, wallet

##### /user - GET
gets the current user based on current user logged in - requires token

##### /review - POST
required params - comment, rating, productId
creates a review for a specific product - requires token

##### /user/{id}/review - GET
gets a users reviews

##### /product/{id}/review - GET
gets review for a specific product

##### /purchase - POST
required params - Array of product ids
requires token