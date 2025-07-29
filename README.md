
# CorePlatform - Price API

Este proyecto implementa una **API REST** en **Spring Boot** para consultar el precio aplicable de un producto en una fecha concreta para una marca específica, basado en una tabla `PRICES` simulada en base de datos **H2** en memoria.

---

## 📌 Descripción

En la base de datos se dispone de la tabla `PRICES` que refleja el precio final (**PVP**) y la tarifa que aplica a un producto de una marca dentro de un rango de fechas determinado.

El servicio REST acepta como **parámetros de entrada**:
- **`applicationDate`** → Fecha y hora de aplicación (en formato ISO LocalDateTime).
- **`productId`** → Identificador de producto.
- **`brandId`** → Identificador de cadena o marca.

Y devuelve como **datos de salida**:
- Identificador de producto.
- Identificador de marca.
- Identificador de tarifa aplicable (**priceList**).
- Rango de fechas de aplicación (**startDate**, **endDate**).
- Precio final a aplicar (**price**).
- Moneda (**curr**).

Si existen múltiples tarifas aplicables en la misma fecha y rango, se aplica la de **mayor prioridad** (`priority`).

---

## 🧩 Arquitectura

- **Hexagonal (Ports & Adapters)**: Separación clara entre dominio, aplicación y capa de infraestructura.
- **Principios SOLID**: Respetados en todo el diseño para garantizar extensibilidad y mantenibilidad.
- **TDD (Test Driven Development)**: Desarrollo guiado por tests unitarios y de integración.
- **API First**: Contrato definido mediante **OpenAPI 3**, con **Swagger UI** disponible.

---


## 🗄️ Base de datos

- Motor: **H2** en memoria.
- Datos precargados desde `data.sql`.
- Ejemplo de tabla `PRICES`:

| BRAND_ID | START_DATE          | END_DATE            | PRICE_LIST | PRODUCT_ID | PRIORITY | PRICE | CURR |
|----------|---------------------|---------------------|------------|------------|----------|-------|------|
| 1        | 2020-06-14 00:00:00 | 2020-12-31 23:59:59 | 1          | 35455      | 0        | 35.50 | EUR  |
| 1        | 2020-06-14 15:00:00 | 2020-06-14 18:30:00 | 2          | 35455      | 1        | 25.45 | EUR  |
| 1        | 2020-06-15 00:00:00 | 2020-06-15 11:00:00 | 3          | 35455      | 1        | 30.50 | EUR  |
| 1        | 2020-06-15 16:00:00 | 2020-12-31 23:59:59 | 4          | 35455      | 1        | 38.95 | EUR  |

---

## 🚀 Cómo ejecutar la aplicación

### ✅ Requisitos previos

- Java **21**
- Maven **3.9+**
- Docker (opcional, recomendado para entorno homogéneo)

### ⚙️ Opción 1: Ejecutar localmente con Maven

```bash
mvn clean spring-boot:run
```

La aplicación se ejecutará en:
```
http://localhost:8080
```

### 🐳 Opción 2: Ejecutar con Docker (recomendado)

```bash
docker build -t price-api .
docker run -p 8080:8080 price-api
```

---

## 🔗 Endpoints

### 📌 Endpoint principal

**GET** `/api/prices`

**Parámetros de consulta:**
- `applicationDate` → Fecha y hora de aplicación (formato ISO LocalDateTime)
- `productId` → ID de producto
- `brandId` → ID de marca

**Ejemplo de petición:**

```
GET http://localhost:8080/api/prices?applicationDate=2020-06-14T16:00:00&productId=35455&brandId=1
```

**Ejemplo de respuesta exitosa:**

```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "curr": "EUR"
}
```

Si no existe tarifa aplicable, se devuelve un **404 Not Found** con mensaje descriptivo.

---

## 📑 OpenAPI & Swagger

- **Swagger UI** disponible en:  
  [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- Archivo `openapi.yaml` en:  
  `src/main/resources/static/openapi.yaml`

---

## ⚠️ Gestión de errores

Las excepciones se gestionan globalmente mediante `GlobalExceptionHandler`, devolviendo respuestas normalizadas con estructura `ErrorResponseDTO` que incluye:
- Código de error HTTP
- Mensaje legible
- Marca de tiempo

---

## ✅ Tests E2E

Se incluyen tests E2E para validar todos los escenarios indicados:

| Test | Fecha de petición                | Resultado esperado |
|------|----------------------------------|--------------------|
| 1    | `2020-06-14T10:00:00`            | Tarifa 1           |
| 2    | `2020-06-14T16:00:00`            | Tarifa 2           |
| 3    | `2020-06-14T21:00:00`            | Tarifa 1           |
| 4    | `2020-06-15T10:00:00`            | Tarifa 3           |
| 5    | `2020-06-16T21:00:00`            | Tarifa 4           |

Los tests están implementados con **JUnit 5**, **Spring Boot Test**, y **RestAssured**.

Para ejecutarlos:

```bash
mvn clean test
```

---

## 📝 Notas finales

✔️ Código limpio, mantenible y extensible siguiendo **Hexagonal Architecture** y **Principios SOLID**.  
✔️ Preparado para entornos de integración y despliegue continuo mediante contenedor Docker.  
✔️ Fácil de extender para nuevas reglas de negocio o persistencia.
