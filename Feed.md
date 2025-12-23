## 피드 개선해야할 점

1. 전체 피드 중 N개 추출해서 메인페이지에서 보여주는 형태로 변경 필요(여기서 DTO는 FeedDetail으로 사용)
2. 각 커뮤니티마다 피드가 있을텐데 커뮤니티별로 피드는 무한 스크롤 하는 형태로 변경 필요
3. 피드 Summary에 좋아요 수, 댓글 수 추가 필요. 그리고 본문 내용을 일부 보여주는 형태로 변경 필요
4. 좋아요는 아직 구현이 안되어있는데 목 업 데이터라도 넣어서 좋아요 수를 보여줄 수 있도록 변경 필요
5. 블라인드의 페이지 형태를 따르고 싶음(메인페이지에서는 각 커뮤니티별로 N개 피드 노출(근데 여기서는 본문이 없고 오로지 제목만 있는 형태), 커뮤니티 페이지에서는 해당
   커뮤니티 피드 무한스크롤 형태)

---

## 구현 로드맵

### Phase 1: 기존 DTO 확장 및 정리

#### 1.1 FeedSummary / FeedDetail 필드 확장
- [ ] **파일**: `feed/application/query/dto/FeedSummary.java`, `feed/application/query/dto/FeedDetail.java`
- [ ] **작업 내용**:
  - `FeedSummary`에 `contentPreview`, `likeCount`, `commentCount` 필드를 추가하여 목록 조회만으로도 핵심 정보를 노출
  - `FeedDetail`에 `likeCount`, `commentCount`를 추가하여 상세 페이지에서도 동일한 수치를 재사용
  - DTO 파일은 기존처럼 개별 클래스로 유지하고, 생성자/레코드 필드 순서를 맞춰 추후 유지보수 비용 최소화
- [ ] **고려사항**:
  - `contentPreview`는 서비스 계층에서 100자 내외로 잘라 전달 (DB에서 substring 하지 않음)
  - 새로운 필드가 추가되므로 Lombok/Record 생성자 변경에 따른 컴파일 오류 포인트 점검

#### 1.2 TitleOnly DTO 및 레이어별 적용
- [ ] **파일**: `feed/application/query/dto/FeedTitleOnly.java` (신규), 관련 Service/Controller
- [ ] **작업 내용**:
  - 메인페이지 제목 전용 조회를 위해 `FeedTitleOnly` DTO를 신설 (필드: `id`, `title`, `likeCount`, `commentCount`, `CommunitySummary` 등)
  - Presentation 계층 응답 DTO에서도 `FeedTitleOnly`를 그대로 활용하거나 필요한 필드만 매핑
  - QueryPort/Service/Adapter/RowMapper가 새 DTO를 반환하도록 조정
- [ ] **고려사항**:
  - 기존 DTO 네이밍은 유지하고, 새 DTO가 추가되더라도 import 충돌이 없도록 패키지 경로를 명확히 유지
  - 테스트 스텁에서도 새 DTO를 생성해주어야 하므로 Fixture 헬퍼 추가 검토

### Phase 2: Like(좋아요) 도메인 구현

#### 2.1 Like 엔티티 생성 (임시 Mock 데이터용)
- [ ] **파일**: `FeedLike.java` (신규 생성)
- [ ] **위치**: `feed/domain/`
- [ ] **작업 내용**:
  - Feed와 User의 다대다 관계 표현
  - `id`, `feedId`, `userId`, `createdAt` 필드
  - JPA `@Entity` 선언
- [ ] **고려사항**: 추후 독립 도메인으로 분리 가능성 염두

#### 2.2 Like Repository 인터페이스
- [ ] **파일**: `FeedLikeRepository.java` (신규 생성)
- [ ] **위치**: `feed/domain/`
- [ ] **작업 내용**: `JpaRepository` 상속, `countByFeedId()` 메서드 정의

#### 2.3 Like 집계 쿼리 추가
- [ ] **파일**: `FeedJdbcRepositoryImpl.java` (수정)
- [ ] **작업 내용**:
  - FeedSummary 조회 시 LEFT JOIN으로 좋아요 수 집계
  - SQL: `SELECT COUNT(fl.id) as like_count FROM feed_likes fl WHERE fl.feed_id = f.id`
  - RowMapper에 likeCount 매핑 추가

