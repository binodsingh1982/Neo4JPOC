package com.stufusion.oauth2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.stufusion.oauth2.entity.Permission;
import com.stufusion.oauth2.entity.Role;
import com.stufusion.oauth2.entity.User;
import com.stufusion.oauth2.model.UserReq;
import com.stufusion.oauth2.repository.UserRepository;
import com.stufusion.oauth2.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = TestRepositoryConfig.class)
@SqlGroup({@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:sql/data-h2.sql"),
        @Sql(executionPhase = ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:sql/cleanup-h2.sql")})
public class UserRegistrationTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void test() {
        assertThat(entityManager).isNotNull();
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void getDefaultEntryTest() {
        List<User> findAll = userRepository.findAll();
        assertThat(findAll).hasSize(1);
        assertThat(findAll.get(0).getRoles()).hasSize(3);
        assertThat(findAll.get(0).getPermissions()).hasSize(3);

        assertThat(findAll.get(0).getPermissions().stream().map(Permission::getPermissionname)
                .collect(Collectors.toList())).contains("READ", "WRITE", "DELETE");

        assertThat(findAll.get(0).getRoles().stream().map(Role::getRoleName).collect(Collectors.toList()))
                .contains("ROLE_ADMIN", "ROLE_FACULTY", "ROLE_USER");
    }

    @Test
    public void createUser() {

        UserReq userReq = new UserReq();
        userReq.setEmail("test@gmail.com");
        userReq.setFirstName("John");
        userReq.setLastName("Smith");
        userReq.setPassword("Abcde@123");
        userReq.setRole("Student");

        Long id = userService.createUser(userReq);

        User findByEmail = userRepository.findByEmail("test@gmail.com");
        assertThat(findByEmail).isNotNull();

    }

}
