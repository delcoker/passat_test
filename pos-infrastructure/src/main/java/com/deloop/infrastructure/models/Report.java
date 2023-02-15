package com.deloop.infrastructure.models;

import io.ebean.Model;
import io.ebean.annotation.DbDefault;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@Entity
@Table(name = "reports")
public class Report extends Model {
    @Column
    @WhenCreated
    @DbDefault("2020-04-26 00:00")
    public LocalDateTime createdAt;

    @Column
    @WhenModified
    @DbDefault("2020-04-26 00:00")
    public LocalDateTime updatedAt;

    @Id
    private long id;

    @Column
    private String name;

    @Column
    private String details;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<Sentiment> sentiments;

}
