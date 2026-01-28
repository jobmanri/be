# 프로젝트 개요

이 문서는 새로운 에이전트가 레포에 합류했을 때 가장 먼저 읽고 전체 구조를
파악할 수 있도록 작성한 가이드입니다.

## 기술 스택

- **런타임:** Spring Boot 3.5.x / Java 21
- **빌드:** Gradle (`./gradlew bootRun`, `./gradlew test`)
- **영속성:** 운영은 MySQL, 테스트는 H2 + Testcontainers
- **API:** Spring MVC 기반 REST + SpringDoc Swagger UI (`/api-test`)
- **인증:** JWT 커스텀 구현 + Spring Security 암호화(`application-*.yaml`에서
  비밀값 주입)

## 전체 아키텍처

`src/main/java/com/example/bak` 아래에 도메인별 패키지가 구성돼 있으며,
Ports & Adapters + CQRS 패턴을 따릅니다.

1. **도메인 패키지** (`auth`, `comment`, `community`, `company`, `feed`,
   `privatemessage`, `user`, `global` 등) 는 엔티티와 비즈니스 규칙을 소유합니다.
   각 도메인은 보통 `application`, `infra`, `presentation`, `domain` 하위 패키지를
   가집니다.
2. **Application 계층**은 커맨드/쿼리 서비스를 통해 Use Case 를 오케스트레이션하며
   포트 인터페이스에만 의존합니다. `application.command`는 상태 변경,
   `application.query`는 읽기 전용 서비스를 제공합니다.
3. **Port** (`application.*.port`)는 서비스가 기대하는 계약을 정의합니다.
   예: ValidationPort, CommandPort, QueryPort 등.
4. **Adapter** (`infra.*`)는 포트를 구현합니다. 쓰기 경로는 JPA, 읽기 경로는
   JDBC/QueryDSL 기반 구현이 많으며 JWT, 패스워드 인코더 등 외부 연동도 포함됩니다.
   스테레오타입은 주로 `@Component`/`@Repository`입니다.
5. **Presentation 계층** (`presentation`)은 REST 컨트롤러와 DTO, 그리고 Swagger
   명세(`presentation.swagger`)를 제공합니다.

`global` 패키지에는 예외, 응답 래퍼, 보안 필터/어노테이션, 공통 유틸이 모여 있습니다.

## 데이터베이스 및 설정

- 주요 설정은 `src/main/resources` 의 `application-local.yaml` / `application-prod.yaml`에
  있으며 DB 접속 정보와 JWT 키는 환경 변수에서 주입합니다.
- 엔티티는 JPA `@Entity` 를 사용합니다. 단순 존재 여부 확인을 위해
  `FeedValidationPort`, `CommunityValidationPort` 같은 전용 포트를 두는 패턴을 사용합니다.
- 통합 테스트는 `src/test/resources/sql/**` 의 스키마/데이터 스크립트를 활용합니다.

## 테스트 전략

- **계층별 분리 목적**: 애플리케이션 레이어와 인프라 레이어를 명확히 나누기 위해
  단위 테스트에서는 스텁/페이크 저장소를 사용하고, 실제 DB 의존성은 통합 테스트에서만
  검증합니다. 이렇게 하면 비즈니스 로직 회귀를 빠르게 잡고, 인프라 이슈는 별도의
  단계에서 확인할 수 있습니다.
- **Stub 저장소 활용**: `FeedRepositoryStub`처럼 메모리 기반 구현을 각 테스트 클래스에서
  새로 생성하여 사용합니다. 여러 스텁 인스턴스가 존재해도 서로 상태를 공유하지 않아
  “DB 이중화”나 데이터 레이스가 생기지 않는다는 점이 장점입니다. 실제 DB를 흉내 내되
  독립된 저장소를 각 테스트가 갖게 하여 테스트 간 간섭을 제거합니다.
- **통합 테스트**: Testcontainers(MySQL) + JDBC 리포지토리를 사용해 실제 SQL 경로를
  검증합니다. `src/test/resources/sql` 아래 스키마/데이터 스크립트를 활용하며,
  `./gradlew test` 실행 시 자동으로 컨테이너가 올라옵니다.

## 도메인 예시 흐름

- **Feed 생성 플로우**
  1. `FeedController` → `FeedCommandService.createFeed()` 호출
  2. `CommunityValidationPort`로 커뮤니티 존재 여부 확인
  3. `FeedCommandPort.save()`를 통해 JPA 어댑터(`FeedCommandAdapter`)가 DB에 저장
