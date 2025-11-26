package com.example.bak.community.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.community.application.command.CommunityCommandService;
import com.example.bak.community.application.query.dto.CommunityResult;
import com.example.bak.community.domain.Community;
import com.example.bak.community.domain.CommunityRepositoryStub;
import com.example.bak.community.domain.CompanyValidationStub;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CommunityService 유닛 테스트")
public class CommunityServiceUnitTest {

    private CommunityCommandService communityCommandService;
    private CommunityRepositoryStub communityRepository;
    private CompanyValidationStub companyValidation;

    @BeforeEach
    void setUp() {
        communityRepository = new CommunityRepositoryStub();
        communityCommandService = new CommunityCommandService(communityRepository, companyValidation);
    }

    private List<Community> setUpCommunities(Long companyId, int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> Community.testInstance(
                        (long) i,
                        i + "번째 커뮤니티",
                        i + "번째 직군",
                        companyId
                ))
                .toList();
    }

    @Nested
    @DisplayName("커뮤니티 생성 테스트")
    class CreateCommunityTest {

        @Test
        @DisplayName("커뮤니티 생성 성공")
        void createCommunity_success() {
            // when
            CommunityResult.CommunityId result = communityCommandService.createCommunity(
                    1L, "it 커뮤니티", "it 직군"
            );

            // then
            assertThat(result).isNotNull();
            Community saved = communityRepository.findAll().getFirst();
            assertThat(saved.getName()).isEqualTo("it 커뮤니티");
            assertThat(saved.getJobGroup()).isEqualTo("it 직군");
            assertThat(saved.getCompanyId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("존재하지 않는 회사 id면 실패")
        void createCommunity_when_company_notFound() {
            assertBusiness(
                    () -> communityCommandService.createCommunity(1L, "it 커뮤니티", "it 직군"),
                    ErrorCode.COMPANY_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("커뮤니티 수정 테스트")
    class CommunityUpdateTest {

        @Test
        @DisplayName("커뮤니티 수정 성공")
        void updateCommunity_success() {
            // given
            Community community = Community.testInstance(1L, "커뮤니티", "it 직군", 1L);
            communityRepository.save(community);

            // when
            communityCommandService.updateCommunity(1L, "수정", "수정");

            // then
            assertThat(community.getName()).isEqualTo("수정");
            assertThat(community.getJobGroup()).isEqualTo("수정");
        }
    }

    @Nested
    @DisplayName("커뮤니티 삭제 테스트")
    class CommunityDeleteTest {

        @Test
        @DisplayName("커뮤니티 삭제 성공 테스트")
        void deleteCommunity_success() {
            // given
            Community community = Community.testInstance(1L, "커뮤니티", "it 직군", 1L);
            communityRepository.save(community);

            // when
            communityCommandService.deleteCommunity(1L);

            // then
            Optional<Community> savedCommunity = communityRepository.findById(1L);
            assertThat(savedCommunity).isEmpty();
        }

        @Test
        @DisplayName("삭제 대상 없는 경우에도 멱등하게 보장")
        void deleteCommunity_idempotency() {
            // given
            Community community = Community.testInstance(1L, "커뮤니티", "it 직군", 1L);
            communityRepository.save(community);

            // when
            communityCommandService.deleteCommunity(1L);
            communityCommandService.deleteCommunity(1L);

            // then
            Optional<Community> savedCommunity = communityRepository.findById(1L);
            assertThat(savedCommunity).isEmpty();
        }
    }
}