### Phase 3: Query Port 및 Service 확장

#### 3.1 FeedQueryPort 메서드 추가
- [ ] **파일**: `FeedQueryPort.java` (수정)
- [ ] **작업 내용**:
  - `findTopNByOrderByCreatedAtDesc(int limit)` - 메인페이지용 최신 N개 조회
  - `findByCommunityId(Long communityId, Pageable pageable)` - 커뮤니티별 피드 조회
  - `findTitleOnlyByCommunityId(Long communityId, int limit)` - 커뮤니티별 제목만 N개 조회
  - `findAllTitleOnlyGroupedByCommunity(int limitPerCommunity)` - 메인페이지용 커뮤니티별 N개씩 조회

#### 3.2 FeedQueryService 메서드 추가
- [ ] **파일**: `FeedQueryService.java` (수정)
- [ ] **작업 내용**:
  - `getMainPageFeeds(int limit)` - 메인페이지용 FeedDetail 리스트 반환
  - `getFeedsByCommunity(Long communityId, int page, int size)` - 커뮤니티별 무한 스크롤용
  - `getFeedTitlesGroupedByCommunity(int limitPerCommunity)` - 블라인드 스타일 메인페이지용
- [ ] **비즈니스 로직**:
  - contentPreview는 본문 앞 100자로 제한 (서비스 레이어에서 처리)
  - null 처리 및 예외 처리 추가

### Phase 4: Adapter 구현

#### 4.1 JDBC Query Adapter 수정
- [ ] **파일**: `FeedJdbcRepositoryImpl.java` (수정)
- [ ] **작업 내용**:
  - 좋아요 수 집계 쿼리 추가 (LEFT JOIN `feed_likes`)
  - 커뮤니티별 조회 쿼리 구현
  - 메인페이지용 최신 N개 조회 쿼리
  - 커뮤니티별 그룹핑 쿼리 (WITH 절 또는 서브쿼리 활용)

#### 4.2 RowMapper 수정
- [ ] **파일**: `FeedSummaryRowMapper.java` (수정)
- [ ] **작업 내용**:
  - `likeCount` 컬럼 매핑 추가
  - `contentPreview` 컬럼 매핑 추가 (SUBSTRING 함수 활용)
- [ ] **파일**: `FeedTitleOnlyRowMapper.java` (신규 생성)
- [ ] **작업 내용**: 제목만 조회하는 RowMapper 구현

### Phase 5: Presentation 계층 API 추가

#### 5.1 FeedController 엔드포인트 추가
- [ ] **파일**: `FeedController.java` (수정)
- [ ] **작업 내용**:
  - `GET /api/v1/feeds/main?limit=10` - 메인페이지용 FeedDetail 리스트
  - `GET /api/v1/feeds/main/titles?limit=5` - 블라인드 스타일 메인페이지용 (커뮤니티별 N개)
  - `GET /api/v1/communities/{communityId}/feeds?page=0&size=20` - 커뮤니티별 무한 스크롤
  - 기존 `GET /api/v1/feeds` 유지 (전체 피드 조회)

#### 5.2 Response DTO 정리
- [ ] **파일**: 기존 DTO 활용 또는 Presentation 계층 전용 Response 생성
- [ ] **작업 내용**:
  - `MainPageFeedsResponse` - FeedDetail 리스트 래핑
  - `CommunityFeedsResponse` - 커뮤니티별 피드 + 페이징 정보
  - `MainPageTitlesResponse` - 커뮤니티별 FeedTitleOnly 그룹화

#### 5.3 Swagger 문서 갱신
- [ ] **파일**: `FeedSwagger.java` (수정)
- [ ] **작업 내용**:
  - 새로운 API 엔드포인트 인터페이스 정의
  - 요청/응답 스키마 예시 추가
  - 파라미터 설명 추가

### Phase 6: 데이터베이스 및 Mock 데이터

#### 6.1 Like 테이블 스키마 추가
- [ ] **파일**: `schema.sql` (테스트용)
- [ ] **위치**: `src/test/resources/sql/`
- [ ] **작업 내용**:
  ```sql
  CREATE TABLE feed_likes (
      id BIGINT AUTO_INCREMENT PRIMARY KEY,
      feed_id BIGINT NOT NULL,
      user_id BIGINT NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      UNIQUE KEY uk_feed_user (feed_id, user_id)
  );
  ```
