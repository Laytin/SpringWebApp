# Intro
This is an MVC application representing an online store application.
# Stack
The following frameworks were used during the project:
- Spring 
  - WEB: mappings
  - Data: to interact with database entities
  - Security: For access control
- Hibernate: for working with ORM and hql query for solving 1+n problems
- Log4j: control of making IMPORTANT changes to the database
- Thymeleaf: templates
- ModelMapper: working with DTO and some another cases

Also solved some 1+n problems by custom HQL queries.
# About
An application user has several roles, depending on which he has different capabilities. For example, a regular user cannot change another user's product or role.

Upon registration, the user receives the role "USER". It is possible to change personal information in your profile. He can view products, add them to the cart. Also, after adding his address, the user will be able to checkout from his cart and track it. Orders are stored in "Orders".

The user with the "MODERATOR" status can change the order status. It also has the ability to create new products, upload related images for a product, modify an existing product.

To get the "MODERATOR" status, you must ask a user with the "ADMIN" status to do so. It has the ability to change the user's status and will also inherit all "MODERATOR" access.

For security purposes, protection was organized with the help of Spring Security and the CSRF token. Also, log4j2 helps to track the actions of moderators and administrators.

# DataBase structure
![image](https://github.com/Laytin/SpringWebApp/assets/70861524/47ffa941-4da0-4acf-a302-dbf4ce65007f)
