package com.soundcloud.SoundCloudUsingSpringBoot.file.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter 
@Setter
@NoArgsConstructor // Hibernate needs to instantiate entities when retrieving records from the
                   // database, it uses this to create an object before population the fields
@Table(name = "stored_files")
public class StoredFile {

    @Id
    @GeneratedValue 
    private Long id;

    @Column(nullable = false, unique = true, length = 512)
    private String objectKey;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false, length = 100)
    private String contentType;

    @Enumerated(EnumType.STRING) //to store the enum names in the DB
    @Column(nullable = false)
    private FileType fileType;

    private Long size;

    @ManyToOne(fetch = FetchType.LAZY) //so the User entity doesn't load entirely
    @JoinColumn(name = "uploaded_by") // in Hibernate (ORM framework implements JPA) this specifies the foreign key
                                      // column used to link an entity association or element collection on the owning
                                      // side of a relationship.
    private User uploadedBy;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileStatus fileStatus;

    @CreationTimestamp 
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


}
