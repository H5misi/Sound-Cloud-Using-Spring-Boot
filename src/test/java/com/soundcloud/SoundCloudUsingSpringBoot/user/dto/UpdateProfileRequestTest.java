
package com.soundcloud.SoundCloudUsingSpringBoot.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.AccountType;
import com.soundcloud.SoundCloudUsingSpringBoot.user.entity.ProfileVisibility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class UpdateProfileRequestTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void shouldHandleAllProfileFieldsCorrectly() throws Exception {

        // Case 1: All fields are omitted
        UpdateProfileRequest omitted = mapper.readValue(
                "{}",
                UpdateProfileRequest.class);

        assertAll("All omitted fields should remain undefined",
                () -> assertFalse(omitted.getDisplayName().isProvided()),
                () -> assertFalse(omitted.getBio().isProvided()),
                () -> assertFalse(omitted.getLocation().isProvided()),
                () -> assertFalse(omitted.getAccountType().isProvided()),
                () -> assertFalse(omitted.getProfileVisibility().isProvided()),
                () -> assertFalse(omitted.getInstagramUrl().isProvided()),
                () -> assertFalse(omitted.getTwitterUrl().isProvided()),
                () -> assertFalse(omitted.getWebsiteUrl().isProvided()),
                () -> assertFalse(omitted.getFavoriteGenres().isProvided()));

        // Case 2: All fields are explicitly null
        UpdateProfileRequest cleared = mapper.readValue(
                """
                        {
                            "displayName": null,
                            "bio": null,
                            "location": null,
                            "accountType": null,
                            "profileVisibility": null,
                            "instagramUrl": null,
                            "twitterUrl": null,
                            "websiteUrl": null,
                            "favoriteGenres": null
                        }
                        """,
                UpdateProfileRequest.class);

        assertAll("Explicit nulls should be provided with null values",
                () -> assertProvidedWithNull(cleared.getDisplayName()),
                () -> assertProvidedWithNull(cleared.getBio()),
                () -> assertProvidedWithNull(cleared.getLocation()),
                () -> assertProvidedWithNull(cleared.getAccountType()),
                () -> assertProvidedWithNull(cleared.getProfileVisibility()),
                () -> assertProvidedWithNull(cleared.getInstagramUrl()),
                () -> assertProvidedWithNull(cleared.getTwitterUrl()),
                () -> assertProvidedWithNull(cleared.getWebsiteUrl()),
                () -> assertProvidedWithNull(cleared.getFavoriteGenres()));

        // Case 3: All fields have actual values
        UpdateProfileRequest supplied = mapper.readValue(
                """
                        {
                            "displayName": "5amisi",
                            "bio": "Backend engineer",
                            "location": "Cairo",
                            "accountType": "ARTIST",
                            "profileVisibility": "PRIVATE",
                            "instagramUrl": "https://instagram.com/example",
                            "twitterUrl": "https://twitter.com/example",
                            "websiteUrl": "https://example.com",
                            "favoriteGenres": ["Rock", "Jazz"]
                        }
                        """,
                UpdateProfileRequest.class);

        assertAll("All supplied values should be preserved",
                () -> assertProvidedWithValue(
                        supplied.getDisplayName(), "5amisi"),
                () -> assertProvidedWithValue(
                        supplied.getBio(), "Backend engineer"),
                () -> assertProvidedWithValue(
                        supplied.getLocation(), "Cairo"),
                () -> assertProvidedWithValue(
                        supplied.getAccountType(), AccountType.ARTIST),
                () -> assertProvidedWithValue(
                        supplied.getProfileVisibility(),
                        ProfileVisibility.PRIVATE),
                () -> assertProvidedWithValue(
                        supplied.getInstagramUrl(),
                        "https://instagram.com/example"),
                () -> assertProvidedWithValue(
                        supplied.getTwitterUrl(),
                        "https://twitter.com/example"),
                () -> assertProvidedWithValue(
                        supplied.getWebsiteUrl(),
                        "https://example.com"),
                () -> assertProvidedWithValue(
                        supplied.getFavoriteGenres(),
                        Set.of("Rock", "Jazz")));
    }

    private static void assertProvidedWithNull(PatchField<?> field) {
        assertTrue(field.isProvided());
        assertNull(field.getValue());
    }

    private static <T> void assertProvidedWithValue(
            PatchField<T> field,
            T expectedValue) {
        assertTrue(field.isProvided());
        assertEquals(expectedValue, field.getValue());
    }
}
