package ua.lviv.lgs.controller2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.lviv.lgs.controller2.domain.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer>{

}
