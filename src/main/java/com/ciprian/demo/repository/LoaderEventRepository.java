package com.ciprian.demo.repository;

import com.ciprian.demo.domain.LoaderEvent;
import com.ciprian.demo.enums.LoaderStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaderEventRepository extends JpaRepository<LoaderEvent, Long> {

  Long countAllByStatusEquals(LoaderStatusEnum loaderStatus);
}
