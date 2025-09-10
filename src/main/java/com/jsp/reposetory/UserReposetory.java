package com.jsp.reposetory;

import com.jsp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposetory extends JpaRepository<User, Integer> {

}
