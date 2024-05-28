package com.pettoyou.server.hospital.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

@Repository
public class HospitalCustomRepositoryImpl implements HospitalCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;

    public HospitalCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


}
