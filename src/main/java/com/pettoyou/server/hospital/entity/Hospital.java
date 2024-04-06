package com.pettoyou.server.hospital.entity;


import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("H")
@Table(name = "hospital")
public class Hospital extends Store {

    private String additionalServiceTag;

//    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
//    private List<TagMapper> tags = new ArrayList<>();

    //순환참조 문제 발생.

}
