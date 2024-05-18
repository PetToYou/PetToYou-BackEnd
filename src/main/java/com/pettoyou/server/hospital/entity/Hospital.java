package com.pettoyou.server.hospital.entity;


import com.pettoyou.server.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("H")
@OnDelete(action = OnDeleteAction.CASCADE)
// store를 soft-delete 했을때 상속 객체인 hospital도 똑같이 soft-delete 해주도록 설정
@Table(name = "hospital")
public class Hospital extends Store {

    private String additionalServiceTag;

//    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
//    private List<TagMapper> tags = new ArrayList<>();

    //순환참조 문제 발생.

}
