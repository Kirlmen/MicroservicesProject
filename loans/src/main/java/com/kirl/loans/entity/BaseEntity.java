package com.kirl.loans.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter@Setter@ToString
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@CreatedDate
	@Column(updatable = false)
	LocalDateTime createdAt;

	@CreatedBy
	@Column(updatable = false)
	String createdBy;

	@LastModifiedDate
	@Column(insertable = false)
	LocalDateTime updatedAt;

	@LastModifiedBy
	@Column(insertable = false)
	String updatedBy;
}
