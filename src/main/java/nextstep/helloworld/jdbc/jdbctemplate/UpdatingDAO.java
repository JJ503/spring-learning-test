package nextstep.helloworld.jdbc.jdbctemplate;

import nextstep.helloworld.jdbc.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class UpdatingDAO {
    private JdbcTemplate jdbcTemplate;

    public UpdatingDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Customer> actorRowMapper = (resultSet, rowNum) -> {
        Customer customer = new Customer(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name")
        );
        return customer;
    };

    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public void insert(Customer customer) {
        String sql = "insert into customers (first_name, last_name) values (?, ?)";
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        jdbcTemplate.update(sql, firstName, lastName);
    }
    /**
     * public int update(String sql, @Nullable Object... args)
     */
    public int delete(Long id) {
        String sql = "delete from customers where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * public int update(final PreparedStatementCreator psc, final KeyHolder generatedKeyHolder)
     */
    public Long insertWithKeyHolder(Customer customer) {
        final String sql = "insert into customers (first_name, last_name) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, new String[] {"id"});
                pstmt.setString(1, customer.getFirstName());
                pstmt.setString(2, customer.getLastName());
                return pstmt;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
