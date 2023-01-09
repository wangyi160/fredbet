package de.fred4jupiter.fredbet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import de.fred4jupiter.fredbet.domain.Group;

public interface GroupRepository extends JpaRepository<Group, String>{
}
