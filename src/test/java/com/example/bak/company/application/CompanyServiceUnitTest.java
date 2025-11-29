package com.example.bak.company.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bak.company.application.command.CompanyCommandService;
import com.example.bak.company.application.query.dto.CompanyResult;
import com.example.bak.company.domain.Company;
import com.example.bak.company.domain.CompanyRepositoryStub;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CompanyService 유닛 테스트")
public class CompanyServiceUnitTest {

    private CompanyCommandService companyService;
    private CompanyRepositoryStub port;

    @BeforeEach
    void setUp() {
        port = new CompanyRepositoryStub();
        companyService = new CompanyCommandService(port);
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
            Company first = port.findAll().getFirst();
            assertThat(first.getName()).isEqualTo("회사");
            assertThat(first.getCareerLink()).isEqualTo("로고");
            assertThat(first.getLogoUrl()).isEqualTo("로고");
            assertThat(first.getDescription()).isEqualTo("설명");
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
            Company savedCompany = port.save(company);

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
            Company company = port.save(setUpCompany(1L));


            // when
            companyService.deleteCompany(1L);

            // then
            Optional<Company> savedCompany = port.findById(1L);
            assertThat(savedCompany).isEmpty();
        }

        @Test
        @DisplayName("회사 삭제 - 이미 없는 id 삭제 시도해도 멱등성 보장")
        void company_delete_success_idempotency() {
            // given
            Company company = port.save(setUpCompany(1L));


            // when
            companyService.deleteCompany(1L);
            companyService.deleteCompany(1L);

            // then
            Optional<Company> savedCompany = port.findById(1L);
            assertThat(savedCompany).isEmpty();
        }
    }
}