- **Comment 작성 플로우**
  1. `CommentCommandService.createComment()`가 `FeedValidationPort.existsById()`로 피드 존재만 확인
  2. `ProfileDataPort`에서 작성자 스냅샷 조회 후 `CommentCommandPort.save()` 호출
  3. 댓글 수정/삭제 시 `Comment` 애그리거트 메서드(`isWrittenBy`, `updateComment`)로 권한 검증
- **User/Profile 참고**
  - User가 애그리거트 루트이며 Profile은 전용 저장소를 통해 명시적으로 관리합니다.
  - 추후 SecondaryTable/Value Object 등으로 합칠 계획이 있다면 Ports & Adapters 경계를 유지한 채
    설계를 재검토해야 합니다.


## 작업 가이드

- Ports & Adapters 경계를 지켜 Application 서비스는 반드시 포트에만 의존하도록 합니다.
- 핵심 검증/인가 로직은 가능하면 애그리거트 내부 메서드(예: `Feed.validateAuthor`)로 이동합니다.
- 새로운 조회 기능을 추가할 때는 기존 CQRS 패턴을 따른다: 커맨드는 JPA, 쿼리는 JDBC/QueryDSL.
- 새 엔드포인트는 `presentation` 계층에 추가하고 Swagger 인터페이스를 갱신합니다.
- 파일을 수정하기 전, 해당 디렉터리에 `AGENTS.md` 등 추가 지침이 있는지 확인합니다.
- 어떤 변경이든 사이드 이펙트를 먼저 점검하세요. 서비스/엔티티 수정은 인프라 계층(JPA,
  JDBC, SQL 스크립트), 테스트 픽스처, DTO, Swagger 명세까지 파급될 수 있습니다. 커맨드 경로에
  SQL/JDBC 구현이 있는 경우, 비즈니스 로직을 고쳤다면 대응하는 쿼리도 꼭 함께 검토하세요.
- 동일한 이유로 단위 테스트 외에도 필요한 경우 통합 테스트나 SQL 스크립트를 갱신해
  런타임 오류를 예방합니다.
- 변경을 마친 뒤에는 반드시 “왜 이렇게 했는지”를 설명할 근거를 정리하세요. 대안 대비
  장단점과 트레이드오프를 명확히 언급하면 리뷰어와 후속 작업자가 의사결정을 빠르게
  추적할 수 있습니다.

## 유용한 명령

- `./gradlew bootRun` — 애플리케이션 실행 (DB/환경 변수 필요)
- `./gradlew test` — 전체 단위/통합 테스트 실행
- `./run.sh` — 로컬 실행용 스크립트(존재할 경우)

이 문서를 기준으로 각 도메인 패키지를 살피면 공통 패턴을 빠르게 파악할 수 있습니다.
구조가 크게 바뀌면 이 파일도 함께 업데이트해주세요.

---

## 구현 체크리스트

새로운 기능을 추가하거나 기존 기능을 수정할 때 아래 항목을 참고하세요.

### 아키텍처 및 패키지 구조

- [ ] **도메인 패키지 구조 준수**: 새 도메인은 `domain`, `application`, `infra`, `presentation` 하위 패키지로 구성
- [ ] **Application 계층 분리**: `application.command`(상태 변경)와 `application.query`(읽기 전용)로 명확히 구분
- [ ] **Port 인터페이스 정의**: Application 계층은 Port 인터페이스에만 의존 (구체 구현 참조 금지)
- [ ] **Adapter 구현**: Infra 계층에서 Port 인터페이스를 구현 (`@Component` 또는 `@Repository` 사용)
- [ ] **CQRS 패턴 준수**: Command는 JPA 기반, Query는 JDBC/QueryDSL 기반 구현

### 도메인 계층 (Domain Layer)

- [ ] **엔티티 선언**: `@Entity` 어노테이션 사용, JPA 스펙 준수
- [ ] **팩토리 메서드**: `create()` 정적 메서드로 엔티티 생성
- [ ] **테스트 인스턴스**: 필요시 `testInstance()` 메서드 제공 (ID 포함 생성)
- [ ] **비즈니스 로직**: 핵심 검증/인가 로직은 애그리거트 내부 메서드로 구현 (예: `validateAuthor()`, `isWrittenBy()`)
- [ ] **불변성 보장**: 생성자는 `protected`, Lombok `@NoArgsConstructor(access = AccessLevel.PROTECTED)` 사용
- [ ] **도메인 규칙**: 상태 변경은 도메인 메서드를 통해서만 가능하도록 캡슐화

