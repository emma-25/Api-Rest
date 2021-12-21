package com.informatorio.emprendimiento.repository;

import com.informatorio.emprendimiento.entity.Tags;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tags, Long> {
}
