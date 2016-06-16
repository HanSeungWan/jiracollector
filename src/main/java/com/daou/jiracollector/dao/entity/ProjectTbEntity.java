package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-03-31.
 */
@Entity
@Table(name = "project_tb", schema = "public", catalog = "jiradata")
public class ProjectTbEntity {
    private String projectMm;
    private String jiraProjectMm;
    private int projectTbId;
    private int releaseNo;
    private boolean activeBoolean;
    private int managerId;

    @Basic
    @Column(name = "project_mm")
    public String getProjectMm() {
        return projectMm;
    }

    public void setProjectMm(String projectMm) {
        this.projectMm = projectMm;
    }

    @Basic
    @Column(name = "jira_project_mm")
    public String getJiraProjectMm() {
        return jiraProjectMm;
    }

    public void setJiraProjectMm(String jiraProjectMm) {
        this.jiraProjectMm = jiraProjectMm;
    }

    @Id
    @Column(name = "project_tb_id")
    public int getProjectTbId() {
        return projectTbId;
    }

    public void setProjectTbId(int projectTbId) {
        this.projectTbId = projectTbId;
    }

    @Basic
    @Column(name = "release_no")
    public int getReleaseNo() {
        return releaseNo;
    }

    public void setReleaseNo(int releaseNo) {
        this.releaseNo = releaseNo;
    }

    @Basic
    @Column(name = "active_boolean")
    public boolean isActiveBoolean() {
        return activeBoolean;
    }

    public void setActiveBoolean(boolean activeBoolean) {
        this.activeBoolean = activeBoolean;
    }

    @Basic
    @Column(name = "manager_id")
    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectTbEntity that = (ProjectTbEntity) o;

        if (projectTbId != that.projectTbId) return false;
        if (releaseNo != that.releaseNo) return false;
        if (activeBoolean != that.activeBoolean) return false;
        if (managerId != that.managerId) return false;
        if (projectMm != null ? !projectMm.equals(that.projectMm) : that.projectMm != null) return false;
        if (jiraProjectMm != null ? !jiraProjectMm.equals(that.jiraProjectMm) : that.jiraProjectMm != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectMm != null ? projectMm.hashCode() : 0;
        result = 31 * result + (jiraProjectMm != null ? jiraProjectMm.hashCode() : 0);
        result = 31 * result + projectTbId;
        result = 31 * result + releaseNo;
        result = 31 * result + (activeBoolean ? 1 : 0);
        result = 31 * result + managerId;
        return result;
    }
}
