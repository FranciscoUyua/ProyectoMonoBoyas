package com.monoboyas.api.operacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuqueRepository extends JpaRepository<Buque, String> {
    // PK es String (nroIMO)
}
