# Pet Service Project

This is a Kotlin-based project that demonstrates a Pet Management System with unit tests written using various testing frameworks. It includes tests using both Mockito and MockK, and showcases different aspects of testing such as mocking, assertions, and nested test classes. The project uses JUnit 5, AssertJ, Mockito, MockK, and Kotlin's `@Nested` classes to structure the tests.

## Technologies Used

- **Kotlin 1.9.22**: The main programming language for the project.
- **Java 17**: Configured Java version for compatibility.
- **JUnit 5**: The testing framework used for writing and executing tests.
- **Mockito**: A mocking framework for Java that is used to create mocks of dependencies for testing.
- **MockK**: A powerful mocking library for Kotlin that is used alongside Mockito.
- **AssertJ**: Fluent assertions for easy-to-read test validations.
- **Ktlint**: A linter for Kotlin code to ensure code quality and consistent style.

## Project Structure

The project consists of the following key components:

1. **Entity Classes**:
    - `Pet`: Represents a pet entity with basic information such as ID and name.

2. **Repository Classes**:
    - `PetRepository`: Interface for repository functions such as finding, adding, and deleting pets.

3. **Service Classes**:
    - `PetService`: The core business logic for managing pets, interacting with the repository.

4. **Unit Tests**:
    - Tests are organized using **@Nested** classes to separate different testing libraries and concepts. There are two main groups of tests: **MockitoTests** and **MockKTests**, which test the same functionality using different libraries.

## Setting Up

1. **Clone the Repository**:
   ```sh
   git clone <repository_url>
   cd TestingLibs
   ```

2. **Build and Test the Project**:
   The project uses Gradle as the build system.

   To build the project:
   ```sh
   ./gradlew build
   ```

   To run all tests:
   ```sh
   ./gradlew test
   ```

3. **Run Ktlint**:
   Ensure that your code follows the defined Kotlin style.
   ```sh
   ./gradlew ktlintCheck
   ```

   To automatically fix issues:
   ```sh
   ./gradlew ktlintFormat
   ```

## Key Features

- **Unit Testing**: Comprehensive unit tests are provided to cover all business logic of the `PetService`. Tests are written to achieve full code coverage, ensuring every function and edge case is addressed.

- **Mockito and MockK**: Demonstrates how to use two popular mocking frameworks in Kotlin. Mockito is used in the `MockitoTests` class, and MockK is used in the `MockKTests` class.

- **Error Handling**: Tests include scenarios for both successful operations and expected failures, such as adding a pet with an existing ID, ensuring proper error handling and message validation.

## Troubleshooting

If you encounter issues with Java compatibility, make sure you are using **Java 17**. If you're seeing errors like "Unsupported class file major version," it could be due to an incompatible Java version.
