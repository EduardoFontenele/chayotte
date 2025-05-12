package br.com.chayotte.company.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContactInfo {
    private String phone;
    private String email;
    private String website;

    @Column(name = "contact_name")
    private String contactName;
}
