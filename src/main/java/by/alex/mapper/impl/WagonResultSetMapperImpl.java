package by.alex.mapper.impl;

import by.alex.entity.Wagon;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class WagonResultSetMapperImpl implements RowMapper<Wagon> {

    @Override
    public Wagon mapRow(ResultSet rs, int rowNum) throws SQLException {
        Wagon wagon = new Wagon();
        wagon.setId(UUID.fromString(rs.getString("id")));
        wagon.setWagonNumber(rs.getString("wagonNumber"));
        wagon.setLoadCapacity(rs.getInt("loadCapacity"));
        wagon.setYearOfConstruction(rs.getInt("yearOfConstruction"));
        wagon.setDateOfLastService(rs.getDate("dateOfLastService").toLocalDate());
        return wagon;
    }
}