- [ ] **운영 DB**: 동일 스키마 적용 (마이그레이션 스크립트 작성)

#### 6.2 Mock 데이터 생성
- [ ] **파일**: `data.sql` (테스트용)
- [ ] **위치**: `src/test/resources/sql/`
- [ ] **작업 내용**:
  - 각 피드당 랜덤 좋아요 수 삽입 (0~50개)
  - 테스트 시나리오별 데이터 준비
- [ ] **고려사항**: 실제 운영 환경에서는 좋아요 기능 완전 구현 전까지 Mock 유지

### Phase 7: 테스트 작성

#### 7.1 단위 테스트 (Application Layer)
- [ ] **파일**: `FeedQueryServiceTest.java` (수정)
- [ ] **작업 내용**:
  - `getMainPageFeeds()` 테스트
  - `getFeedsByCommunity()` 테스트
  - `getFeedTitlesGroupedByCommunity()` 테스트
  - Stub 저장소에서 좋아요 수 모킹
- [ ] **파일**: `FeedQueryPortStub.java` (신규 생성)
- [ ] **작업 내용**: 메모리 기반 Stub 구현

#### 7.2 통합 테스트 (Infrastructure Layer)
- [ ] **파일**: `FeedJdbcRepositoryImplTest.java` (신규 또는 수정)
- [ ] **작업 내용**:
  - Testcontainers MySQL로 실제 쿼리 검증
  - 좋아요 집계 쿼리 정확성 테스트
  - 커뮤니티별 조회 쿼리 테스트
  - 페이징 및 정렬 검증

#### 7.3 API 테스트
- [ ] **파일**: `FeedControllerTest.java` (수정)
- [ ] **작업 내용**:
  - 새로운 엔드포인트 MockMvc 테스트
  - 응답 구조 검증
  - 페이징 파라미터 검증

### Phase 8: 성능 최적화 및 리팩토링

#### 8.1 쿼리 최적화
- [ ] N+1 문제 확인 및 해결 (JOIN FETCH 또는 배치 조회)
- [ ] 인덱스 추가: `feeds(community_id, created_at)`, `feed_likes(feed_id)`
- [ ] 실행 계획 분석 (EXPLAIN)

#### 8.2 캐싱 전략 (선택사항)
- [ ] 메인페이지 피드 리스트 캐싱 (Redis 또는 Spring Cache)
- [ ] 좋아요 수 집계 캐싱
- [ ] TTL 설정 (예: 5분)

#### 8.3 코드 리뷰 및 리팩토링
- [ ] 중복 코드 제거
- [ ] 매직 넘버 상수화 (본문 미리보기 길이 등)
- [ ] 에러 메시지 일관성 검토

### Phase 9: 문서화 및 배포 준비

#### 9.1 API 문서 업데이트
- [ ] Swagger UI 확인 (`/api-test`)
- [ ] 엔드포인트별 예시 응답 추가
- [ ] 에러 케이스 문서화

#### 9.2 README 갱신
- [ ] 새로운 기능 설명 추가
- [ ] API 사용 예시 추가
- [ ] 좋아요 기능 Mock 데이터 사용 안내

#### 9.3 배포 체크리스트
- [ ] `./gradlew test` 전체 테스트 통과 확인
- [ ] `./gradlew bootRun` 로컬 실행 검증
- [ ] DB 마이그레이션 스크립트 준비
- [ ] 환경 변수 설정 확인
- [ ] 롤백 계획 수립

---

## 구현 우선순위

### 🔴 High Priority (우선 구현)
1. Phase 1.1 - FeedSummary DTO 확장 (좋아요 수, 본문 미리보기)
2. Phase 2 - Like Mock 데이터 구조
3. Phase 3 - Query Service 확장 (커뮤니티별 조회, 메인페이지 조회)
4. Phase 5.1 - API 엔드포인트 추가

### 🟡 Medium Priority (2차 구현)
5. Phase 1.2 - 블라인드 스타일 메인페이지용 DTO
6. Phase 4 - Adapter 구현 (JDBC 쿼리)
7. Phase 6 - 데이터베이스 스키마 및 Mock 데이터
8. Phase 7 - 테스트 작성

