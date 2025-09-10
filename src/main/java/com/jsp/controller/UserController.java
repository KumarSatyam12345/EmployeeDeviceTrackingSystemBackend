package com.jsp.controller;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jsp.entity.User;
import com.jsp.reposetory.UserReposetory;
@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserReposetory userRepository;
	@PostMapping("/SaveUser")
	public String saveUser(@RequestBody @Valid User user) {
		if(user!=null) {
			userRepository.save(user);
			return "User record saved successfully!";
		}
		else {
			return "Please insert the data";
		}
	}
	@GetMapping("/getAllUser")
	public List<User> getAllUser(){
		List<User> s = userRepository.findAll();
		System.out.println(s);
		return s;
	}
	@GetMapping("/{id}")
	public User getUserById(@PathVariable int id) {
		Optional<User> opt = userRepository.findById(id);
		if(opt.isPresent()) {
			return opt.get();
		}
		else {
			System.out.println("Data not found");
			return null;
		}
	}
	@DeleteMapping("/{id}")
	public String deleteUser(@PathVariable int id) {
		Optional<User> opt = userRepository.findById(id);
		if(opt.isPresent()) {
			userRepository.delete(opt.get());
			return "Data is deleted";
		}
		else {
			return "Not deleted";
		}
	}
	@PutMapping("/{id}")
	public String updateUser(@PathVariable int id, @RequestBody @Valid User updatedUser) {
	    Optional<User> optionalUser = userRepository.findById(id);
	    if (optionalUser.isPresent()) {
	        User existingUser = optionalUser.get();
	        existingUser.setName(updatedUser.getName());
	        existingUser.setGmail(updatedUser.getGmail());
	        existingUser.setPassword(updatedUser.getPassword());
	        userRepository.save(existingUser);
	        return "User record updated";
	    } else {
	        return "User not found";
	    }
	}
}
