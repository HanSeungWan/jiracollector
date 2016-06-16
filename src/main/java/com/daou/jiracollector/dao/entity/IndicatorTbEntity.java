package com.daou.jiracollector.dao.entity;

import javax.persistence.*;

/**
 * Created by intern on 2016-04-06.
 */
@Entity
@Table(name = "indicator_tb", schema = "public", catalog = "jiradata")
public class IndicatorTbEntity {
    private String indicatorMm;
    private int indicatorId;

    @Basic
    @Column(name = "indicator_mm")
    public String getIndicatorMm() {
        return indicatorMm;
    }

    public void setIndicatorMm(String indicatorMm) {
        this.indicatorMm = indicatorMm;
    }

    @Id
    @Column(name = "indicator_id")
    public int getIndicatorId() {
        return indicatorId;
    }

    public void setIndicatorId(int indicatorId) {
        this.indicatorId = indicatorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndicatorTbEntity that = (IndicatorTbEntity) o;

        if (indicatorId != that.indicatorId) return false;
        if (indicatorMm != null ? !indicatorMm.equals(that.indicatorMm) : that.indicatorMm != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = indicatorMm != null ? indicatorMm.hashCode() : 0;
        result = 31 * result + indicatorId;
        return result;
    }
}
