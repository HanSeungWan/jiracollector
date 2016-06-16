package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-04-06.
 */
@Entity
@Table(name = "indicator_setting_tb", schema = "public", catalog = "jiradata")
public class IndicatorSettingTbEntity {
    private int indicatorSettingTbId;
    private int projectId;
    private int targetNo;
    private String targetCharMm;
    private String explanationMm;
    private int graphtypeId;

    @Id
    @Column(name = "indicator_setting_tb_id")
    public int getIndicatorSettingTbId() {
        return indicatorSettingTbId;
    }

    public void setIndicatorSettingTbId(int indicatorSettingTbId) {
        this.indicatorSettingTbId = indicatorSettingTbId;
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
    @Column(name = "target_no")
    public int getTargetNo() {
        return targetNo;
    }

    public void setTargetNo(int targetNo) {
        this.targetNo = targetNo;
    }

    @Basic
    @Column(name = "target_char_mm")
    public String getTargetCharMm() {
        return targetCharMm;
    }

    public void setTargetCharMm(String targetCharMm) {
        this.targetCharMm = targetCharMm;
    }

    @Basic
    @Column(name = "explanation_mm")
    public String getExplanationMm() {
        return explanationMm;
    }

    public void setExplanationMm(String explanationMm) {
        this.explanationMm = explanationMm;
    }

    @Basic
    @Column(name = "graphtype_id")
    public int getGraphtypeId() {
        return graphtypeId;
    }

    public void setGraphtypeId(int graphtypeId) {
        this.graphtypeId = graphtypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndicatorSettingTbEntity that = (IndicatorSettingTbEntity) o;

        if (indicatorSettingTbId != that.indicatorSettingTbId) return false;
        if (projectId != that.projectId) return false;
        if (targetNo != that.targetNo) return false;
        if (graphtypeId != that.graphtypeId) return false;
        if (targetCharMm != null ? !targetCharMm.equals(that.targetCharMm) : that.targetCharMm != null) return false;
        if (explanationMm != null ? !explanationMm.equals(that.explanationMm) : that.explanationMm != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = indicatorSettingTbId;
        result = 31 * result + projectId;
        result = 31 * result + targetNo;
        result = 31 * result + (targetCharMm != null ? targetCharMm.hashCode() : 0);
        result = 31 * result + (explanationMm != null ? explanationMm.hashCode() : 0);
        result = 31 * result + graphtypeId;
        return result;
    }
}
