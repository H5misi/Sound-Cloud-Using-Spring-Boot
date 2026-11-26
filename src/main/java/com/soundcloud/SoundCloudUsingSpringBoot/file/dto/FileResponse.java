package com.soundcloud.SoundCloudUsingSpringBoot.file.dto;

import java.time.LocalDateTime;

import com.soundcloud.SoundCloudUsingSpringBoot.file.entity.FileStatus;
import com.soundcloud.SoundCloudUsingSpringBoot.file.entity.FileType;

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
