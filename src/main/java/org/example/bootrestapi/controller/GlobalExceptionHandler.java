// 이 클래스가 속한 패키지를 선언합니다. 주로 기능별 또는 계층별로 구성됩니다.
package org.example.bootrestapi.controller;

// 필요한 클래스들을 임포트합니다.
import org.apache.coyote.BadRequestException; // 처리할 예외 클래스 임포트 (Apache Coyote의 BadRequestException)
import org.springframework.http.HttpStatus; // HTTP 상태 코드를 사용하기 위한 임포트 (예: 400 Bad Request)
import org.springframework.http.ResponseEntity; // HTTP 응답(상태 코드, 헤더, 본문 포함)을 표현하는 클래스 임포트
import org.springframework.web.bind.annotation.ExceptionHandler; // 특정 예외를 처리하는 핸들러 메서드를 지정하기 위한 어노테이션 임포트
import org.springframework.web.bind.annotation.RestControllerAdvice; // 모든 @RestController에 대한 전역 설정을 위한 어노테이션 임포트 (AOP 기반)



/**
 * RestControllerAdvice 어노테이션은 이 클래스가 애플리케이션의 모든 @RestController 에서 발생하는
 * 예외를 중앙에서 처리하거나, 공통 모델 속성을 추가하는 등의 역할을 수행함을 나타냅니다.
 * AOP(관점 지향 프로그래밍)의 개념을 사용하여 컨트롤러 로직과 예외 처리 로직을 분리합니다.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * ExceptionHandler 어노테이션은 특정 예외 클래스(여기서는 BadRequestException.class)가 발생했을 때
     * 해당 메서드가 호출되어 예외를 처리하도록 지정합니다.
     * 컨트롤러 메서드 실행 중 BadRequestException이 던져지고(throw) 잡히지(catch) 않으면 이 핸들러가 동작합니다.
     *
     * @param e 발생한 BadRequestException 객체. 예외 관련 정보(메시지 등)를 포함합니다.
     * @return 클라이언트에게 반환될 HTTP 응답 객체 (ResponseEntity).
     * 여기서는 응답 본문으로 String 타입을 사용합니다.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        // ResponseEntity 객체를 생성하여 클라이언트에게 응답합니다.
        // 첫 번째 인자: 응답 본문(Body) - "Exception Handler : " 문자열과 실제 예외 메시지를 조합합니다.
        // 두 번째 인자: HTTP 상태 코드 - HttpStatus.BAD_REQUEST (400)를 설정하여 클라이언트 요청 오류임을 알립니다.
        return new ResponseEntity<>("Exception Handler : " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 필요하다면 다른 종류의 예외를 처리하기 위해 @ExceptionHandler 메서드를 추가할 수 있습니다.
    /*
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException e) {
        // NullPointerException 발생 시 처리 로직
        return new ResponseEntity<>("Internal Server Error: Null value encountered", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class) // 가장 상위 타입의 Exception을 처리 (다른 특정 핸들러에서 잡히지 않은 모든 예외)
    public ResponseEntity<String> handleGenericException(Exception e) {
        // 일반적인 예외 처리 로직
        return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    */
}

/*
이 코드는 Spring Boot 애플리케이션에서 BadRequestException이 발생하면,
애플리케이션 전체에서 이를 감지하여 특정 로직
(여기서는 "Exception Handler : [오류메시지]" 라는 응답 본문과 HTTP 400 상태 코드 반환)을
수행하는 **전역 예외 처리기(Global Exception Handler)**를 정의하는 것입니다.
이를 통해 예외 처리 코드를 중앙화하고 일관된 오류 응답을 클라이언트에게 제공할 수 있습니다.

 */