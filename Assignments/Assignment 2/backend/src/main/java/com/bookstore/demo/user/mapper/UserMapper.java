package com.bookstore.demo.user.mapper;

import com.bookstore.demo.user.dto.UserDTO;
import com.bookstore.demo.user.dto.UserListDTO;
import com.bookstore.demo.user.dto.UserMinimalDTO;
import com.bookstore.demo.user.model.User;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDto(User user);
    User fromUserDto(UserDTO userDTO);

    @Mappings({
            @Mapping(target = "name", source = "user.username")
    })
    UserMinimalDTO userMinimalFromUser(User user);

    @Mappings({
            @Mapping(target = "name", source = "user.username"),
            @Mapping(target = "roles", ignore = true)
    })
    UserListDTO userListDtoFromUser(User user);

    @AfterMapping
    default void populateRoles(User user, @MappingTarget UserListDTO userListDTO) {
        userListDTO.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
    }
}
