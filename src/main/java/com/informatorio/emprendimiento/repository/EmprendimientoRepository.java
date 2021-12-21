package com.informatorio.emprendimiento.repository;

import com.informatorio.emprendimiento.entity.Emprendimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmprendimientoRepository extends JpaRepository<Emprendimiento, Long>  {
}
