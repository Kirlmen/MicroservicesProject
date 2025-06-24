package com.kirl.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass //these fields will be in all tables of these modules. that's why we are creating a base entity.
@Getter
@Setter
@ToString
public class BaseEntity {

	//created fields won't get updated because it is written as a timestamp at initialization.
	//updated fields won't be insertable at the creation because it's getting created not updated.

	@Column(updatable = false)
	LocalDateTime createdAt;

	@Column(updatable = false)
	String createdBy;

	@Column(insertable = false)
	LocalDateTime updatedAt;

	@Column(insertable = false)
	String updatedBy;
}
