package org.creator.autovideocreator.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "projects")
@RequiredArgsConstructor
@Setter
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<YoutubeVideo> youtubeVideos = new ArrayList<>();
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<InternalVideo> internalVideos = new ArrayList<>();
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UploadTime> uploadTimes = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(nullable = false)
    private int videosDuration = 60;
    @Column
    private String timezoneId = "UTC";
}
