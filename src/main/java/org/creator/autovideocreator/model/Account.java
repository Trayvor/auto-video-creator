package org.creator.autovideocreator.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Entity
@Table(name = "accounts")
@RequiredArgsConstructor
@Setter
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String securityToken;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public static enum AccountType {
        YOUTUBE("Youtube");

        private final String displayName;

        AccountType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }
}
