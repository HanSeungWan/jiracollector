package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-03-31.
 */
@Entity
@Table(name = "admin_tb", schema = "public", catalog = "jiradata")
public class AdminTbEntity {
    private int adminTbId;
    private int projectId;
    private int versionId;
    private String startdateYmd;
    private String enddateYmd;

    @Id
    @Column(name = "admin_tb_id")
    public int getAdminTbId() {
        return adminTbId;
    }

    public void setAdminTbId(int adminTbId) {
        this.adminTbId = adminTbId;
    }

    @Basic
    @Column(name = "project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "version_id")
    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    @Basic
    @Column(name = "startdate_ymd")
    public String getStartdateYmd() {
        return startdateYmd;
    }

    public void setStartdateYmd(String startdateYmd) {
        this.startdateYmd = startdateYmd;
    }

    @Basic
    @Column(name = "enddate_ymd")
    public String getEnddateYmd() {
        return enddateYmd;
    }

    public void setEnddateYmd(String enddateYmd) {
        this.enddateYmd = enddateYmd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminTbEntity that = (AdminTbEntity) o;

        if (adminTbId != that.adminTbId) return false;
        if (projectId != that.projectId) return false;
        if (versionId != that.versionId) return false;
        if (startdateYmd != null ? !startdateYmd.equals(that.startdateYmd) : that.startdateYmd != null) return false;
        if (enddateYmd != null ? !enddateYmd.equals(that.enddateYmd) : that.enddateYmd != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = adminTbId;
        result = 31 * result + projectId;
        result = 31 * result + versionId;
        result = 31 * result + (startdateYmd != null ? startdateYmd.hashCode() : 0);
        result = 31 * result + (enddateYmd != null ? enddateYmd.hashCode() : 0);
        return result;
    }
}
