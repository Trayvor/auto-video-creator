package org.creator.autovideocreator.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity()
@Table(name = "upload_time")
@RequiredArgsConstructor
@Setter
@Getter
public class UploadTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalTime timeToUpload;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
}
