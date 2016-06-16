package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-03-31.
 */
@Entity
@Table(name = "user_tb", schema = "public", catalog = "jiradata")
public class UserTbEntity {
    private String nameMm;
    private int userTbId;

    @Basic
    @Column(name = "name_mm")
    public String getNameMm() {
        return nameMm;
    }

    public void setNameMm(String nameMm) {
        this.nameMm = nameMm;
    }

    @Id
    @Column(name = "user_tb_id")
    public int getUserTbId() {
        return userTbId;
    }

    public void setUserTbId(int userTbId) {
        this.userTbId = userTbId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserTbEntity that = (UserTbEntity) o;

        if (userTbId != that.userTbId) return false;
        if (nameMm != null ? !nameMm.equals(that.nameMm) : that.nameMm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nameMm != null ? nameMm.hashCode() : 0;
        result = 31 * result + userTbId;
        return result;
    }
}
