package org.girishtechuniverse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CITY_MASTER")
public class City {

	@Id
	private Integer cityId;

	private String cityName;

	private Integer stateId;
}
