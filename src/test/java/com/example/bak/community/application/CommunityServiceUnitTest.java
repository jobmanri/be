package com.example.bak.community.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.community.application.dto.CommunityResult;
import com.example.bak.community.application.dto.CommunityResult.Detail;
import com.example.bak.community.domain.Community;
import com.example.bak.community.domain.CommunityRepositoryStub;
import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepositoryStub;
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

    private CommunityService communityService;
    private CommunityRepositoryStub communityRepository;
    private CompanyRepositoryStub companyRepository;

    @BeforeEach
    void setUp() {
        companyRepository = new CompanyRepositoryStub();
        communityRepository = new CommunityRepositoryStub();
        communityService = new CommunityService(companyRepository, communityRepository);
    }

    private Company setUpCompany(Long id) {
        return Company.testInstance(
                id,
                id + "번째 회사",
                id + "careerLink.com",
                "testLogoUrl.com",
                id + "번째 회사"
        );
    }

    private List<Community> setUpCommunities(Company company, int count) {
        return IntStream.rangeClosed(1, count)
                .mapToObj(i -> Community.testInstance(
                        (long) i,
                        i + "번째 커뮤니티",
                        i + "번째 직군",
                        company
                ))
                .toList();
    }

    @Nested
    @DisplayName("커뮤니티 생성 테스트")
    class CreateCommunityTest {

        @Test
        @DisplayName("커뮤니티 생성 성공")
        void createCommunity_success() {
            // given
            Company company = setUpCompany(1L);
            Company savedCommunity = companyRepository.save(company);

            // when
            CommunityResult.CommunityId result = communityService.createCommunity(
                    1L, "it 커뮤니티", "it 직군"
            );

            // then
            assertThat(result).isNotNull();
            Community saved = communityRepository.findAll().getFirst();
            assertThat(saved.getName()).isEqualTo("it 커뮤니티");
            assertThat(saved.getJobGroup()).isEqualTo("it 직군");
            assertThat(saved.getCompany()).isEqualTo(savedCommunity);
        }

        @Test
        @DisplayName("존재하지 않는 회사 id면 실패")
        void createCommunity_when_company_notFound() {
            assertBusiness(
                    () -> communityService.createCommunity(1L, "it 커뮤니티", "it 직군"),
                    ErrorCode.COMPANY_NOT_FOUND
            );
        }
    }

    @Nested
    @DisplayName("커뮤니티 조회 테스트")
    class CommunityGetTest {

        @Test
        @DisplayName("커뮤니티 조회 성공")
        void getCommunity_success() {
            // given
            Company company = setUpCompany(1L);
            Company savedCommunity = companyRepository.save(company);
            setUpCommunities(savedCommunity, 5).forEach(communityRepository::save);

            // when
            List<Detail> communities = communityService.getCommunities(1L);

            // then
            assertThat(communities.size()).isEqualTo(5);
            Detail first = communities.getFirst();
            assertThat(first.name()).isEqualTo("1번째 커뮤니티");
            assertThat(first.jobGroup()).isEqualTo("1번째 직군");
        }

        @Test
        @DisplayName("존재하지 않는 회사에 대한 조회면 실패")
        void getCommunity_when_company_notFound() {
            assertBusiness(
                    () -> communityService.getCommunities(1L),
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
            Company company = setUpCompany(1L);
            Company savedCompany = companyRepository.save(company);

            Community community = Community.testInstance(1L, "커뮤니티", "it 직군", savedCompany);
            communityRepository.save(community);
            // when
            communityService.updateCommunity(1L, "수정", "수정");

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
            Company company = setUpCompany(1L);
            Company savedCompany = companyRepository.save(company);

            Community community = Community.testInstance(1L, "커뮤니티", "it 직군", savedCompany);
            communityRepository.save(community);

            // when
            communityService.deleteCommunity(1L);

            // then
            Optional<Community> savedCommunity = communityRepository.findById(1L);
            assertThat(savedCommunity).isEmpty();
        }

        @Test
        @DisplayName("삭제 대상 없는 경우에도 멱등하게 보장")
        void deleteCommunity_when_communityId_notFound() {
            // when
            communityService.deleteCommunity(1L);

            // then
            Optional<Community> community = communityRepository.findById(1L);
            assertThat(community).isEmpty();
        }
    }
}
