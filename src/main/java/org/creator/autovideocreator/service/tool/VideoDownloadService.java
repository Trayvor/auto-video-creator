package org.creator.autovideocreator.service.tool;

import java.io.File;

public interface VideoDownloadService {
    File download(String url, String name);
}