### 🟢 Low Priority (추후 개선)
9. Phase 8 - 성능 최적화
10. Phase 9 - 문서화 및 배포

---

## 주요 고려사항

1. **좋아요 기능의 임시성**: 현재는 Mock 데이터로 구현하지만, 추후 실제 좋아요 기능 구현 시 독립 도메인(`like` 패키지)로 분리 필요
2. **무한 스크롤 구현**: 커서 기반 페이징 vs 오프셋 기반 페이징 트레이드오프 검토 (현재는 오프셋 방식 사용)
3. **메인페이지 전략**: 블라인드 스타일(제목만) vs 본문 미리보기 스타일 중 선택 또는 두 가지 모두 제공
4. **CQRS 준수**: Command는 JPA, Query는 JDBC로 명확히 분리 유지
5. **테스트 격리**: Stub 저장소는 각 테스트마다 독립적으로 생성하여 상태 공유 방지

---

## 예상 변경 파일 목록

### 신규 생성
- `feed/domain/FeedLike.java`
- `feed/domain/FeedLikeRepository.java`
- `feed/infra/query/jdbc/mapper/FeedTitleOnlyRowMapper.java`
- `feed/application/query/port/FeedQueryPortStub.java` (테스트)
- `src/test/resources/sql/like_schema.sql`
- `src/test/resources/sql/like_data.sql`

### 수정
- `feed/application/query/dto/FeedSummary.java` (필드 확장)
- `feed/application/query/dto/FeedDetail.java` (필드 확장)
- `feed/application/query/dto/FeedTitleOnly.java` (메인페이지 전용 DTO 연결)
- `feed/application/query/port/FeedQueryPort.java`
- `feed/application/query/FeedQueryService.java`
- `feed/infra/query/FeedQueryAdapter.java`
- `feed/infra/query/jdbc/FeedJdbcRepositoryImpl.java`
- `feed/infra/query/jdbc/mapper/FeedSummaryRowMapper.java`
- `feed/presentation/FeedController.java`
- `feed/presentation/swagger/FeedSwagger.java`
- 테스트 파일들 (`*Test.java`)

### 영향 받는 파일
- `global/exception/ErrorCode.java` (필요시 새 에러 코드 추가)
- `community/presentation/CommunityController.java` (커뮤니티 상세 페이지에서 피드 조회 시)

---

## DTO 관리 가이드

### 개별 DTO 유지 원칙
1. **클래스 단위 유지**: `FeedSummary`, `FeedDetail`, `FeedTitleOnly`처럼 역활별 DTO를 개별 파일로 두고 필요한 필드만 선언한다.
2. **명시적 의존성**: 어떤 레이어에서 어떤 DTO를 쓰는지 주석 또는 패키지 구조로 드러나게 하여 import 만으로도 의도를 파악할 수 있게 한다.
3. **호환성 고려**: 기존 DTO를 수정할 때는 동일 시그니처를 사용하는 다른 서비스/테스트에 영향이 없는지 먼저 검색(`rg FeedSummary`)으로 확인한다.
4. **네이밍 규칙**: `FeedSummaryResponse` 같이 용도가 Presentation이라면 접미사를 붙이고, Application 계층 DTO는 `FeedSummary`처럼 단순화한다.

### 필드 확장/변경 체크리스트
- **생성자/Record 업데이트**: 필드 추가 시 모든 생성자, 빌더, Mapper에서 값을 전달하는지 확인한다.
- **테스트 픽스처 동기화**: Stub이나 Fixture 객체가 새 필드를 채우지 않으면 NPE가 발생할 수 있으므로 공통 Fixture 유틸을 업데이트한다.
- **Swagger/문서 반영**: Presentation DTO 구조가 바뀌면 `FeedSwagger`의 예시 응답도 즉시 수정한다.
- **Backward Compatibility**: API 응답 스키마 변경 시 버전 관리 또는 프론트 협의 후 반영한다.

### 신규 DTO 도입 절차
```java
// 1단계: 새로운 용도 정의
public record FeedTitleOnly(
    Long id,
    String title,
    int likeCount,
    int commentCount,
    CommunitySummary community
) {}

// 2단계: QueryPort/Service/Adapter에 메서드 및 매퍼 추가

// 3단계: Controller/Swagger/Test에서 DTO 연결 확인
```
