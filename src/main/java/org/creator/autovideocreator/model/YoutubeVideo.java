package org.creator.autovideocreator.model;

import autovalue.shaded.org.jetbrains.annotations.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
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
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    @Column(nullable = false)
    private int startTime;
    @Column(nullable = false)
    private int endTime;
}
