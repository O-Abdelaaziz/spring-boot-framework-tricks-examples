package com.datatransferobjectmanual.repository;

import com.datatransferobjectmanual.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Created 02/10/2022 - 10:32
 * @Package com.datatransferobjectmanual.repository
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
