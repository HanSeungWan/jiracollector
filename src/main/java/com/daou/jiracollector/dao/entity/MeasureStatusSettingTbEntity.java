package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-03-31.
 */
@Entity
@Table(name = "measure_status_setting_tb", schema = "public", catalog = "jiradata")
public class MeasureStatusSettingTbEntity {
    private String statusMm;
    private String measureMm;
    private int projectId;
    private int measureStatusSettingTbId;
    private String jirastatusMm;

    @Basic
    @Column(name = "status_mm")
    public String getStatusMm() {
        return statusMm;
    }

    public void setStatusMm(String statusMm) {
        this.statusMm = statusMm;
    }

    @Basic
    @Column(name = "measure_mm")
    public String getMeasureMm() {
        return measureMm;
    }

    public void setMeasureMm(String measureMm) {
        this.measureMm = measureMm;
    }

    @Basic
    @Column(name = "project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Id
    @Column(name = "measure_status_setting_tb_id")
    public int getMeasureStatusSettingTbId() {
        return measureStatusSettingTbId;
    }

    public void setMeasureStatusSettingTbId(int measureStatusSettingTbId) {
        this.measureStatusSettingTbId = measureStatusSettingTbId;
    }

    @Basic
    @Column(name = "jirastatus_mm")
    public String getJirastatusMm() {
        return jirastatusMm;
    }

    public void setJirastatusMm(String jirastatusMm) {
        this.jirastatusMm = jirastatusMm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeasureStatusSettingTbEntity that = (MeasureStatusSettingTbEntity) o;

        if (projectId != that.projectId) return false;
        if (measureStatusSettingTbId != that.measureStatusSettingTbId) return false;
        if (statusMm != null ? !statusMm.equals(that.statusMm) : that.statusMm != null) return false;
        if (measureMm != null ? !measureMm.equals(that.measureMm) : that.measureMm != null) return false;
        if (jirastatusMm != null ? !jirastatusMm.equals(that.jirastatusMm) : that.jirastatusMm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = statusMm != null ? statusMm.hashCode() : 0;
        result = 31 * result + (measureMm != null ? measureMm.hashCode() : 0);
        result = 31 * result + projectId;
        result = 31 * result + measureStatusSettingTbId;
        result = 31 * result + (jirastatusMm != null ? jirastatusMm.hashCode() : 0);
        return result;
    }
}
