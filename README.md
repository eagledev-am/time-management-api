# Time Managemet Api
The **Time Management System API** is designed to help users effectively organize and manage their tasks and projects. **Key features** include:
- **Task Management**: The core unit where users define and track their activities and goals.
- **Project Collaboration**: Users can create projects to manage related tasks. Admins and managers can assign team members, encouraging collaborative work.
- **Task Categorization**: users can organize tasks into custom categories for better task prioritization and tracking.
- **Communication**: Task discussions are facilitated through comments and replies, allowing for detailed communication between project members.
- **File Sharing**: Members can attach files to tasks and projects, centralizing necessary resources and documentation.
- **Notifications:** Keeps users informed about task updates, comments, and project changes through a notification system.


# Table Of Contents
* [Getting Started](#GettingStarted)
  * [Prerequisites](#Prerequisites)
  * [Installation](#installation)
* [Usage](#Usage)
  * [Authentication](#Authentication)
  * [Endpoints](#Endpoints)
* [Examples](#examples)
* [Contact](#contact)



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

<a name="contact"></a>
# Contact
**Email:** [magdyabdo484@gamil.com](mailto:magdyabdo484@gmail.com)
  
