package org.AGES.repository.user;

import org.AGES.dto.UserDto;
import org.AGES.model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGetInformationServiceImpl implements UserGetInformationService{

    private UserCRUDRepo userCRUDRepo;

    public UserGetInformationServiceImpl(UserCRUDRepo userCRUDRepo){
        this.userCRUDRepo = userCRUDRepo;
    }
    @Override
    public UserDto getUserInformation(long userId) throws SQLException {
        User user = userCRUDRepo.findById(userId);

        UserDto userDto = UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .address(user.getAddress())
                .number(user.getNumber())
                .image(user.getImage())
                .role(user.getRole())
                .build();

        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userCRUDRepo.findAll();

        List<UserDto> usersDtoList = new ArrayList<>();

        for (User user : users) {
            UserDto userDto = UserDto.builder()
                    .name(user.getName())
                    .surname(user.getSurname())
                    .age(user.getAge())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .number(user.getNumber())
                    .image(user.getImage())
                    .role(user.getRole())
                    .build();

            usersDtoList.add(userDto);
        }

        return usersDtoList;
    }
}
