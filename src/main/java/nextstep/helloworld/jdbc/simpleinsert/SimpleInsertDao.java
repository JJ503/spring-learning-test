package nextstep.helloworld.jdbc.simpleinsert;

import nextstep.helloworld.jdbc.Customer;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SimpleInsertDao {
    private SimpleJdbcInsert insertActor;

    public SimpleInsertDao(DataSource dataSource) {
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("customers")
                .usingGeneratedKeyColumns("id");
    }

    /**
     * Map +
     * insertActor.executeAndReturnKey
     * id를 포함한 Customer 객체를 반환하세요
     */
    public Customer insertWithMap(Customer customer) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("first_name", customer.getFirstName());
        parameters.put("last_name", customer.getLastName());
        Number newId = insertActor.executeAndReturnKey(parameters);
        return new Customer(newId.longValue(), customer.getFirstName(), customer.getLastName());
    }

    /**
     * SqlParameterSource +
     * insertActor.executeAndReturnKey
     * id를 포함한 Customer 객체를 반환하세요
     */
    public Customer insertWithBeanPropertySqlParameterSource(Customer customer) {
        String firstName = customer.getFirstName();
        String lastName = customer.getLastName();
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("first_name", firstName)
                .addValue("last_name", lastName);
        Number newId = insertActor.executeAndReturnKey(sqlParameterSource);
        return new Customer(newId.longValue(), firstName, lastName);
    }
}
