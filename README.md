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



<a name="Technologies"></a>

## Technologies
- Spring boot
- Spring data jpa
- JWT (security)
- Spring Email
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
1- Clone Repo
<br>

```git
git clone https://github.com/eagledev-am/todolist-api.git```
```
<br>

2- Create ```env.properities``` file in the ```classpath```  

<br>
 
> [!IMPORTANT]
> - [ ] You can Get your App Password using this **[Link](https://myaccount.google.com/u/1/apppasswords)** <br> 
> - [ ] Create **env.properites** in the **resources** dir

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

3- Open ```cmd``` in the project dir and place this command :
<br>

```
 ./mvnw spring-boot:run
```
<br>

<!-- ## To Run The project using docker Use this command 

<br>

```
docker compose up -d
```


2- To Stop and remove all containers 
```
docker compose down
```
-->

> [!NOTE]
> - [ ] The Project is Running in the port : ```8080```
> - [ ] In your machine You can use this ```http://localhost:8080/{endpoint}``` to Call API
> - [ ] Use **Swagger ui** to discover documentation of project ```http://localhost:8080/swagger-ui/index.html```


  
