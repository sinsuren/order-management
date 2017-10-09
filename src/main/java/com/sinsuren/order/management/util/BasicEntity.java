package com.sinsuren.order.management.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by surender.s on 05/10/17.
 */
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@Getter
@Setter
public abstract class BasicEntity extends AbstractPersistable<Long> implements Serializable {


    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    @LastModifiedBy
    private String updatedBy;

}