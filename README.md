
# Spring Boot REST API 실습 - Recipe 관리 애플리케이션

이 프로젝트는 Spring Boot를 사용하여 간단한 Recipe(레시피) 정보를 관리하는 RESTful API를 구현하는 실습 예제입니다. JPA를 이용한 데이터베이스 연동, 기본적인 CRUD(Create, Read, Update, Delete) 기능, 전역 예외 처리, DTO 사용 등을 포함하고 있습니다.

## 주요 기능

* **Recipe CRUD:** 레시피 정보를 생성, 조회, 수정, 삭제하는 기능을 제공합니다.
    * 전체 레시피 목록 조회 (GET)
    * 새 레시피 추가 (POST)
    * 기존 레시피 정보 전체 수정 (PUT)
    * 기존 레시피 이름만 수정 (PATCH)
    * 특정 레시피 삭제 (DELETE)
* **RESTful API:** HTTP 메서드(GET, POST, PUT, PATCH, DELETE)와 상태 코드(200, 201, 204, 400 등)를 적절히 사용하여 REST 원칙을 따릅니다.
* **JPA 연동:** Spring Data JPA를 사용하여 MySQL 데이터베이스와 상호작용합니다.
* **DTO 사용:** `RecipeDTO`를 사용하여 클라이언트 요청 데이터를 효율적으로 처리합니다.
* **전역 예외 처리:** `@RestControllerAdvice`와 `@ExceptionHandler`를 사용하여 `BadRequestException` 등 특정 예외를 중앙에서 처리하고 일관된 오류 응답을 제공합니다.
* **CORS 지원:** `@CrossOrigin` 어노테이션을 사용하여 다른 도메인에서의 API 요청을 허용합니다 (개발 편의 목적).
* **간단한 Frontend:** `index.html` 파일을 통해 API를 테스트하고 결과를 확인할 수 있는 기본적인 웹 페이지를 제공합니다.

## 기술 스택

* **언어:** Java 17
* **프레임워크:** Spring Boot 3.4.4
    * Spring Web (MVC, REST)
    * Spring Data JPA
* **데이터베이스:** MySQL
* **ORM:** Hibernate
* **빌드 도구:** Gradle
* **라이브러리:**
    * Lombok: 보일러플레이트 코드 감소
    * MySQL Connector/J: MySQL 드라이버
* **Frontend:** HTML, JavaScript (Fetch API)

## 프로젝트 구조 (주요 패키지)

```
src
├── main
│   ├── java
│   │   └── org
│   │       └── example
│   │           └── bootrestapi
│   │               ├── controller       # API 요청 처리 (RecipeController, GlobalExceptionHandler)
│   │               ├── model
│   │               │   ├── dto          # 데이터 전송 객체 (RecipeDTO)
│   │               │   ├── entity       # JPA 엔티티 (Recipe)
│   │               │   └── repository   # Spring Data JPA 리포지토리 (RecipeRepository)
│   │               ├── service          # 비즈니스 로직 (RecipeService, RecipeServiceJPAImpl)
│   │               └── BootRestApiApplication.java # Spring Boot 메인 애플리케이션
│   └── resources
│       ├── static
│       │   └── index.html       # 테스트용 Frontend HTML
│       ├── application.yml      # 메인 설정 파일
│       └── application-dev.yml  # 개발 환경 설정 파일 (DB 정보 등)
└── test                     # 테스트 코드 (포함되지 않음)
build.gradle                 # Gradle 빌드 설정 파일
```

## 설정 및 실행 방법

### 사전 요구 사항

* JDK 17 이상 설치
* Gradle 설치 (또는 프로젝트 내 gradlew 사용)
* MySQL 데이터베이스 서버 실행 중

### 데이터베이스 설정

1.  MySQL 데이터베이스에 애플리케이션에서 사용할 데이터베이스 스키마(예: `recipe_db`)를 생성합니다.
2.  `src/main/resources/application-dev.yml` 파일에서 데이터베이스 연결 정보를 설정합니다. 환경 변수를 사용하는 것이 권장됩니다.
    * `DB_URL`: 예) `jdbc:mysql://localhost:3306/recipe_db?useSSL=false&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true`
    * `DB_USERNAME`: MySQL 사용자 이름
    * `DB_PASSWORD`: MySQL 비밀번호
