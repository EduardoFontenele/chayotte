# Chayotte

A modular logistics management system for multi-segment retailers.

## Overview

Chayotte is a comprehensive logistics management platform designed to help retailers across various segments streamline their operations. Built with a modular monolithic architecture, Chayotte provides a robust set of features to manage products, orders, deliveries, and more.

## Key Features

- **Multi-segment Support**: Works for various retail segments (pet shops, music stores, etc.)
- **Product Management**: Complete product catalog and inventory management
- **Order Processing**: End-to-end order management and tracking
- **Customer Management**: Customer profiles and purchase history
- **Delivery Tracking**: Shipment management and real-time tracking
- **Billing & Invoicing**: Automated invoice generation and tax compliance
- **Reporting**: Comprehensive analytics and business intelligence
- **Notifications**: Automated alerts and customer communications

## Technical Stack

- **Backend**: Spring MVC (Java)
- **Architecture**: Modular Monolith based on Domain-Driven Design
- **Database**: SQL Relational Database
- **Authentication**: Keycloak IAM
- **Batch Processing**: Spring Batch for reporting
- **Documentation**: OpenAPI/Swagger
- **Inter-module Communication**: Spring Application Events

## Modules

Chayotte is organized into the following domain-based modules:

- **Company**: Retailer management
- **Product**: Product and inventory management
- **Order**: Order processing
- **Customer**: Customer data management
- **Delivery**: Shipping and tracking
- **Billing**: Invoicing and financial operations
- **Notification**: Alert system
- **Report**: Analytics and reporting
- **Integration**: Third-party services connectivity
- **User**: Authentication and authorization (via Keycloak)

## Getting Started

### Prerequisites

- JDK 17+
- Maven or Gradle
- SQL Database (PostgreSQL/MySQL)
- Keycloak server

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/chayotte.git
cd chayotte
```

2. Configure the database connection in `application.yml`

3. Start the application
```bash
./mvnw spring-boot:run
```

4. Access the application at http://localhost:8080
5. API documentation is available at http://localhost:8080/swagger-ui.html

## Development

Chayotte follows a domain-driven design approach with clear boundaries between modules. Each module has its own set of controllers, services, repositories, and models.

### Project Structure

```
src/main/java/com/chayotte/
├── common/           # Shared components and utilities
├── config/           # Application configuration
├── company/          # Company module
├── product/          # Product module
├── order/            # Order module
├── customer/         # Customer module
├── delivery/         # Delivery module
├── billing/          # Billing module
├── notification/     # Notification module
├── report/           # Report module
├── integration/      # Integration module
└── ChayotteApplication.java
```

## License

[MIT License](LICENSE)

## Contact

For support or inquiries, please contact [your-email@example.com](mailto:your-email@example.com)