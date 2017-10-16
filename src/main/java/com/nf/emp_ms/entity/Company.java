package com.nf.emp_ms.entity;

import javax.persistence.*;

@Entity
@Table(name = "company2")
public class Company {
    @Id @GeneratedValue
    private long id;

    private String name;

    private String address;

    @Version
    private long version; // 数据的版本号

    public Company() {
    }

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "compno=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
