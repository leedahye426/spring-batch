package com.ll.springbatch.domain.product.product.repository;

import com.ll.springbatch.domain.product.product.entity.ProductLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLogRepository extends JpaRepository<ProductLog, Long> {
}
