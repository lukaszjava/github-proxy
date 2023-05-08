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

### Docker
Make sure that you are in github-proxy catalog (at the same level as the dockerfile)
1. docker build --tag=github-proxy:latest . or add a tag you like
2. docker run -p8887:8080 github-proxy:latest 

then you can call api 

    localhost:8887/github-proxy/repos/{username}
    