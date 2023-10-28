package dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.lviv.lgs.controller2.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
