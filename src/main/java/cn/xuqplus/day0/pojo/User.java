package cn.xuqplus.day0.pojo;

import java.io.Serializable;

public class User implements Serializable {
    public Long id;
    public String name;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
