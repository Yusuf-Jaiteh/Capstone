
package learn.data;

import learn.models.AppUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private AppUserJdbcTemplateRepository repository;

    @Test
    void findAllReturnsAllUsers() {
        AppUser user1 = new AppUser();
        user1.setAppUserId(1);
        user1.setUsername("user1");

        AppUser user2 = new AppUser();
        user2.setAppUserId(2);
        user2.setUsername("user2");

        when(repository.findAll()).thenReturn(List.of(user1, user2));

        List<AppUser> users = repository.findAll();
        assertEquals(2, users.size());
        assertEquals("user1", users.get(0).getUsername());
        assertEquals("user2", users.get(1).getUsername());
    }

    @Test
    void findAppUserByIdReturnsUserWhenExists() {
        AppUser user = new AppUser();
        user.setAppUserId(1);
        user.setUsername("user1");

        when(repository.findAppUserById(1)).thenReturn(user);

        AppUser result = repository.findAppUserById(1);
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    void findAppUserByIdReturnsNullWhenNotExists() {
        when(repository.findAppUserById(1)).thenReturn(null);

        AppUser result = repository.findAppUserById(1);
        assertNull(result);
    }

    @Test
    void findByUserNameReturnsUserWhenExists() {
        AppUser user = new AppUser();
        user.setAppUserId(1);
        user.setUsername("user1");

        when(repository.findByUserName("user1")).thenReturn(user);

        AppUser result = repository.findByUserName("user1");
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    void findByUserNameReturnsNullWhenNotExists() {
        when(repository.findByUserName("user1")).thenReturn(null);

        AppUser result = repository.findByUserName("user1");
        assertNull(result);
    }

    @Test
    void addInsertsNewUser() {
        AppUser user = new AppUser();
        user.setUsername("newuser");
        user.setPassword("password");

        when(repository.add(user)).thenReturn(user);

        AppUser result = repository.add(user);
        assertNotNull(result);
        assertEquals("newuser", result.getUsername());
    }

    @Test
    void updateModifiesExistingUser() {
        AppUser user = new AppUser();
        user.setAppUserId(1);
        user.setUsername("updateduser");

        when(repository.update(user)).thenReturn(true);

        boolean result = repository.update(user);
        assertTrue(result);
    }

    @Test
    void deleteByIdRemovesUser() {
        when(repository.deleteById(1)).thenReturn(true);

        boolean result = repository.deleteById(1);
        assertTrue(result);
    }
}
