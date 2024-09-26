# Time Managemet Api
The **Time Management System API** is designed to help users effectively organize and manage their tasks and projects. **Key features** include:
- **Task Management**: The core unit where users define and track their activities and goals.
- **Project Collaboration**: Users can create projects to manage related tasks. Admins and managers can assign team members, encouraging collaborative work.
- **Task Categorization**: users can organize tasks into custom categories for better task prioritization and tracking.
- **Communication**: Task discussions are facilitated through comments and replies, allowing for detailed communication between project members.
- **File Sharing**: Members can attach files to tasks and projects, centralizing necessary resources and documentation.
- **Notifications:** Keeps users informed about task updates, comments, and project changes through a notification system.


# Table Of Contents
* [Technologies](#Technologies)
* [Prerequisites](#Prerequisites)
* [Installation](#installation)
* [Test](#test)
  



<a name="Technologies"></a>

## Technologies
- Spring boot
- Spring data jpa
- Spring security with JWT
- Spring Email
- Realtime notifications using Websocket
- MySQL
- Mapstruct (mapping)
- Swagger
- Maven
- Docker


<a name="Prerequisites"></a>
## Prerequisites
* Java ( JDK  )
* MySQL database 
* Maven
* Postman or a similar tool for API testing
* Docker (optional)

<a name="installation"></a>
## installation
#### Run with maven

1- Clone Repo
<br>

```git
git clone https://github.com/eagledev-am/time-management-api.git
```
<br>

2- Create ```env.properities``` file in the ```root directory``` of the project  

<br>
 
> [!IMPORTANT]
> - [ ] You can Get your App Password using this **[Link](https://myaccount.google.com/u/1/apppasswords)** <br> 
> - Copy the next properities to your env file

<br>

```
# db info
DB_USERNAME=YOUR_DB_USERNAME
DB_PASSWORD=YOUR_DB_PASSWORD

# email
MAIL_USERNAME=YOR_EMAIL
MAIL_PASSWORD=GOOGLE_APP_PASSWORD

# secret key
SECRET_KEY=YOUR_SECRET_KEY

```
<br>

3- Open ```cmd``` in the project dir and place this commands :
<br>

-  Build the Spring Boot Application
```
mvn clean package
```
- Run the project 
```
 ./mvnw spring-boot:run
```

<br>

#### Run With Docker 


>[!NOTE]
> make sure that docker is running. Open a ```cmd``` in the root directory of the project and place the next command. 

<BR>

-  Build the Spring Boot Project
```
mvn clean package
```

- Start the Services with Docker Compose
```
docker-compose up --build
```
- Verify Everything is Running
```
docker-compose ps
```

- To stop and remove all containers 
```
docker-compose down
```
<br>

> [!IMPORTANT]
> - [ ] The Project is Running in the port : ```8080```
> - [ ] Now, Spring Boot application should be running and accessible via ```http://localhost:8080```.
> - [ ] Use **Swagger ui** to discover documentation of project ```http://localhost:8080/swagger-ui/index.html```

<a name="test"></a>
## Test
[<img src="https://run.pstmn.io/button.svg" alt="Run In Postman" style="width: 128px; height: 32px;">](https://app.getpostman.com/run-collection/29779062-32ae97d6-7138-48f4-bc99-73f0216e7f33?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D29779062-32ae97d6-7138-48f4-bc99-73f0216e7f33%26entityType%3Dcollection%26workspaceId%3Df05a19fb-5bb8-4877-93aa-ca3c9a8411ba)


## ERD Digram
![erd](https://github.com/eagledev-am/time-management-api/blob/main/pictures/erd.png)



