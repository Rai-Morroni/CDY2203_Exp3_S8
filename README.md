# Unidos por los Animales - Gestión Veterinaria

Este repositorio contiene la solución desarrollada para la asignatura de **Seguridad y Calidad en el Desarrollo (CDY2203)** de Duoc UC. La aplicación es un sistema de gestión veterinaria integral que cumple con estándares modernos de seguridad, arquitectura desacoplada y aseguramiento de calidad mediante pruebas automatizadas.

## 🚀 Contexto del Proyecto

[cite_start]El objetivo principal es asegurar la robustez de una aplicación web compuesta por un ecosistema de microservicios y vistas, garantizando que el código sea seguro y altamente probado[cite: 1298].

- **Estudiante:** Rainiero Morroni
- **Institución:** Duoc UC
- **Semana de Entrega:** Semana 8 (Asegurando la calidad de mi aplicación)

## 🛠️ Tecnologías Utilizadas

### Backend
- [cite_start]**Framework:** Spring Boot 3.3.5 [cite: 1300]
- [cite_start]**Seguridad:** Spring Security con autenticación basada en **JWT (JSON Web Token)**[cite: 1302, 1303].
- [cite_start]**Persistencia:** Spring Data JPA con MySQL[cite: 1300].
- **Documentación:** Springdoc OpenAPI / Swagger.

### Frontend
- [cite_start]**Framework:** Spring Boot con **Thymeleaf** para el motor de plantillas[cite: 1307].
- [cite_start]**Estilos:** CSS3 funcional[cite: 1310].
- **Comunicación:** RestTemplate para consumo de APIs protegidas.

### Infraestructura y Calidad
- [cite_start]**Contenedores:** Docker para el despliegue de la base de datos MySQL 8.0[cite: 1324].
- [cite_start]**Pruebas:** JUnit 5 y MockMVC[cite: 1380].
- [cite_start]**Cobertura:** **JaCoCo** (Java Code Coverage)[cite: 1376].

## 📊 Aseguramiento de Calidad (Code Coverage)

[cite_start]Se ha implementado una estrategia de pruebas unitarias y de integración siguiendo el patrón **Arrange-Act-Assert**[cite: 1380]. [cite_start]Se configuró JaCoCo con una regla de cumplimiento mínima del **60%**[cite: 1379].

### Resultados Finales:
- [cite_start]**Cobertura Backend:** **65%** [cite: 1395]
- [cite_start]**Cobertura Frontend:** **63%** [cite: 1412]

Los reportes detallados pueden ser generados localmente en la carpeta `/target/site/jacoco/index.html`.

## 📂 Estructura del Repositorio

El proyecto se encuentra estructurado de forma modular para facilitar su mantenimiento:

- `/backend`: Lógica de negocio y APIs REST protegidas.
- `/frontend`: Interfaz de usuario y consumo de servicios.
- `/database`: Configuración de infraestructura mediante `Dockerfile` y scripts `init.sql`.

## ⚙️ Instalación y Ejecución

### 1. Base de Datos (Docker)
Navega a la carpeta `/database` y ejecuta:
```bash
docker build -t mysql-db .
docker run -d -p 3306:3306 --name bd-veterinaria mysql-db