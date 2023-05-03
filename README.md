# GitHub Proxy
This Java application provides an API to retrieve information about a Github user's repositories. The API returns a JSON response including the repository name, owner login, and branch information.

### Technologies Used

    Java 17
    Spring Boot
    Maven
    Feign

### Running the Application

To run the application, clone the repository and run the following command:

    mvn spring-boot:run

By default, the application runs on port 8080. You can access the API at 

    http://localhost:8080/github-proxy/repos/{username}.

### Swagger-ui
   
    http://localhost:8080/github-proxy/swagger-ui/index.html
    