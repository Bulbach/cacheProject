package by.alex.repository.impl;

import by.alex.connector.DataBaseConnector;
import by.alex.entity.Wagon;
import by.alex.mapper.impl.WagonResultSetMapperImpl;
import by.alex.repository.WagonRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class WagonRepositoryImpl implements WagonRepository {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    public WagonRepositoryImpl() {

        try {
            dataSource = DataBaseConnector.getInstance().getDataSource();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Wagon> findAll() {

        String sql = "SELECT w.id, w.wagonNumber, w.loadCapacity, w.yearOfConstruction, w.dateOfLastService FROM wagons w";
        RowMapper<Wagon> rowMapper = new WagonResultSetMapperImpl();
        List<Wagon> wagons = jdbcTemplate.query(sql, rowMapper);
        return wagons;
    }

    @Override
    public Optional<Wagon> findById(UUID id) {

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
        UUID uuid = UUID.randomUUID();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setObject(1, uuid);
            statement.setString(2, item.getWagonNumber());
            statement.setDouble(3, item.getLoadCapacity());
            statement.setInt(4, item.getYearOfConstruction());
            statement.setDate(5, java.sql.Date.valueOf(item.getDateOfLastService()));

            statement.executeUpdate();
            return findById(uuid).orElseThrow();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create wagon. No generated key obtained." + e.getMessage());
        }
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

        return findById(item.getId()).orElseThrow();
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
