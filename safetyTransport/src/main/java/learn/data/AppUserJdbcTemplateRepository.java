package learn.data;

import learn.models.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<AppUser> findAll() {

        final String sql = """
                select app_user_id, username, password_hash, enabled, locked, authority
                from app_user;
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            AppUser user = new AppUser();
            user.setAppUserId(resultSet.getInt("app_user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password_hash"));
            user.setEnabled(resultSet.getBoolean("enabled"));
            user.setLocked(resultSet.getBoolean("locked"));
            if (resultSet.getString("authority") == null
                    || resultSet.getString("authority").isBlank()) {
                user.setAuthorities(List.of());
            } else {
                user.setAuthorities(List.of(resultSet.getString("authority")));
            }
            return user;
        });
    }

    @Override
    public AppUser findAppUserById(int id) {

        final String sql = """
                select app_user_id, username, password_hash, enabled, locked, authority
                from app_user
                where app_user_id = ?;
                """;
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            AppUser user = new AppUser();
            user.setAppUserId(resultSet.getInt("app_user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password_hash"));
            user.setEnabled(resultSet.getBoolean("enabled"));
            user.setLocked(resultSet.getBoolean("locked"));
            if (resultSet.getString("authority") == null
                    || resultSet.getString("authority").isBlank()) {
                user.setAuthorities(List.of());
            } else {
                user.setAuthorities(List.of(resultSet.getString("authority")));
            }
            return user;
        }, id).stream().findFirst().orElse(null);
    }

    public AppUser findByUserName(String username) {
        final String sql = """
                select app_user_id, username, password_hash, enabled, locked, authority
                from app_user
                where username = ?;
                """;

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            AppUser user = new AppUser();
            user.setAppUserId(resultSet.getInt("app_user_id"));
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password_hash"));
            user.setEnabled(resultSet.getBoolean("enabled"));
            user.setLocked(resultSet.getBoolean("locked"));
            if (resultSet.getString("authority") == null
                    || resultSet.getString("authority").isBlank()) {
                user.setAuthorities(List.of());
            } else {
                user.setAuthorities(List.of(resultSet.getString("authority")));
            }
            return user;
        }, username).stream().findFirst().orElse(null);
    }

    @Override
    public AppUser add(AppUser appUser) {

        if (findByUserName(appUser.getUsername()) != null) {
            throw new IllegalArgumentException("UserName already exists: " + appUser.getUsername());
        }

        final String sql = """
                insert into app_user (username, password_hash, enabled, locked, authority)
                values (?, ?, ?, ?, ?);
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, appUser.getUsername());
            ps.setString(2, appUser.getPassword());
            ps.setBoolean(3, appUser.isEnabled());
            ps.setBoolean(4, appUser.isAccountNonLocked());
            ps.setString(5, appUser.getAuthorities().isEmpty() ? null : appUser.getAuthorities().get(0).getAuthority());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        appUser.setAppUserId(keyHolder.getKey().intValue());
        return appUser;
    }

    @Override
    public boolean update(AppUser appUser) {

        final String sql = """
                update app_user set
                username = ?,
                password_hash = ?,
                enabled = ?,
                locked = ?,
                authority = ?
                where app_user_id = ?;
                """;
        return jdbcTemplate.update(sql,
                appUser.getUsername(),
                appUser.getPassword(),
                appUser.isEnabled(),
                appUser.isAccountNonLocked(),
                appUser.getAuthorities().isEmpty() ? null : appUser.getAuthorities().get(0).getAuthority(),
                appUser.getAppUserId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {

        final String deleteReviewsSql = """
                delete from reviews
                where app_user_id = ?;
                """;
        return jdbcTemplate.update(deleteReviewsSql, id) > 0;
    }
}

