# Foro Hub - Challenge - Back End
Un `foro` es un espacio donde todos los participantes de una plataforma pueden plantear sus preguntas sobre determinados `tópicos`.


## Feature
Crearemos una API REST usando Spring. Nuestra API se centrará específicamente en los tópicos, y debe permitir a los usuarios:
#### Crear un nuevo tópico.
```http
  POST /topicos
```
#### Mostrar todos los tópicos creados.
```http
  GET /topicos
```
#### Mostrar un tópico específico.
```http
  GET /topicos/{id}
```
#### Actualizar un tópico.
```http
  PUT /topicos/{id}
```
#### Eliminar un tópico.
```http
  DELETE /topicos/{id}
```
#
- La API permite realizar las operaciones CRUD: Crear, Consultar, Actualizar y Eliminar tópicos.
- Los datos se guardan en una base de datos relacional llamada `foro_hub` para la persistencia de la información, la cual incluye las siguientes tablas:
  - `topicos`
  - `respuestas`
  - `cursos`
  - `usuarios`
- Se implementa la autenticación de JWT (JSON Web Token) para validar usuarios con correo y contraseña, los cuales están registrados en la base de datos de usuarios.
- Se implementan las validaciones de negocio para no recibir solicitudes con campos nulos y para no permitir el registro de tópicos duplicados con el mismo título y mensaje.
## Tech Stack

**Java JDK:** versión 17 en adelante.

**Maven:** versión 4 en adelante.

**Spring Boot:** versión 3 en adelante.

**MySQL:** versión 8 en adelante.

**IDE:** IntelliJ IDEA (opcional).


 ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
 ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white)

 


## Dependence
**spring-boot-starter-data-jpa:** Para la integración de JPA con Spring Boot.

**spring-boot-starter-security:** Para la seguridad y autenticación.

**spring-boot-starter-validation:** Para la validación de datos.

**spring-boot-starter-web:** Para crear aplicaciones web, incluyendo RESTful services.

**flyway-core y flyway-mysql:** Para la migración y gestión de la base de datos.

**spring-boot-devtools:** Para el desarrollo, con reinicio automático.

**mysql-connector-j:** Para la conexión con la base de datos MySQL.

**lombok:** Para reducir el código boilerplate.

**spring-boot-starter-test:** Para pruebas unitarias y de integración.

**spring-security-test:** Para pruebas de seguridad.

**java-jwt:** Para trabajar con JSON Web Tokens.
## Source Code Review
**Estructura del Proyecto**
- `Revisar la organización del código`: Verificar que el código esté organizado de manera coherente y siga las convenciones del proyecto (p. ej., paquete controller para controladores, domain para dominios, infra para errores y seguridad, etc.).

- `Archivos de configuración`: Asegurarse de que los archivos de configuración (application.properties o application.yml) estén bien estructurados y no contengan información sensible.

**Revisión de Controladores**
- `Manejo de rutas`: Verificar que las rutas estén bien definidas y sigan un patrón lógico y consistente.

- `Anotaciones correctas`: Asegurarse de que se estén utilizando las anotaciones correctas (@RestController, @GetMapping, @PostMapping, etc.).

```java
@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @PostMapping
    public ResponseEntity registrarTopico(@RequestBody @Valid DatosRegistroTopicoDTO datos) {
      // código oculto
    }

    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicoDTO>> listarTopicos(@PageableDefault(size = 10, sort = {"fechaCreacion"}) Pageable pageable) {
      // código oculto
    }

    @GetMapping("/{id}")
    public ResponseEntity detallarTopico(@PathVariable Long id) {
      // código oculto
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopicoDTO datos) {
      // código oculto
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
      // código oculto
    }
}
```

- `Validaciones de entradas`: Comprobar que se estén validando adecuadamente las entradas del usuario usando anotaciones como @Valid.

- `Manejo de excepciones`: Revisar cómo se están manejando las excepciones y si hay controladores de excepción globales (@ControllerAdvice).

```java
@RestControllerAdvice
public class TratadorDeErrores {
  // código oculto
}
```

**Revisión de Repositorios**
- `Uso de JPA/Hibernate`: Comprobar que se estén utilizando correctamente las interfaces de repositorio de Spring Data JPA.

```java
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
  // código oculto
}

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
  // código oculto
}
```

- `Consultas personalizadas`: Revisar las consultas JPQL o SQL personalizadas para asegurar que sean eficientes y seguras.

```java
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("""
            select t.activo
            from Topico t
            where t.id=:idTopico
            """)
    Boolean findActivoById(Long idTopico);
}
```

**Revisión de Modelos/Entidades**
- `Mapeo correcto`: Verificar que las entidades estén mapeadas correctamente a las tablas de la base de datos.

- `Validaciones`: Comprobar que las validaciones de campos (@NotNull, @Size, etc.) estén presentes y sean adecuadas.

- `Relaciones entre entidades`: Revisar las relaciones y asegurarse de que estén correctamente configuradas con las anotaciones correspondientes (@OneToMany, @ManyToOne, etc.).

```java
@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fechaCreacion;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    private Boolean activo;

    // código oculto
}
```

**Seguridad**
- `Autenticación y autorización`: Verificar que se estén utilizando mecanismos de seguridad adecuados (como Spring Security) para la autenticación y autorización de usuarios.

- `Protección contra CSRF`: Asegurarse de que la aplicación esté protegida contra ataques comunes como CSRF.

```java
@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //Le indiamos a Spring el tipo de sesion
                .and().authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

Realizar un code review detallado siguiendo estos pasos ayudará a garantizar que el API REST con Spring sea robusto, eficiente y mantenible.
