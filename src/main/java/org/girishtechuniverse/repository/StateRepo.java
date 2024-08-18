package org.girishtechuniverse.repository;

import java.util.List;

import org.girishtechuniverse.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepo extends JpaRepository<State, Integer> {

	public List<State> findByCountryId(Integer countryId);
}
