package com.ciprian.demo.domain;

import com.ciprian.demo.enums.LoaderStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "loader_event")
public class LoaderEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private LoaderStatusEnum status;

  @CreatedDate
  @Column(name = "created_date", updatable = false)
  @JsonIgnore
  private Instant createdDate = Instant.now();

  @LastModifiedDate
  @Column(name = "last_modified_date")
  @JsonIgnore
  private Instant lastModifiedDate = Instant.now();
}
