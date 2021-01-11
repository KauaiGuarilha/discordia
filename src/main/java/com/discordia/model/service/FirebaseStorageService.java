package com.discordia.model.service;

import com.discordia.model.dto.RoomImageDTOResponse;
import com.discordia.model.entity.Room;
import com.discordia.model.entity.RoomImage;
import com.discordia.model.repository.RoomImageRepository;
import com.discordia.model.repository.RoomRepository;
import com.discordia.utils.InitializationUtils;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageService {

    @Autowired private RoomImageRepository roomImageRepository;
    @Autowired private RoomRepository roomRepository;
    @Autowired private InitializationUtils initializationUtils;

    public String upload(String fName, String mimiType, MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        System.out.println(bucket);

        byte[] bytes = file.getBytes();

        Blob blob = bucket.create(fName, bytes, mimiType);

        // URL public
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        return String.format(initializationUtils.getStorageGoogleApis(), bucket.getName(), fName);
    }

    public RoomImageDTOResponse uploadDatabase(
            String fName, String mimiType, MultipartFile file, String idRoom) throws IOException {

        Optional<Room> room = roomRepository.findById(UUID.fromString(idRoom));

        if (!room.isPresent()) throw new RuntimeException("Could not find any room.");

        Bucket bucket = StorageClient.getInstance().bucket();
        byte[] bytes = file.getBytes();

        Blob blob = bucket.create(fName, bytes, mimiType);

        // URL public
        blob.createAcl(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        RoomImage roomImage =
                RoomImage.builder()
                        .room(room.get())
                        .pathImg(
                                String.format(
                                        initializationUtils.getStorageGoogleApis(),
                                        bucket.getName(),
                                        fName))
                        .build();

        roomImageRepository.save(roomImage);

        return RoomImageDTOResponse.builder()
                .base64(Base64.getEncoder().encodeToString(file.getBytes()))
                .url(
                        String.format(
                                initializationUtils.getStorageGoogleApis(),
                                bucket.getName(),
                                fName))
                .build();
    }

    @PostConstruct
    private void init() throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            InputStream inputStream =
                    FirebaseStorageService.class.getResourceAsStream("/serviceAccountKey.json");

            System.out.println(inputStream);

            FirebaseOptions options =
                    new FirebaseOptions.Builder()
                            .setCredentials(GoogleCredentials.fromStream(inputStream))
                            .setStorageBucket(initializationUtils.getStorageBucket())
                            .setDatabaseUrl(initializationUtils.getDatabaseUrl())
                            .build();

            FirebaseApp.initializeApp(options);
        }
    }
}
