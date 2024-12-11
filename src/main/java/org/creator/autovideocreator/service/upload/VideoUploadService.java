package org.creator.autovideocreator.service.upload;

import org.creator.autovideocreator.model.Tag;

import java.io.File;
import java.util.List;

public interface VideoUploadService {
    public void uploadVideo(String refreshToken, File file, String title, String description, List<Tag> tags);
}
