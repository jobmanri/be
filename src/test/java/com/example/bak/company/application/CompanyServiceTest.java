package com.example.bak.company.application;

import static com.example.bak.global.utils.AssertionsErrorCode.assertBusiness;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.community.domain.CommunityRepositoryStub;
import com.example.bak.company.application.dto.CompanyResult;
import com.example.bak.company.application.dto.CompanyResult.Detail;
import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepositoryStub;
import com.example.bak.global.exception.ErrorCode;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CompanyService 유닛 테스트")
public class CompanyServiceTest {

    private CompanyService companyService;
    private CompanyRepositoryStub companyRepository;
    private CommunityRepositoryStub communityRepository;

    @BeforeEach
    void setUp() {
        companyRepository = new CompanyRepositoryStub();
        communityRepository = new CommunityRepositoryStub();
        companyService = new CompanyService(companyRepository, communityRepository);
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

    @Nested
    @DisplayName("회사 생성 테스트")
    class CreateCompanyTest {

        @Test
        @DisplayName("회사 생성 성공")
        void companyCreate_success() {
            // when
            CompanyResult.CompanyId result = companyService.createCompany("회사", "로고", "로고", "설명");

            // then
            assertThat(result).isNotNull();
            Company first = companyRepository.findAll().getFirst();
            assertThat(first.getName()).isEqualTo("회사");
            assertThat(first.getCareerLink()).isEqualTo("로고");
            assertThat(first.getLogoUrl()).isEqualTo("로고");
            assertThat(first.getDescription()).isEqualTo("설명");
        }
    }

    @Nested
    @DisplayName("회사 조회 테스트")
    class GetCompanyTest {

        @Test
        @DisplayName("회사 단건 조회")
        void company_single_get_success() {
            // given
            Company company = setUpCompany(1L);
            companyRepository.save(company);

            // when
            Detail companyDetail = companyService.getCompany(1L);

            // then
            assertThat(companyDetail.name()).isEqualTo("1번째 회사");
            assertThat(companyDetail.careerLink()).isEqualTo("1careerLink.com");
            assertThat(companyDetail.logoUrl()).isEqualTo("testLogoUrl.com");
            assertThat(companyDetail.description()).isEqualTo("1번째 회사");
        }

        @Test
        @DisplayName("회사 단건 조회 실패 - id 없음")
        void company_single_get_when_not_found() {
            assertBusiness(
                    () -> companyService.getCompany(1L),
                    ErrorCode.COMPANY_NOT_FOUND
            );
        }

        @Test
        @DisplayName("회사 복수 조회 성공")
        void company_multi_get_success() {
            // given
            for (long i = 1; i <= 10; i++) {
                companyRepository.save(setUpCompany(i));
            }

            // when
            List<Detail> companies = companyService.getCompanies();

            // then
            assertThat(companies.size()).isEqualTo(10);
            Detail companyDetail = companies.getFirst();
            assertThat(companyDetail.name()).isEqualTo("1번째 회사");
            assertThat(companyDetail.careerLink()).isEqualTo("1careerLink.com");
            assertThat(companyDetail.logoUrl()).isEqualTo("testLogoUrl.com");
            assertThat(companyDetail.description()).isEqualTo("1번째 회사");
        }

        @Test
        @DisplayName("회사 복수 조회 - 아무것도 없을 때 성공")
        void company_multi_get_success_nothing() {
            // when
            List<Detail> companies = companyService.getCompanies();

            // then
            assertThat(companies.size()).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("회사 수정 테스트")
    class UpdateCompanyTest {

        @Test
        @DisplayName("회사 수정 성공")
        void company_update_success() {
            // given
            Company company = setUpCompany(1L);
            Company savedCompany = companyRepository.save(company);

            // when
            companyService.updateCompany(1L, "이름", "링크", "로고", "설명");

            // then
            assertThat(savedCompany.getName()).isEqualTo("이름");
            assertThat(savedCompany.getCareerLink()).isEqualTo("링크");
            assertThat(savedCompany.getLogoUrl()).isEqualTo("로고");
            assertThat(savedCompany.getDescription()).isEqualTo("설명");
        }
    }

    @Nested
    @DisplayName("회사 삭제 테스트")
    class DeleteCompanyTest {

        @Test
        @DisplayName("회사 삭제 성공")
        void company_delete_success() {
            // given
            Company company = companyRepository.save(setUpCompany(1L));


            // when
            companyService.deleteCompany(1L);

            // then
            Optional<Company> savedCompany = companyRepository.findById(1L);
            assertThat(savedCompany).isEmpty();
        }

        @Test
        @DisplayName("회사 삭제 - 이미 없는 id 삭제 시도해도 멱등성 보장")
        void company_delete_success_idempotency() {
            // given
            Company company = companyRepository.save(setUpCompany(1L));


            // when
            companyService.deleteCompany(1L);
            companyService.deleteCompany(1L);

            // then
            Optional<Company> savedCompany = companyRepository.findById(1L);
            assertThat(savedCompany).isEmpty();
        }
    }
}
