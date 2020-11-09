# JAVA-SPRING-POSTGRESQL-ANGULAR

**! This project contains plenty of examples of how to configure and work with Spring Boot, Spring Security, PostgreSQL database, Hibernate framework, REST APIs, and even write tests using JUnit and Mockito. Also, Angular components, services, routing, and other usefull stuff can be found on the frontend side.**

An example of a working project with a **client-server** architecture, where:
* the **server** is written in Java and uses Spring Boot, Spring Security and PostgreSQL;
* the **client** is written in Angular.

## App overview
This project is basically an application that allows one to perform CRUD operations on 3 entities: movies, clients and rentals. Between movies and clients there is a many to many relationship (since one movie can be rented by many clients and one client can rent many movies) which is mediated by the intermediate rentals entity (so basically we have a one to many relationship between movies and rentals, and a many to one relationship between rentals and clients).

**! Note that the application is very simplistic since its aim is only to provide the basics of how to work with and integrate all of the technologies and frameworks mentioned above.**

## Screenshots from the application

## Features of the application:
* login & logout - **there can be 2 types of users for this application: CLIENTs and ADMINs**;
* Add/Delete/Update/List/View details (of) movie(s);
* Add/Delete/Update/List/View details (of) client(s);
* Add/Delete/Update/List/View details (of) rental(s) - **only when logged in as an ADMIN**.

## How to start the application:
**Backend:**
* Make sure you have the PostgreSQL server installed, along with pgAdmin;
* Modify the username and password keys from the application.properties to match your PostgreSQL credentials;
* Create an empty DB named "movieapp" (at the first run of the application, the tables will be created automatically, but the database itself must be already created);
* Add users in the "app_user" table. You can execute this SQL query to do so: "insert into app_user VALUES (<INT_ID>, '<FIRST_NAME>', '<LAST_NAME>', '<ENCODED_PASSWORD>', '<USERNAME>', '<ROLE>');". Make sure you replace all the values with your custom ones, and make sure that ROLE is either CLIENT or ADMIN. **To obtain the encoded password**, check the SecurityConfig.java file and change the value of the "passwordToEncode" variable to a password that you wish to use for your user; then, just run the application once and look for the log in the console which will output your encoded password;
* Run the app.

**Frontend:**
* Install Node.js;
* Install Angular CLI;
* Run "npm install" followed by "npm audit fix" in the angular-app folder from the catalog-web module;
* Run "npm install rxjs-compat" in the same angular-app folder;
* Install bootstrap as indicated in the approved stack overflow answer from this page: https://stackoverflow.com/questions/52676474/bootstrap-not-working-properly-in-angular-6#:~:text=js%20script%20Bootstrap%20will%20not%20work.&text=If%20you%20are%20adding%20the,the%20project%5Carchitect%5Ctest.
* Run "npm start" on the angular-app folder.

