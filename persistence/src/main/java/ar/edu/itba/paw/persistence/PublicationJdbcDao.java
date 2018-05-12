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

import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.model.Publication;

@Primary
@Repository
public class PublicationJdbcDao implements PublicationDao {
	private	JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public PublicationJdbcDao(final DataSource ds) {
		jdbcTemplate = new	JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
						.withTableName("publications")
						.usingColumns("supervisor", "description", "price", "quantity")
						.usingGeneratedKeyColumns("publication_id");
	}

	private final static RowMapper<Publication> ROW_MAPPER =
			(ResultSet rs, int rowNum) -> new Publication(rs.getLong("publication_id"),rs.getString("supervisor"),rs.getString("description"),rs.getFloat("price"),rs.getInt("quantity"));

	@Override
	public Publication findById(long id) {
		final List<Publication> list = jdbcTemplate.query("SELECT * FROM publications WHERE publication_id = ?;", ROW_MAPPER, id);
		if	(list.isEmpty()) {
			return	null;
		}
		return	list.get(0);
	}

	@Override
	public List<Publication> findBySupervisor(String username) {
		return	jdbcTemplate.query("SELECT * FROM publications WHERE supervisor = ?;", ROW_MAPPER, username);
	}

	@Override
	public List<Publication> findByDescription(String description) {
		return	jdbcTemplate.query("SELECT * FROM publications WHERE LOWER(description) like LOWER(?);", ROW_MAPPER, "%"+description+"%");
	}

	@Override
	public List<Publication> findByPrice(float minPrice, float maxPrice) {
		return	jdbcTemplate.query("SELECT * FROM publications WHERE price between ? and ?;", ROW_MAPPER, minPrice, maxPrice);
	}

	@Override
	public List<Publication> findByQuantity(int quantity) {
		return	jdbcTemplate.query("SELECT * FROM publications WHERE quantity = ?;", ROW_MAPPER, quantity);
	}

	@Override
	public List<Publication> findByQuantity(int minQuantity, int maxQuantity) {
		return	jdbcTemplate.query("SELECT * FROM publications WHERE price between ? and ?;", ROW_MAPPER, minQuantity, maxQuantity);
	}

	@Override
	public Publication create(String supervisor, String description, float price, int quantity) {
		final Map<String, Object> args = new HashMap<>();
		
		args.put("supervisor", supervisor);
		args.put("description", description);
		args.put("price", price);
		args.put("quantity", quantity);
		
		final Number publicationId = jdbcInsert.executeAndReturnKey(args);
		
		return new Publication(publicationId.longValue(),supervisor,description,price,quantity);
	}

	@Override
	public boolean confirm(long id) {
		return jdbcTemplate.update("UPDATE publications SET is_confirmed = ? WHERE publication_id = ?",true,id) > 0;
	}

	@Override
	public boolean delete(long id) {
		return jdbcTemplate.update("DELETE FROM publications WHERE publication_id = ?",id) > 0;
	}
}