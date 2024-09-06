package org.creator.autovideocreator.service.tool.impl;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.creator.autovideocreator.service.tool.TranscriptionAudioService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class WhisperTranscriptionAudioServiceImpl implements TranscriptionAudioService {
//    @Value("${openai.api.key}")
//    private String openaiApiKey = "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";
    private String openaiApiKey = "sk-XfRiBxp2kBPXzUeFwKVG4CMFFXbjum8r_ioKXUMqrbT3BlbkFJvKHaQl8u5TVMgSkncLUDa1tDB_TYZh18l20TlORvAA";
    private static final String apiEndpoint = "https://api.openai.com/v1/audio/transcriptions";

    @Override
    public String transcriptAudio(String audioPath) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost uploadFile = new HttpPost(apiEndpoint);

            // Set up headers
            uploadFile.setHeader("Authorization", "Bearer " + openaiApiKey);

            // Create MultipartEntityBuilder
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("model", "whisper-1");
            builder.addPart("file", new FileBody(new File(audioPath), ContentType.MULTIPART_FORM_DATA));

            HttpEntity multipart = builder.build();
            uploadFile.setEntity(multipart);
            System.out.println("Audio was sent to whisper");
            // Execute request
            try (CloseableHttpResponse response = httpClient.execute(uploadFile)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    String result = EntityUtils.toString(responseEntity);
                    System.out.println(result);
                    return result;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}