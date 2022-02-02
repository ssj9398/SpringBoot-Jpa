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

