package com.bookstore.demo.user;

import com.bookstore.demo.user.dto.UserDTO;
import com.bookstore.demo.user.dto.UserListDTO;
import com.bookstore.demo.user.dto.UserMinimalDTO;
import com.bookstore.demo.user.mapper.UserMapper;
import com.bookstore.demo.user.model.ERole;
import com.bookstore.demo.user.model.Role;
import com.bookstore.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public UserDTO create(UserDTO user){
        User newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode( user.getPassword()) )
                .build();

        Set<Role> roles = new HashSet<>();
        Role defaultRole = roleRepository.findByName(ERole.EMPLOYEE)
                .orElseThrow(() -> new RuntimeException("Cannot find EMPLOYEE role"));
        roles.add(defaultRole);

        newUser.setRoles(roles);

        return userMapper.toUserDto(userRepository.save(newUser));
    }

    public UserDTO edit(UserDTO user){
        User newUser = userRepository.findById(user.getId()).orElseThrow(()->new EntityExistsException("User not found"));
        newUser.setUsername(user.getUsername());

        if(user.getPassword() != null) {
            if (!user.getPassword().equals("")) {
                newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        return userMapper.toUserDto(userRepository.save(newUser));

    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

}
