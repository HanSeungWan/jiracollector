package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-03-31.
 */
@Entity
@Table(name = "version_tb", schema = "public", catalog = "jiradata")
public class VersionTbEntity {
    private int projectId;
    private String versionMm;
    private int versionTbId;
    private boolean releaseBoolean;

    @Basic
    @Column(name = "project_id")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "version_mm")
    public String getVersionMm() {
        return versionMm;
    }

    public void setVersionMm(String versionMm) {
        this.versionMm = versionMm;
    }

    @Id
    @Column(name = "version_tb_id")
    public int getVersionTbId() {
        return versionTbId;
    }

    public void setVersionTbId(int versionTbId) {
        this.versionTbId = versionTbId;
    }

    @Basic
    @Column(name = "release_boolean")
    public boolean isReleaseBoolean() {
        return releaseBoolean;
    }

    public void setReleaseBoolean(boolean releaseBoolean) {
        this.releaseBoolean = releaseBoolean;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VersionTbEntity that = (VersionTbEntity) o;

        if (projectId != that.projectId) return false;
        if (versionTbId != that.versionTbId) return false;
        if (releaseBoolean != that.releaseBoolean) return false;
        if (versionMm != null ? !versionMm.equals(that.versionMm) : that.versionMm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = projectId;
        result = 31 * result + (versionMm != null ? versionMm.hashCode() : 0);
        result = 31 * result + versionTbId;
        result = 31 * result + (releaseBoolean ? 1 : 0);
        return result;
    }
}
