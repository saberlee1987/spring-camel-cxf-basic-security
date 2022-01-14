package com.saber.spring_camel_service_provider.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "persons",uniqueConstraints = @UniqueConstraint(name = "nationalUnique",columnNames ={"nationalCode"} ))
@Getter
@Setter
@ToString
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "firstname",length = 70)
    private String firstName;
    @Column(name = "lastname",length = 90)
    private String lastName;
    @Column(name = "nationalCode",length = 10,unique = true)
    private String nationalCode;
    @Column(name = "age")
    private Integer age;
    @Column(name = "email",length = 30)
    private String email;
    @Column(name = "mobile",length = 11)
    private String mobile;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(nationalCode, that.nationalCode) && Objects.equals(age, that.age) && Objects.equals(email, that.email) && Objects.equals(mobile, that.mobile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nationalCode, age, email, mobile);
    }
    @Override
    public String toString() {
        return  new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("firstname", firstName)
                .append("lastname", lastName)
                .append("nationalCode", nationalCode)
                .append("age", age)
                .append("email", email)
                .append("mobile", mobile)
                .toString();
    }
}
