# CorePlatform - Price API

Este proyecto implementa una API REST para consultar el precio aplicable de un producto en una fecha concreta para una marca específica.

## Arquitectura

- **Hexagonal**: Separación clara entre dominio, aplicación e infraestructura.
- **Principios SOLID**: Se aplican en cada capa del sistema.
- **TDD (Test Driven Development)**: El desarrollo parte desde los tests.
- **API First**: Definida con OpenAPI 3.

---

## Cómo ejecutar la aplicación

### Requisitos

- Java 21
- Maven 3.9+
- Docker (opcional, pero recomendado)

### Opción 1: Local con Maven

```bash
mvn clean spring-boot:run
```

La aplicación se ejecutará en:  
`http://localhost:8080`

### Opción 2: Docker (recomendado)

```bash
docker build -t coreplatform .
docker run -p 8080:8080 coreplatform
```

---

## Especificación OpenAPI / Swagger

- Swagger UI disponible en:  
  `http://localhost:8080/swagger-ui/index.html`

- El fichero OpenAPI (`openapi.yaml`) se encuentra en:  
  `src/main/resources/static/openapi.yaml`

---

## Gestión de excepciones

Se captura y formatea toda excepción esperable en `GlobalExceptionHandler`, devolviendo objetos `ErrorResponseDTO` con mensajes descriptivos.

---

## Notas finales

- Diseño orientado al dominio, fácilmente extensible con nuevos casos de uso.
- Fácil de mantener y escalar gracias a la separación de responsabilidades.
- Preparado para despliegue en producción con contenedores.