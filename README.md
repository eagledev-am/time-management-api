# TODO List API
The Todo List API provides a powerful and flexible backend for managing tasks and to-dos in your applications. Designed with simplicity and extensibility in mind, this API enables seamless integration of task management functionalities.

# Table Of Contents
* [Features]()
* [Getting Started]()
  * [Prerequisites]()
  * [Installation]()
* [Usage]()
  * [Authentication]()
  * [Endpoints]()
* [Examples]()

#  Features
* Task Operation: create,retrieve,update and delete task
* Task List: Organize tasks into lists ( categories or tags )
* User Authentication: use JWT to authenticate user accounts
* Filtering and Search: find user tasks based on status
# Getting Started 
## Prerequisites
* Java ( JDK 17 )
* MySQL database ( your chioce )
* Maven
* IDE ( your choice )
* Postman or a similar tool for API testing
* Docker
## installation
1- clone repository
```
git clone https://github.com/eagledev-am/todolist-api.git
```
2- change application.properties 
```
spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE
spring.datasource.password=YOUR_PASSWORD
spring.datasource.username=YOUR_USERNAME
server.port=8080
```
3-  run the following command in a terminal window (in the complete) directory:
```
 ./mvnw spring-boot:run
```
The application is running in <font color="blue"> http://localhost:8080 </font>
## Another installation way
1- run the following command in a terminal window (in the complete) directory:
```
docker compose up -d
// This create two docker s' containers
// one for application
// one for database  
```
The application is running in <font color="blue"> http://localhost:8080 </font>
The database is running in <font color="blue"> http://localhost:3306 </font>
2- To Stop and remove all containers 
```
docker compose down
```
# Usage 
 ### **Authentication**
 Create new User:
 ```
  POST /api/auth/register
 ```
 Authenticate user and Access Token
 ```
  POST /api/auth/login
 ```
 #### **Endpoints**
 * Tasks
   | Method | Endpoint | description |
   | :------: | :------: | :------:|
   | GET | /api/tasks/:id | get a task |
   | POST | /api/lists/:list_id/tasks| create a task at specified list |
   | PUT | /api/tasks/:id  | update a task |
   | DELETE | /api/tasks/:id | delete a task |

  