### Application 계층 (Application Layer)

- [ ] **서비스 어노테이션**: `@Service` 어노테이션 사용
- [ ] **트랜잭션 관리**: Command 서비스는 `@Transactional` 적용
- [ ] **Port 의존성**: 생성자 주입으로 Port만 의존 (`@RequiredArgsConstructor` 활용)
- [ ] **Validation Port 활용**: 존재 여부 확인용 전용 Port 사용 (예: `CommunityValidationPort`, `FeedValidationPort`)
- [ ] **예외 처리**: `BusinessException`과 `ErrorCode` 사용
- [ ] **DTO 반환**: Application 계층은 DTO로 결과 반환 (엔티티 직접 노출 금지)

### Infra 계층 (Infrastructure Layer)

- [ ] **Adapter 네이밍**: `*Adapter` 또는 `*Adaptor` 네이밍 규칙 준수
- [ ] **Repository 인터페이스**: JPA Repository는 `JpaRepository` 상속
- [ ] **Query 구현**: 복잡한 조회는 QueryDSL 또는 JDBC Template 사용
- [ ] **외부 연동**: JWT, 암호화 등 외부 의존성도 Adapter로 추상화
- [ ] **Component 등록**: `@Component` 또는 `@Repository` 어노테이션으로 빈 등록

### Presentation 계층 (Presentation Layer)

- [ ] **컨트롤러 어노테이션**: `@RestController` 사용
- [ ] **요청/응답 DTO**: Request/Response DTO 명확히 정의
- [ ] **Swagger 문서화**: `presentation.swagger` 패키지에 Swagger 인터페이스 작성
- [ ] **API 경로**: RESTful 규칙 준수
- [ ] **보안 어노테이션**: 필요시 인증/인가 어노테이션 적용
- [ ] **예외 처리**: Global Exception Handler로 일관된 에러 응답 제공

### 테스트 전략

- [ ] **단위 테스트**: Application 계층은 Stub/Fake 저장소로 테스트
- [ ] **Stub 구현**: 각 테스트 클래스마다 독립적인 Stub 저장소 생성 (상태 공유 방지)
- [ ] **통합 테스트**: Infra 계층은 Testcontainers + 실제 DB로 검증
- [ ] **테스트 데이터**: `src/test/resources/sql` 스키마/데이터 스크립트 활용
- [ ] **테스트 격리**: 테스트 간 데이터 간섭이 없도록 독립성 보장
- [ ] **빌드 확인**: `./gradlew test` 실행하여 전체 테스트 통과 확인

### 코드 컨벤션

- [ ] **Lombok 활용**: `@Getter`, `@RequiredArgsConstructor`, `@NoArgsConstructor` 등 적극 활용
- [ ] **네이밍**: 명확하고 의도가 드러나는 이름 사용 (Port, Adapter, Service 등)
- [ ] **포맷팅**: 일관된 코드 스타일 유지
- [ ] **주석 최소화**: 코드로 의도를 표현, 필요한 경우에만 주석 추가

### 변경 영향 분석

- [ ] **엔티티 변경 시**: JPA 매핑, DTO, 테스트 픽스처, SQL 스크립트 함께 수정
- [ ] **비즈니스 로직 변경 시**: 커맨드/쿼리 양쪽 경로 모두 검토
- [ ] **API 변경 시**: Request/Response DTO, Swagger 문서, 컨트롤러 모두 갱신
- [ ] **Port 변경 시**: 모든 Adapter 구현체와 테스트 Stub 동시 수정
- [ ] **사이드 이펙트 점검**: 변경 전 영향 범위 파악 및 회귀 테스트 실행

### 문서화

- [ ] **변경 이유 기록**: 왜 이렇게 구현했는지 근거 정리
- [ ] **트레이드오프 명시**: 대안 대비 장단점 문서화
- [ ] **README 갱신**: 구조 변경 시 PROJECT_OVERVIEW.md 업데이트
- [ ] **도메인 문서**: 필요시 각 도메인 디렉터리에 AGENTS.md 추가

### 배포 및 환경 설정

- [ ] **환경 변수**: 민감 정보는 `application-*.yaml`에서 환경 변수로 주입
- [ ] **프로파일 분리**: local/prod 프로파일 설정 명확히 구분
- [ ] **DB 마이그레이션**: 스키마 변경 시 운영 DB 영향 검토
- [ ] **빌드 검증**: `./gradlew bootRun` 로컬 실행 테스트
