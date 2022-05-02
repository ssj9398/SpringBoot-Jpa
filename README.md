# SpringBoot-Jpa
-  이 저장소는 인프런 [초급~활용] 김영한의 스프링 부트와 JPA 실무 완전 정복 수업을 듣고 실습해본 코드를 저장하는 곳입니다!
-  강의 로드맵 https://www.inflearn.com/roadmaps/149
---------------
1. [자바 ORM 표준 JPA 프로그래밍 - 기본편]()

2. [실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발]()

3. [실전! 스프링 부트와 JPA 활용2 - API 개발과 성능 최적화]()

4. [실전! 스프링 데이터 JPA]()

5. [실전! Querydsl]()

# 스프링 부트와 AWS로 혼자 구현하는 웹 서비스
## 테스트코드
![image](https://user-images.githubusercontent.com/48196352/166220598-73e4a9bd-89a3-4cad-9930-5b97cfa61f6d.png)
### TDD란 
  - 테스트가 주도하는 개발 (테스트 코드를 먼저 작성하는 것부터 시작)
    - 테스트가 개발을 이끌어 나간다.
  - ```RED``` : 항상 실패하는 테스트를 먼저 작성
  - ```GREEN``` : 테스트에 통과하는 프로덕션 코드 작성
  - ```REFACTOR``` : 테스트가 통과하면 프로덕션 코드를 리팩토링

#### TDD를 왜 사용하는가?
- 개발 단계 초기에 문제를 발견하게 해준다.
- 추후에 코드를 리팩토링하거나 라이브러리 업그레이드 등에서 기존기능이 올바르게 작동하는지 확인할 수 있다.
- 기능에 대한 불확실성을 감소시켜준다.
- 시스템에 대한 실제 문서를 제공한다. 즉, 단위 테스트 자체가 문서로 사용할 수 있다.

#### 단위테스트를 배우기 전 개방방식
1. 코드작성
2. 프로그램 실행(TOMCAT)
3. Postman이나 수작업으로 직접 API 테스트 후
4. 요청 결과를 ```System.out.println()```으로 눈으로 검증
5. 결과가 다르면 다시 서버(TOMCAT)를 내리고 코드 수정


문제1. 매번 서버를 내렸다가 코드를 수정하고 반복해야한다.
- 테스트코드가 없다 보니 직접 확인 하는 방법 밖에 없다.

문제2. ```System.out.println()```을 통해 눈으로 검증
- 테스트코드를 작성하면 더이상 눈으로 검증하지 않고 자동검증 가능

문제3. 개발자가 만든 기능을 안전하게 보호
- 기존 A기능 외에 B기능이 추가 됬을 때 기존 A기능에 문제가 생길 수도 있는데 이를 테스트코드로 기존 기능이 잘 작동되는 것을 보장해준다.

### Controller 테스트 코드 작성하기
#### HelloControllerTest Class 작성

```java
package com.ssj.webservice.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
```

```java
package com.ssj.webservice.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void hello가_리턴된다() throws  Exception {
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }
}
```

- 코드설명
  1. @RunWith(SpringRunner.class)
  - 테스트를 진행할 때 JUint에 내장된 실행자 외에 다른 실행자를 실행
  - 여기서는 SpringRunner라는 스프링 실행자를 사용
  - 즉, 스프링 부트 테스트와 JUnit 사이에 연갈자 역할
  2. @WebMvcTest
  - 여러 스프링 테스트 어노테이션 중, Web(Spring MVC)에 집중할 수 있는 어노테이션
  - 선언할 경우 @Controller, @ControllerAdvice 등을 사용가능
  - 단, @Service, @Component, @Repository 등은 사용가능
  3. @Autowired
  - 스프링이 관리하는 빈(Bean)을 주입 받는다.
  4. private MockMvc mvc
  - 웹 API를 테스트할 때 사용
  - 스프링 MVC 테스트의 시작점
  - 이 클래스를 통해 HTTP GET,POST 등에 대한 API 테스트를 할 수 있다.
  5. mvc.perform(get("/hello"))
  - MockMvc를 통해 /hello 주소로 HTTP GET 요청
  - 체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다.
  6. .andExpect(status().isOk())
  - mvc.perform의 결과를 검증
  - HTTP Header의 Status를 검증
  - 우리가 흔히 알고 있는 200,404,500 등의 상태를 검증
  7. .andExpect(content().string(hello))
  - mvc.perform의 결과를 검증
  - 응답 본문의 내용을 검증