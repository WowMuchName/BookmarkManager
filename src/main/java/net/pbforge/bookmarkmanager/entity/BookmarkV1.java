package net.pbforge.bookmarkmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;
import lombok.With;
import lombok.experimental.FieldDefaults;
import net.pbforge.bookmarkmanager.entity.BookmarkV1.BookmarkV1Builder;
import org.springframework.data.annotation.Id;


@Data
@With
@Builder(toBuilder = true)
@JsonDeserialize(builder = BookmarkV1Builder.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookmarkV1 {
    public static final String MIME = "application/vnd.net.pbforge.bookmark-v1+json";
    public static final String LIST_MIME = "application/vnd.net.pbforge.bookmark-list-v1+json";

    // Note: Using @NonNull here makes it harder to work with in the dao.
    // However, on the specification level this is still a required property.
    @Id
    @Schema(required = true, description = "The id of the bookmark")
    String id;

    @Schema(required = true, description = "The name of the bookmark")
    @NonNull
    String name;

    @Schema(required = true, description = "The URL of the bookmark")
    @NonNull
    String url;

    @Schema(description = "The description provided for a bookmark")
    @NonNull
    @Default
    String description = "";

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookmarkV1Builder {
    }
}
