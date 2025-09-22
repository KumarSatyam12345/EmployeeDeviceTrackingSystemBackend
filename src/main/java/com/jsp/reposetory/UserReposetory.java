package com.jsp.reposetory;

import com.jsp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReposetory extends JpaRepository<User, Integer> {

    Optional<User> findByGmail(String gmail);
}
