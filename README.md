# Final Project - Software Development Fundamentals

This repository contains the final project for the course *Fundamentos de Desenvolvimento de Software* at PUCRS. The project is an application for managing subscriptions, developed using Clean Architecture principles and JPA for data persistence.

## Project Overview

The application is designed to manage the subscription lifecycle, including users, clients, and payments. It follows a layered structure, respecting the separation of concerns and promoting easy maintenance and scalability.

### Technologies Used

- **Java**: Core language for business logic and persistence.
- **Spring Data JPA**: Used for data storage and access, facilitating integration with a relational database.
- **Clean Architecture**: Ensures that the core logic is independent from external concerns like data persistence or user interface.

### Project Structure

The project follows a modular structure, divided into several packages:

- **apresentacao**: Handles user interaction through the `AppController.java` class, which acts as the interface between the user and the business layer.

- **config**: Contains configuration files like `DataInitializer.java`, used to bootstrap data for the application.

- **negocio**: Represents the business logic layer. It includes services for different aspects of the subscription system, such as:
  - `AplicativoService.java`
  - `AssinaturaService.java`
  - `ClienteService.java`
  - `PagamentoService.java`
  - `UsuarioService.java`
  - `VendaAssinatura.java`

- **persistencia**: Handles data persistence and database operations through entities and repositories, including:
  - Entities like `Aplicativo.java`, `Assinatura.java`, `Cliente.java`, `Pagamento.java`, and `Usuario.java`.
  - Repositories like `IAplicativoRepository.java`, `IAssinaturaRepository.java`, `IClienteRepository.java`, `IPagamentoRepository.java`, and `IUsuarioRepository.java`.

- **resources/static/admin-ui**: Contains front-end files, including:
  - `index.html`: Main HTML page.
  - `scripts.js`: JavaScript for front-end logic.
  - `styles.css`: CSS for styling the UI.
  - `application.properties`: Configuration properties for the application.

- **test/java/br/pucrs/samaramtsouza**: Contains test files such as `TfApplicationTests.java` to ensure that the core features are functioning as expected.

### How to Run the Project

1. Clone this repository:
- `git clone [repository_url]`
2. Navigate to the project directory and build the project using Maven:
- `mvn clean install`
3. Run the application:
- `mvn spring-boot`

### Usage

- Access the application through the `index.html` page, which provides an interface for managing subscriptions.
- Admin UI includes forms for adding clients, users, and managing payments.

### Testing

The project includes unit tests located in the `test` folder. To run the tests:
- `mvn test`
