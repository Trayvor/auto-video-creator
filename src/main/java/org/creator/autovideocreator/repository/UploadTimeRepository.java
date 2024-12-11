package org.creator.autovideocreator.repository;

import org.creator.autovideocreator.model.UploadTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.List;

public interface UploadTimeRepository extends JpaRepository<UploadTime, Long> {
    List<UploadTime> findByTimeToUpload(LocalTime timeToUpload);
}
