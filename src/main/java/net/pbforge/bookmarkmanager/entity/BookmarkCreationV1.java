package net.pbforge.bookmarkmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.common.base.Preconditions;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;
import lombok.With;
import lombok.experimental.FieldDefaults;
import net.pbforge.bookmarkmanager.entity.BookmarkCreationV1.BookmarkCreationV1Builder;
import net.pbforge.bookmarkmanager.entity.BookmarkV1.BookmarkV1Builder;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static net.pbforge.bookmarkmanager.entity.BookmarkV1.URL_PATTERN;

// Note on entity design:
// see BookmarkV1
@Data
@With
@Builder(toBuilder = true)
@JsonDeserialize(builder = BookmarkCreationV1Builder.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookmarkCreationV1 {
    public static final String MIME = "application/vnd.net.pbforge.bookmark-creation-v1+json";

    @Schema(required = true, description = "The name of the bookmark")
    @Size(min = 3, max = 32)
    @NonNull
    String name;

    @Schema(required = true, description = "The URL of the bookmark")
    @Pattern(regexp = URL_PATTERN)
    @NonNull
    String url;

    @Schema(description = "The description provided for a bookmark")
    @Size(max = 256)
    @NonNull
    @Default
    String description = "";

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookmarkCreationV1Builder {
        public BookmarkCreationV1Builder name(@NonNull String name) {
            Preconditions.checkArgument(name.length() <= 32 && name.length() >= 3,
                    "Name must be non null and between 3 and 32 characters long.");
            this.name = name;
            return this;
        }

        public BookmarkCreationV1Builder url(@NonNull String url) {
            Preconditions.checkArgument(url.matches(URL_PATTERN), "Url must match the patter " + URL_PATTERN);
            this.url = url;
            return this;
        }
    }

    public BookmarkV1 toBookmark() {
        return BookmarkV1.builder()
                .description(description)
                .name(name)
                .url(url)
                .build();
    }
}
