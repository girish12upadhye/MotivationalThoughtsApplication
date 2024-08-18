package org.girishtechuniverse.repository;

import java.util.List;

import org.girishtechuniverse.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepo extends JpaRepository<City, Integer> {

	public List<City> findByStateId(Integer stateId);

}
