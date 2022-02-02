# 자바 ORM 표준 JPA 프로그래밍 - 기본편
# JPA 소개
## JPA란?
- Java Persistence API
- 자바 진영의 ORM 기술 표준

</br>

## ORM
- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로 설계
- 관계형 데이터베이스는 관계형 데이터베이스대로 설계
- ORM 프레임워크가 중간에서 매핑
- 대중적인 언어에는 대부분 ORM 기술이 존재

</br>

## JPA를 왜 사용해야 하는가?
- SQL 중심적인 개발에서 객체 중심으로 개발
- 생산성
- 유지보수
- 패러다임의 불일치 해결
- 성능
- 데이터 접근 추상화와 벤더 독립성
- 표준

</br>

## 생산성 - JPA와 CRUD
• 저장: jpa.persist(member)
• 조회: Member member = jpa.find(memberId)
• 수정: member.setName(“변경할 이름”)
• 삭제: jpa.remove(member)

## JPA와 패러다임의 불일치 해결
- JPA와 상속
- JPA와 연관관계
- JPA와 객체 그래프 탐색
- JPA와 비교하기

</br>

## JPA의 성능 최적화 기능
1. 1차 캐시와 동일성(identity) 보장
  1. 같은 트랜잭션 안에서는 같은 엔티티를 반환 - 약간의 조회 성능 향상
  2. DB Isolation Level이 Read Commit이어도 애플리케이션에서 Repeatable Read 보장
2. 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
  1. 트랜잭션을 커밋할 때까지 INSERT SQL을 모음
  2. JDBC BATCH SQL 기능을 사용해서 한번에 SQL 전송
3. 지연 로딩(Lazy Loading) 과 즉시 로딩
  1. 지연 로딩: 객체가 실제 사용될 때 로딩
  2. 즉시 로딩: JOIN SQL로 한번에 연관된 객체까지 미리 조회

</br>

----

# JPA 시작
## H2데이터베이스 설치와 실행
• 최고의 실습용 DB
• 가볍다.(1.5M)
• 웹용 쿼리툴 제공
• MySQL, Oracle 데이터베이스 시뮬레이션 기능
• 시퀀스, AUTO INCREMENT 기능 지원

</br>

## 데이터베이스 방언
### 방언 1
JPA는 특정 데이터베이스에 종속 X
• 각각의 데이터베이스가 제공하는 SQL 문법과 함수는 조금씩 다름
  • 가변 문자: MySQL은 VARCHAR, Oracle은 VARCHAR2
  • 문자열을 자르는 함수: SQL 표준은 SUBSTRING(), Oracle은 SUBSTR()
  • 페이징: MySQL은 LIMIT , Oracle은 ROWNUM
• 방언: SQL 표준을 지키지 않는 특정 데이터베이스만의 고유한 기능

</br>

### 방언 2
• hibernate.dialect 속성에 지정
  • H2 : org.hibernate.dialect.H2Dialect
  • Oracle 10g : org.hibernate.dialect.Oracle10gDialect
  • MySQL : org.hibernate.dialect.MySQL5InnoDBDialect
• 하이버네이트는 40가지 이상의 데이터베이스 방언 지원

</br>

## 엔티티 주의사항
• 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
• 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
• JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
</br>

## JPQL
• JPA를 사용하면 엔티티 객체를 중심으로 개발
• 문제는 검색 쿼리
• 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
• 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
• 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요
• JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
• SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
• JPQL은 엔티티 객체를 대상으로 쿼리
• SQL은 데이터베이스 테이블을 대상으로 쿼리
• 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
• SQL을 추상화해서 특정 데이터베이스 SQL에 의존X
• JPQL을 한마디로 정의하면 객체 지향 SQL
• JPQL은 뒤에서 아주 자세히 다룸
</br>

----

# 영속성관리
## JPA에서 가장 중요한 2가지
• 객체와 관계형 데이터베이스 매핑하기 (Object Relational Mapping)
• 영속성 컨텍스트

</br>

## 영속성 컨텍스트
• JPA를 이해하는데 가장 중요한 용어
• “엔티티를 영구 저장하는 환경”이라는 뜻
• EntityManager.persist(entity);

</br>

