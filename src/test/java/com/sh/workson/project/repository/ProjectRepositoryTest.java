package com.sh.workson.project.repository;

import com.sh.workson.department.entity.Department;
import com.sh.workson.department.repository.DepartmentRepository;
import com.sh.workson.employee.entity.Employee;
import com.sh.workson.employee.entity.WorkStatus;
import com.sh.workson.employee.repository.EmployeeRepository;
import com.sh.workson.position.entity.Position;
import com.sh.workson.position.repository.PositionRepository;
import com.sh.workson.project.entity.Project;
import com.sh.workson.project.entity.ProjectEmployee;
import com.sh.workson.project.entity.ProjectRole;
import com.sh.workson.project.entity.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest // test에 관련된 bean만 로드하기 때문에 passwordencoder를 로드하지 못함
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED) // 트랜젝션 사용 X 설정
//@SpringBootTest
class ProjectRepositoryTest {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    ProjectEmployeeRepository projectEmployeeRepository;

    @DisplayName("내가 참여중인 전체 프로젝트를 조회할 수 있다.")
    @Test
    void test1() {
        // given
        // when
        // then
    }
    @DisplayName("프로젝트를 생성할 수 있다.")
    @Test
    void test2() {
        // given
        // when
        // then
    }
    @DisplayName("프로젝트 정보를 업데이트할 수 있다.")
    @Test
    void test3() {
        // given
        // when
        // then
    }
    @DisplayName("프로젝트를 삭제할 수 있다.")
    @Test
    void test4() {
        // given
        // when
        // then
    }


}