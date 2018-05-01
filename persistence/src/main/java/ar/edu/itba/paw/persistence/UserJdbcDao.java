package ar.edu.itba.paw.persistence;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;

@Primary
@Repository
public class UserJdbcDao implements UserDao {
	private	JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public UserJdbcDao(final DataSource ds) {
		jdbcTemplate = new	JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
						.withTableName("users")
						.usingColumns("username", "email", "password")
						.usingGeneratedKeyColumns("user_id");
	}
	
	private final static RowMapper<User> ROW_MAPPER =
			(ResultSet rs, int rowNum) ->
				new User(rs.getLong("user_id"), rs.getString("username"), rs.getString("email"));

	@Override
	public User findById(final long id) {
		final List<User> list = jdbcTemplate.query("SELECT * FROM users WHERE user_id = ?;", ROW_MAPPER, id);
		if	(list.isEmpty()) {
			return	null;
		}
		return	list.get(0);
	}

	@Override
	public User create(String username, String email, String password) {
		final Map<String, Object> args = new HashMap<>();
		final String hashedPassword = DigestUtils.md5DigestAsHex(password.getBytes());
		
		args.put("username", username);
		args.put("password", hashedPassword);
		args.put("email", email);
		
		final Number userId = jdbcInsert.executeAndReturnKey(args);
		
		return new User(userId.longValue(), username, email);
	}
}