## 엔티티의 생명주기
- 비영속 (new/transient)
  - 영속성 컨텍스트와 전혀 관계가 없는 새로운 상태
- 영속 (managed)
  - 영속성 컨텍스트에 관리되는 상태
- 준영속 (detached)
  - 영속성 컨텍스트에 저장되었다가 분리된 상태
- 삭제 (removed)
  - 삭제된 상태

</br>

## 비영속
```java
//객체를 생성한 상태(비영속)
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
```

</br>

## 영속
```java
//객체를 생성한 상태(비영속)
Member member = new Member();
member.setId("member1");
member.setUsername(“회원1”);
EntityManager em = emf.createEntityManager();
em.getTransaction().begin();
//객체를 저장한 상태(영속)
em.persist(member);
```

</br>

## 준영속, 삭제
```java
//회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
em.detach(member); 
```

```java
//객체를 삭제한 상태(삭제)
em.remove(member);
```

</br>

## 영속성 컨텍스트의 이점
• 1차 캐시
• 동일성(identity) 보장
• 트랜잭션을 지원하는 쓰기 지연
(transactional write-behind)
• 변경 감지(Dirty Checking)
• 지연 로딩(Lazy Loading)

</br>

## 엔티티 조회, 1차 캐시
```java
//엔티티를 생성한 상태(비영속)
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
//엔티티를 영속
em.persist(member);
```

</br>

## 1차 캐시에서 조회
```java
Member member = new Member();
member.setId("member1");
member.setUsername("회원1");
//1차 캐시에 저장됨
em.persist(member);
//1차 캐시에서 조회
Member findMember = em.find(Member.class, "member1");
```

</br>

## 데이터베이스에서 조회
```java
Member findMember2 = em.find(Member.class, "member2");
```

</br>

## 영속 엔티티의 동일성 보장
```java
Member a = em.find(Member.class, "member1");
Member b = em.find(Member.class, "member1");
System.out.println(a == b); //동일성 비교 true
```
- 1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 
격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공

</br>

## 엔티티 등록
### 트랜잭션을 지원하는 쓰기 지연
```java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
//엔티티 매니저는 데이터 변경시 트랜잭션을 시작해야 한다.
transaction.begin(); // [트랜잭션] 시작
em.persist(memberA);
em.persist(memberB);
//여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
//커밋하는 순간 데이터베이스에 INSERT SQL을 보낸다.
transaction.commit(); // [트랜잭션] 커밋
```

</br>

## 엔티티 수정
### 변경감지
```java
EntityManager em = emf.createEntityManager();
EntityTransaction transaction = em.getTransaction();
transaction.begin(); // [트랜잭션] 시작
// 영속 엔티티 조회
Member memberA = em.find(Member.class, "memberA");
// 영속 엔티티 데이터 수정
memberA.setUsername("hi");
memberA.setAge(10);
//em.update(member) 이런 코드가 있어야 하지 않을까?
transaction.commit(); // [트랜잭션] 커밋
```

</br>

## 엔티티 삭제
```java
//삭제 대상 엔티티 조회
Member memberA = em.find(Member.class, “memberA");
em.remove(memberA); //엔티티 삭제
```

</br>

## 플러시
- 영속성 컨텍스트의 변경내용을 데이터베이스에 반영
- 영속성 컨텍스트를 비우지 않음
- 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
- 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화하면 됨

</br>

### 플러시 발생
• 변경 감지
• 수정된 엔티티 쓰기 지연 SQL 저장소에 등록
• 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송
(등록, 수정, 삭제 쿼리)

</br>

## 영속성 컨텍스트를 플러시하는 방법
• em.flush() - 직접 호출
• 트랜잭션 커밋 - 플러시 자동 호출
• JPQL 쿼리 실행 - 플러시 자동 호출

</br>

## 준영속 상태
• 영속 -> 준영속
• 영속 상태의 엔티티가 영속성 컨텍스트에서 분리(detached)
• 영속성 컨텍스트가 제공하는 기능을 사용 못함

</br>

## 준영속 상태로 만드는 방법
- em.detach(entity)
  - 특정 엔티티만 준영속 상태로 전환
- em.clear()
  - 영속성 컨텍스트를 완전히 초기화
- em.close()
  - 영속성 컨텍스트를 종료

----