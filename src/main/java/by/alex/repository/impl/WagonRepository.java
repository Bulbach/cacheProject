package by.alex.repository.impl;

import by.alex.connector.DataBaseConnector;
import by.alex.entity.Wagon;
import by.alex.mapper.impl.WagonResultSetMapperImpl;
import by.alex.repository.AbstractRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WagonRepository implements AbstractRepository<Wagon> {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public WagonRepository() {
        try {
            dataSource = DataBaseConnector.getInstance().getDataSource();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Wagon> getAll() {
        String sql = "SELECT w.id, w.wagonNumber, w.loadCapacity, w.yearOfConstruction, w.dateOfLastService FROM wagons w";
        RowMapper<Wagon> rowMapper = new WagonResultSetMapperImpl();
        List<Wagon> wagons = jdbcTemplate.query(sql, rowMapper);
        return wagons;
    }

    @Override
    public Optional<Wagon> getById(UUID id) {
        String sql = "SELECT * FROM wagons WHERE id = ?";
        RowMapper<Wagon> rowMapper = new WagonResultSetMapperImpl();
        List<Wagon> wagons = jdbcTemplate.query(sql, new Object[]{id}, rowMapper);
        if (!wagons.isEmpty()) {
            return Optional.of(wagons.get(0));
        }
        return Optional.empty();
    }


    @Override
    public Wagon create(Wagon item) {
        String sql = "INSERT INTO wagons (id, wagonNumber, loadCapacity, yearOfConstruction, dateOfLastService) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setObject(1, item.getId());
            statement.setString(2, item.getWagonNumber());
            statement.setDouble(3, item.getLoadCapacity());
            statement.setInt(4, item.getYearOfConstruction());
            statement.setDate(5, java.sql.Date.valueOf(item.getDateOfLastService()));

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to create wagon. No rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(UUID.fromString(generatedKeys.getString(1)));
                } else {
                    throw new SQLException("Failed to create wagon. No generated key obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create wagon. No generated key obtained." + e.getMessage());
        }

        return item;
    }

    public Wagon update(Wagon item) {
        String sql = "UPDATE wagons SET wagonNumber = ?, loadCapacity = ?, yearOfConstruction = ?, " +
                "dateOfLastService = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, item.getWagonNumber());
            statement.setDouble(2, item.getLoadCapacity());
            statement.setInt(3, item.getYearOfConstruction());
            statement.setDate(4, java.sql.Date.valueOf(item.getDateOfLastService()));
            statement.setObject(5, item.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update wagon. No rows affected.");
            }
        } catch (SQLException e) {
           throw new RuntimeException("Failed to update wagon. " + e.getMessage());
        }

        return item;
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM wagons WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)
    ) {
            statement.setObject(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete wagon. " + e.getMessage());
        }
    }
}
