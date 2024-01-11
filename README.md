# TODO List API
The Todo List API provides a powerful and flexible backend for managing tasks and to-dos in your applications. Designed with simplicity and extensibility in mind, this API enables seamless integration of task management functionalities.

# Table Of Contents
* [Features](#Features)
* [Getting Started](#GettingStarted)
  * [Prerequisites](#Prerequisites)
  * [Installation](#installation)
* [Usage](#Usage)
  * [Authentication](#Authentication)
  * [Endpoints](#Endpoints)
* [Examples](#examples)
* [Contact](#contact)

<a name="Features"></a>
#  Features
* Task Operation: create,retrieve,update and delete task
* Task List: Organize tasks into lists ( categories or tags )
* User Authentication: use JWT to authenticate user accounts
* Filtering and Search: find user tasks based on status
<a name="GettingStarted"></a>
# Getting Started 
<a name="Prerequisites"></a>
## Prerequisites
* Java ( JDK 17 )
* MySQL database ( your chioce )
* Maven
* IDE ( your choice )
* Postman or a similar tool for API testing
* Docker
<a name="installation"></a>
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
The application is running in <font color="blue"> http://localhost:8080 </font></br>
The database is running in <font color="blue"> http://localhost:3306 </font></br></br>
2- To Stop and remove all containers 
```
docker compose down
```
<a name="Usage"></a>
# Usage 
<a name="Authentication"></a>
 ### **Authentication**
 Create new User:
 ```
  POST /api/auth/register
 ```
 Authenticate user and Access Token
 ```
  POST /api/auth/login
 ```
<a name="Endpoints"></a>
 #### **Endpoints**
 * Tasks
   </br>
   | Method | Endpoint | description |
   | :------: | :------: | :------:|
   | GET | /api/tasks/:id | Get a task |
   | POST | /api/lists/:list_id/tasks| Create a task at specified list |
   | PUT | /api/tasks/:id  | Update a task |
   | DELETE | /api/tasks/:id | Delete a task |
   | GET | /api/users/:user_id/tasks | Get all tasks of user |
   | GET | api/users/:user_id/tasks?status= | Get tasks based on status |
   | GET | /api/lists/:list_id/tasks | Get all tasks of a list |
* Lists
  </br>
     | Method | Endpoint | description |
   | :------: | :------: | :------:|
   | GET | /api/lists/:list_id/tasks| Get a tasks of a list |
   | POST | /api/users/:user_id/lists| Create a list for a user |
   | DELETE | /api/lists/:id| Delete a list |
   | GET | /api/users/:id/lists | Get all list of a user |
* User
  </br>
       | Method | Endpoint | description |
   | :------: | :------: | :------:|
   | GET | /api/users/:id| Get a user |
   | PUT | /api/users/:id| Update user data |
   | DELETE | /api/lists/:id| Delete a user |

<a name="examples"></a>
# Examples
You can test api using [Postman](https://www.postman.com/navigation-astronomer-98011947/workspace/api/collection/29779062-3cdf8018-1481-4f33-9abd-e2dcc9ac0b5d?action=share&creator=29779062)

<a name="contact"></a>
# Contact
**Email:** [magdyabdo484@gamil.com](mailto:magdyabdo484@gmail.com)
  
