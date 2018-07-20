package com.example.tool.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * Created by if on 2017/11/22.
 */
@MappedSuperclass
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public abstract class BaseEntity<ID extends Serializable> extends AbstractEntity implements Serializable {
    protected ID id ;
    protected Date createTime;
    protected Date updateTime;
    protected String delTag = "0";
    protected Long updateBy;
    protected Long createBy;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotFound(action= NotFoundAction.IGNORE)
    @Column(name = "id")
    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotFound(action= NotFoundAction.IGNORE)
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @NotFound(action= NotFoundAction.IGNORE)
    @Column(name = "update_time", nullable = true)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    @Column(name = "update_by", nullable = true)
    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    @Column(name = "create_by", nullable = true)
    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }


}
