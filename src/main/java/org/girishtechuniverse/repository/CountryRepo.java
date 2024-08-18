package org.girishtechuniverse.repository;

import org.girishtechuniverse.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepo extends JpaRepository<Country, Integer> {

}
