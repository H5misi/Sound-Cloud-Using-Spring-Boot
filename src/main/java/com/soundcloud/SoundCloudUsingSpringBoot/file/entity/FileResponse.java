package com.soundcloud.SoundCloudUsingSpringBoot.file.entity;

import java.time.LocalDateTime;

public record FileResponse(
        Long id,
        String originalFilename,
        String contentType,
        FileType fileType,
        Long size,
        FileStatus fileStatus,
        LocalDateTime createdAt

    ) {

}
