package com.kirl.loans.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter@Setter@ToString
public class BaseEntity {

	@Column(name = "created_at")
	@NotNull
	private LocalDateTime createdAt;

	@Column(name = "created_by")
	@NotNull
	private String createdBy;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;
}
