package uz.wearflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.wearflow.entity.User;
import java.util.Optional;

// JpaRepository - Spring Data bizga avtomatik save(), findAll(), findById() metodlarini beradi
// Bu interfeysi uchun biz hech qanday SQL yozmaymiz!
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring shu metod nomiga qarab avtomatik SQL yozadi:
    // SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
}
