## Introduction

X-Choice is a survey platform which can allow users to publish surveys quickly.

This package is the backend service for the application. For an overview of the project and the front end package, please check [xchoice-web](https://github.com/kevinwchn/xchoice-web).
## Tech stack

- Java
- Spring & SpringBoot
- JUnit 5
- Mockito
- MySQL

## Local development

### Run dev server

I highly recommend using IntelliJ for Java development, which is the most powerful IDE for Java in my opinion. 

First, import project to IntelliJ by selecting `pom.xml` file and import as project. IntelliJ will automatically download and index dependencies for you.

To run the development server:

Go to `src/main/java/XChoiceApplication` and run the main function. By default, it will run on port 8080.

Since this service calls MySQL database, to avoid exposing the database login credentials, I've taken the approach to use env variables. Thus to run locally, you need the following env variables: `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`. Those can be configured in your `.bash_profile` or `.zshrc` depending on which one your desktop is using. Because of the same security concern, I will not post the names here, thus please drop me a message or email kangxu.wang@outlook.com for variable details.

### Run unit tests

In IntelliJ, right click on `src/test/java` folder and select Run 'All Tests'.


## API Design

TBD

## Appendix

### References
