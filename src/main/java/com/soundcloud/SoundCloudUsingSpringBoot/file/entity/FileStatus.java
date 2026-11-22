package com.soundcloud.SoundCloudUsingSpringBoot.file.entity;

public enum FileStatus {
    PENDING_UPLOAD, //The database record exists, but the file hasn't been uploaded to S3 yet.
    UPLOADED, //The file reached S3, but the application hasn't finished validating or confirming it.
    READY, //The file has been accepted and is available for use.
    PENDING_DELETION //The file is marked for deletion, but its S3 object may not have been deleted yet.

}
