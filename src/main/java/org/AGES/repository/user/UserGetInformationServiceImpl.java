package org.AGES.repository.user;

import org.AGES.dto.UserDto;
import org.AGES.model.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserGetInformationServiceImpl implements UserGetInformationService{

    private UserCRUDRepo userCRUDRepo;
    //TODO: ADD THE FILE REPO TO FETCH THE IMAGE FROM

    public UserGetInformationServiceImpl(UserCRUDRepo userCRUDRepo){
        this.userCRUDRepo = userCRUDRepo;
    }
    @Override
    public UserDto getUserInformation(long userId) throws SQLException {
        User user = userCRUDRepo.findById(userId);

        //TODO: ADD THE IMAGE ON HERE TO THE USER USING THE FILE REPO
        UserDto userDto = UserDto.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .address(user.getAddress())
                .number(user.getNumber())
                .role(user.getRole())
                .build();

        return userDto;
    }

    @Override
    public List<UserDto> findAll() {
        //TODO : PASS THE IMAGE HERE USING THE FILE REPO
        List<User> users = userCRUDRepo.findAll();

        List<UserDto> usersDtoList = new ArrayList<>();

        for (User user : users) {
            //TODO: CALL THE METHOD THAT RETURN THE IMAGE BY THE USER ID HERE, THEN PASS THE IMAGE TO THE USER
            UserDto userDto = UserDto.builder()
                    .name(user.getName())
                    .surname(user.getSurname())
                    .age(user.getAge())
                    .email(user.getEmail())
                    .address(user.getAddress())
                    .number(user.getNumber())
                    .role(user.getRole())
                    .build();

            usersDtoList.add(userDto);
        }

        return usersDtoList;
    }
}
