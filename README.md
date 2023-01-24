# Login Register App

Uses Spring Boot 3.0.0 & Java 17

## Tools

- Spring Security
- Lombok
- java-jwt [Auth0]
- H2 database



There will be a default user admin/admin which can help to access.

**Urls Exposed**

> [/users/register] - help to register new user

> [/login]           - help to login 

> [app/status]       - help to know the status of application

> [app/db]           - help to open h2 database [try in browser only]

> [/hello]           - help to test secured endpoint only Users having USER role can access

> [/uploadfile]      - Any authorized user can upload files
