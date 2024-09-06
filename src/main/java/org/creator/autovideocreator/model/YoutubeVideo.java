package org.creator.autovideocreator.model;

import autovalue.shaded.org.jetbrains.annotations.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity()
@Table(name = "youtube_videos")
@RequiredArgsConstructor
@Setter
@Getter
public class YoutubeVideo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "video_id", nullable = false)
    private String videoId;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(name = "status")
    private String status;
}
