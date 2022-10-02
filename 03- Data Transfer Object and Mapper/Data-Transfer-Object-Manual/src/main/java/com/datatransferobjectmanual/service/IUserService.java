package com.datatransferobjectmanual.service;

import com.datatransferobjectmanual.entity.User;

import java.util.List;

/**
 * @Created 02/10/2022 - 10:34
 * @Package com.datatransferobjectmanual.service
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
public interface IUserService {
    public List<User> findAll();

    public User findById(Long id);

    public User findByUsername(String username);

    public User findByEmail(String email);

    public User save(User user);

    public User update(Long id, User user);

    public void delete(long id);
}
