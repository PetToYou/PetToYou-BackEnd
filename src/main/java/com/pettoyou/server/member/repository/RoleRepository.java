package com.pettoyou.server.member.repository;

import com.pettoyou.server.member.entity.Role;
import com.pettoyou.server.member.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
}
