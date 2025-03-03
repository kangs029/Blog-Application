package com.blog.apis.blog_application.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.apis.blog_application.configs.AppConstants;
import com.blog.apis.blog_application.entities.Role;
import com.blog.apis.blog_application.entities.User;
import com.blog.apis.blog_application.exceptions.ResourceNotFoundException;
import com.blog.apis.blog_application.payloads.UserDTO;
import com.blog.apis.blog_application.repositories.RoleRepo;
import com.blog.apis.blog_application.repositories.UserRepo;
import com.blog.apis.blog_application.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
	private RoleRepo roleRepo;

    @Override
    public UserDTO createUser(UserDTO userDto) {
        User user = dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return userToDto(savedUser);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, Integer userId) {
        Optional<User> user=this.userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("User", "id", userId);
        }
        User usr = user.get();
        
        usr.setName(userDto.getName());
        usr.setEmail(userDto.getEmail());
        usr.setPassword(userDto.getPassword());
        usr.setAbout(userDto.getAbout());


        User updatedUser = this.userRepo.save(usr);
        return userToDto(updatedUser);
    }

    @Override
    public UserDTO getUserById(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(
            ()-> new ResourceNotFoundException("User", "id", userId));

        return userToDto(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDTO> userDtos= users.stream()
             .map(user->this.userToDto(user))
             .collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(
            ()-> new ResourceNotFoundException("User", "id", userId));

        this.userRepo.delete(user);
    }

    private User dtoToUser(UserDTO userDTO){
        User user = this.modelMapper.map(userDTO, User.class);

        // User user = new User();
        // user.setId(userDTO.getId());
        // user.setName(userDTO.getName());
        // user.setEmail(userDTO.getEmail());
        // user.setPassword(userDTO.getPassword());
        // user.setAbout(userDTO.getAbout());
        return user;
    }

    private UserDTO userToDto(User user){
        UserDTO userDto = this.modelMapper.map(user, UserDTO.class);

        // UserDTO userDto = new UserDTO();
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setPassword(user.getPassword());
        // userDto.setAbout(user.getAbout());
        return userDto;
    }

    @Override
	public UserDTO registerNewUser(UserDTO userDto) {

		User user = this.modelMapper.map(userDto, User.class);
		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		// roles
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDTO.class);
	}
}
