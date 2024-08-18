package org.girishtechuniverse.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "USER_MASTER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String userName;

	private String email;

	private String pwd;

	private Integer countryId;

	private Integer stateId;

	private Integer cityId;

	private String pwdUpdated;

	@CreationTimestamp
	private LocalDate creaedDate;

	@UpdateTimestamp
	private LocalDate updatedDate;

}
