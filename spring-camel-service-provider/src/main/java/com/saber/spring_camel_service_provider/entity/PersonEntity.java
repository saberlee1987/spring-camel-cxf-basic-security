package com.saber.spring_camel_service_provider.entity;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "persons",uniqueConstraints = @UniqueConstraint(name = "nationalUnique",columnNames ={"nationalCode"} ))
@Getter
@Setter
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
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE)
                .create().toJson(this, PersonEntity.class);
    }
}
