package org.girishtechuniverse.repository;

import org.girishtechuniverse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {

	public User findByEmail(String email);

	public User findByEmailAndPwd(String email, String pwd);

}
