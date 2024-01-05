package by.alex.repository.impl;

import by.alex.entity.Wagon;
import by.alex.mapper.impl.WagonResultSetMapperImpl;
import by.alex.repository.WagonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Repository
public class WagonRepositoryImpl implements WagonRepository {

    private JdbcTemplate jdbcTemplate;

    public WagonRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Collection<Wagon> findAll() {

        String sql = "SELECT w.id, w.wagonNumber, w.loadCapacity, w.yearOfConstruction, w.dateOfLastService FROM wagons w";
        RowMapper<Wagon> rowMapper = new WagonResultSetMapperImpl();

        return jdbcTemplate.query(sql, rowMapper);
    }

    //    @Override
    public Collection<Wagon> findAll(int page, int pageSize) {

        int offset = (page - 1) * pageSize;
        String sql = "SELECT w.id, w.wagonNumber, w.loadCapacity, w.yearOfConstruction, w.dateOfLastService FROM wagons w LIMIT ? OFFSET ?";
        RowMapper<Wagon> rowMapper = new WagonResultSetMapperImpl();

        return jdbcTemplate.query(sql, rowMapper, pageSize, offset);
    }

    @Override
    public Optional<Wagon> findById(UUID id) {

        String sql = "SELECT * FROM wagons WHERE id = ?";
        RowMapper<Wagon> rowMapper = new WagonResultSetMapperImpl();
        List<Wagon> wagons = jdbcTemplate.query(sql, new Object[]{id}, rowMapper);

        return wagons.isEmpty() ? Optional.empty() : Optional.of(wagons.get(0));
    }

    public Wagon create(Wagon item) {

        String sql = "INSERT INTO wagons (id, wagonNumber, loadCapacity, yearOfConstruction, dateOfLastService) VALUES (?, ?, ?, ?, ?)";
        UUID uuid = UUID.randomUUID();
        jdbcTemplate.update(sql
                , uuid
                , item.getWagonNumber()
                , item.getLoadCapacity()
                , item.getYearOfConstruction()
                , java.sql.Date.valueOf(item.getDateOfLastService()));

        return findById(uuid).orElseThrow();
    }

    public Wagon update(Wagon item) {

        String sql =
                "UPDATE wagons SET wagonNumber = ?, loadCapacity = ?, yearOfConstruction = ?, dateOfLastService = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql
                , item.getWagonNumber()
                , item.getLoadCapacity()
                , item.getYearOfConstruction()
                , java.sql.Date.valueOf(item.getDateOfLastService())
                , item.getId());

        if (rowsAffected == 0) {
            throw new RuntimeException("Failed to update wagon. No rows affected.");
        }

        return findById(item.getId()).orElseThrow();
    }

    public void delete(UUID id) {

        String sql = "DELETE FROM wagons WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Wagon> findByWagonNumber(String wagonNumber) {

        String sql = "SELECT * FROM wagons WHERE wagonNumber = ?";
        RowMapper<Wagon> rowMapper = new WagonResultSetMapperImpl();
        List<Wagon> wagons = jdbcTemplate.query(sql, new Object[]{wagonNumber}, rowMapper);

        return wagons.isEmpty() ? Optional.empty() : Optional.of(wagons.get(0));
    }
}
