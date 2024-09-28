package org.library.library_backend.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Members")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    private long memberId;
    @Column(name = "memberName")
    private String memberName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "registeredDate")
    private String registeredDate;
}