3.  `application-dev.yml`의 `spring.jpa.hibernate.ddl-auto` 값이 `none`으로 설정되어 있으므로, 애플리케이션 실행 전에 `recipe` 테이블을 직접 생성해야 합니다. (필요시 `create` 또는 `update`로 변경하여 자동 생성 가능)
    ```sql
    -- 예시 테이블 생성 SQL (MySQL 기준)
    CREATE TABLE recipe (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        description VARCHAR(255)
    );
    ```

### 애플리케이션 빌드 및 실행

1.  **프로젝트 빌드 (선택 사항):**
    ```bash
    ./gradlew build
    ```
2.  **애플리케이션 실행:**
    * **Gradle 사용:**
        ```bash
        ./gradlew bootRun --args='--spring.profiles.active=dev'
        ```
    * **Jar 파일 실행:**
        ```bash
        java -jar build/libs/boot-rest-api-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
        ```
        (Jar 파일 이름은 `build.gradle` 설정에 따라 다를 수 있습니다.)

애플리케이션은 기본적으로 `8080` 포트에서 실행됩니다.

## API 엔드포인트

* `GET /api/recipe`: 모든 레시피 목록을 조회합니다.
    * 응답: `List<Recipe>` (JSON 형식)
* `POST /api/recipe`: 새로운 레시피를 추가합니다.
    * 요청 본문 (Request Body): `{"name": "레시피 이름", "description": "레시피 설명"}` (JSON 형식 - `RecipeDTO`)
    * 성공 응답: 상태 코드 `201 Created`, 생성된 `Recipe` 객체 (JSON 형식)
* `PUT /api/recipe/{id}`: 지정된 ID의 레시피 정보를 전체 수정합니다.
    * 요청 본문 (Request Body): `{"name": "수정된 이름", "description": "수정된 설명"}` (JSON 형식 - `RecipeDTO`)
    * 성공 응답: 상태 코드 `201 Created` (또는 `200 OK`), 수정된 `Recipe` 객체 (JSON 형식)
* `PATCH /api/recipe/{id}/name`: 지정된 ID의 레시피 이름을 수정합니다.
    * 요청 본문 (Request Body): `{"name": "새로운 이름"}` (JSON 형식 - `RecipeDTO`)
    * 성공 응답: 상태 코드 `201 Created` (또는 `200 OK`), 수정된 `Recipe` 객체 (JSON 형식)
* `DELETE /api/recipe/{id}`: 지정된 ID의 레시피를 삭제합니다.
    * 성공 응답: 상태 코드 `204 No Content`

## 예외 처리

* `GlobalExceptionHandler` 클래스가 `@RestControllerAdvice`를 통해 전역적으로 예외를 처리합니다.
* 현재 `BadRequestException` 발생 시 클라이언트에게 HTTP 상태 코드 `400 Bad Request`와 함께 `"Exception Handler : [오류 메시지]"` 형식의 응답 본문을 반환합니다.
    * 예: 레시피 저장 시 이름(`name`)이 비어있으면 `RecipeServiceJPAImpl`에서 `BadRequestException`이 발생하여 이 핸들러에 의해 처리됩니다.

## Frontend (`index.html`)

* `src/main/resources/static/index.html` 파일을 웹 브라우저에서 열거나, 애플리케이션 실행 후 `http://localhost:8080/index.html` 주소로 접속하여 사용할 수 있습니다.
* 이 페이지는 JavaScript `Workspace` API를 사용하여 `/api/recipe` 엔드포인트와 비동기적으로 통신합니다.
* 레시피 이름과 설명을 입력하고 '등록' 버튼을 누르면 `POST /api/recipe` 요청을 보냅니다.
* 페이지 로드 시 및 레시피 등록 후 `GET /api/recipe`를 호출하여 현재 레시피 목록을 JSON 형태로 화면에 표시합니다.
* `RecipeController`의 `@CrossOrigin` 어노테이션 덕분에 `index.html` 파일이 로컬 파일 시스템(`file://`)에서 열렸을 때도 `localhost:8080` API 서버와 통신할 수 있습니다.

이 `README.md` 파일은 제공해주신 코드의 구조와 기능을 설명하고, 프로젝트 설정 및 실행 방법을 안내합니다. 필요에 따라 내용을 더 추가하거나 수정하여 사용하시면 됩니다.