package com.task_management_system.task.repository;

import com.task_management_system.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUserName(String name);
    User findByEmail(String email);
    User findByPhoneNumber(String number);

}
