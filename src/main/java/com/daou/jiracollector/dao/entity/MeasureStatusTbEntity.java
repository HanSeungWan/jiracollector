package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-03-31.
 */
@Entity
@Table(name = "measure_status_tb", schema = "public", catalog = "jiradata")
public class MeasureStatusTbEntity {
    private String dateYdm;
    private int projectId;
    private int versionId;
    private int countNo;
    private int measureStatusTbId;
    private int statusId;

    @Basic
    @Column(name = "date_ydm")
    public String getDateYdm() {
        return dateYdm;
    }

    public void setDateYdm(String dateYdm) {
        this.dateYdm = dateYdm;
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
    @Column(name = "count_no")
    public int getCountNo() {
        return countNo;
    }

    public void setCountNo(int countNo) {
        this.countNo = countNo;
    }

    @Id
    @Column(name = "measure_status_tb_id")
    public int getMeasureStatusTbId() {
        return measureStatusTbId;
    }

    public void setMeasureStatusTbId(int measureStatusTbId) {
        this.measureStatusTbId = measureStatusTbId;
    }

    @Basic
    @Column(name = "status_id")
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasureStatusTbEntity that = (MeasureStatusTbEntity) o;

        if (projectId != that.projectId) return false;
        if (versionId != that.versionId) return false;
        if (countNo != that.countNo) return false;
        if (measureStatusTbId != that.measureStatusTbId) return false;
        if (statusId != that.statusId) return false;
        if (dateYdm != null ? !dateYdm.equals(that.dateYdm) : that.dateYdm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = dateYdm != null ? dateYdm.hashCode() : 0;
        result = 31 * result + projectId;
        result = 31 * result + versionId;
        result = 31 * result + countNo;
        result = 31 * result + measureStatusTbId;
        result = 31 * result + statusId;
        return result;
    }
}
