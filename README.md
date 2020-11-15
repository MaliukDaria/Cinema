# Cinema 
The goal of this project is to create a model of a cinema ticket service. The project implements role-based access control with 2 roles: user and administrator.:
 - administrators can add cinema halls, movies and movie sessions
 - users can add tickets to cart, checkout and view order history
 - registration, a list of films and available sessions are available for unregistered users

## Implementation details and technologies
Project based on 3-layer architecture:
- Presentation layer (controllers)
- Application layer (services)
- Data access layer (DAO)

### Technologies
* Apache Tomcat - version 9.0.37
* MySQL - version 8.0.21
* Hibernate - version 5.4.5
* Spring Framework - version 5.2.2
* Spring Web MVC
* Spring Security

## Setup
1. Configure Apache Tomcat 
2. Install MySQL and MySQL Workbench
3. Add all necessary properties in db.properties file 
4. Add Lombok plugin 
After starting the project, 3 users will be available:
- user with administrator rights (login “admin”, password “1234”)
- user with normal rights (login “alise@mail.com”, password “1234”)
- user with ordinary user and administrator rights (login “bob@mail.com”, password “1234”)

## Author
[Maliuk Daria](https://github.com/MaliukDaria)
