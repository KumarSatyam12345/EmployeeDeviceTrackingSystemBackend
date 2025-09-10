package com.jsp.reposetory;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.jsp.entity.User;

@Repository
public interface UserReposetory extends JpaRepository<User, Integer> {
	
}
