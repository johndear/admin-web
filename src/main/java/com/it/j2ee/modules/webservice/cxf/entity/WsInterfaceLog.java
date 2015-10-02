package com.it.j2ee.modules.webservice.cxf.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 接口异常日志
 * <p/>
 * Created by Fuwei on 2015/1/10.
 */
@Entity
@Table(name = "WS_INTERFACE_LOG")
public class WsInterfaceLog {
    private Long id;
    private String name; //接口名称
    private String content; //详细内容
    private Date createTime; //创建时间

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "SEQ_WS_INTERFACE_LOG", allocationSize = 1, sequenceName = "SEQ_WS_INTERFACE_LOG")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_WS_INTERFACE_LOG")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "create_time")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
