package by.alex.service;

import by.alex.dto.WagonDto;

import java.util.UUID;

public interface WagonService extends AbstractService<WagonDto>{
    boolean isExist(String wagonNumber);
    boolean isExist(UUID wagonUUID);
}
