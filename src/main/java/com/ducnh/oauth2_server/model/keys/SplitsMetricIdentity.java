package com.ducnh.oauth2_server.model.keys;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class SplitsMetricIdentity implements Serializable {
    
    @NotNull
    private Long activityId;
    @NotNull
    private Integer splitId;
    
    public SplitsMetricIdentity(Long activityId, Integer splitId){
        this.activityId = activityId;
        this.splitId = splitId;
    }

    public SplitsMetricIdentity() {
    }

    public Long getActivityId() {
        return this.activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Integer getSplitId() {
        return this.splitId;
    }

    public void setSplitId(Integer splitId) {
        this.splitId = splitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SplitsMetricIdentity that = (SplitsMetricIdentity) o;
        return that.getActivityId().equals(this.getActivityId()) && that.getSplitId().equals(this.getSplitId());
    }

    @Override
    public int hashCode() {
        int result = this.activityId.hashCode();
        result = result * 31 + this.splitId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "SplitsMetricIdentity [activityId=" + activityId + ", splitId=" + splitId + "]";
    }

    public String getKey() {
        return this.activityId + "_" + this.splitId;
    }

}
