package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-04-11.
 */
@Entity
@Table(name = "issuetype_tb", schema = "public", catalog = "jiradata")
public class IssuetypeTbEntity {
    private int issuetypeTbId;
    private String issuetypeMm;
    private int projectId;

    @Id
    @Column(name = "issuetype_tb_id")
    public int getIssuetypeTbId() {
        return issuetypeTbId;
    }

    public void setIssuetypeTbId(int issuetypeTbId) {
        this.issuetypeTbId = issuetypeTbId;
    }

    @Basic
    @Column(name = "issuetype_mm")
    public String getIssuetypeMm() {
        return issuetypeMm;
    }

    public void setIssuetypeMm(String issuetypeMm) {
        this.issuetypeMm = issuetypeMm;
    }

    @Basic
    @Column(name = "project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IssuetypeTbEntity that = (IssuetypeTbEntity) o;

        if (issuetypeTbId != that.issuetypeTbId) return false;
        if (projectId != that.projectId) return false;
        if (issuetypeMm != null ? !issuetypeMm.equals(that.issuetypeMm) : that.issuetypeMm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = issuetypeTbId;
        result = 31 * result + (issuetypeMm != null ? issuetypeMm.hashCode() : 0);
        result = 31 * result + projectId;
        return result;
    }
}
