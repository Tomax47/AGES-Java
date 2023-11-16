package org.AGES.repository.user;

import org.AGES.dto.UserDto;
import java.sql.SQLException;
import java.util.List;

public interface UserGetInformationService {

    UserDto getUserInformation(long userId) throws SQLException;
    List<UserDto> findAll();
}
