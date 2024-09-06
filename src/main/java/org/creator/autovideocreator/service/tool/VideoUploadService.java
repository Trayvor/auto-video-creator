package org.creator.autovideocreator.service.tool;

import java.io.File;
import java.util.List;

public interface VideoUploadService {
    void uploadVideo(String refreshToken, File file, String title, String description,
                     List<String> tags);
}
