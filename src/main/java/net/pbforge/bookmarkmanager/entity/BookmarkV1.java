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
import net.pbforge.bookmarkmanager.entity.BookmarkV1.BookmarkV1Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

// Note on entity design:
// 1. All entities are immutable. A modified copy can be created with the "with" methods if only property is different
//    or the "toBuilder" if multiple properties need to be changed in one go.
// 2. All entities are created through:
// 2a. A lombok builder. This is used for instancing from code and by the Jackson Library.
// 2b. Synthetic private constructor invocation by reactive-mongo
// 3. Validation is done on these levels.
// 3a. Bean validation methods. They automatically validated when an entity arrives at the REST controller.
//     They are also picked up by spring-doc and are reflected in the json-schemas of the openapi-v3 spec.
// 3b. Validation methods on the builder level. All entity construction except the one done by mongo pass through
//     those sections.
// This approach has the small downside of doing validation two times for entities that arrive via REST.

@Data
@With
@Document
@Builder(toBuilder = true)
@JsonDeserialize(builder = BookmarkV1Builder.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookmarkV1 {
    // Note: This serves as an example, there is a lot more complex reg-exes that could be used here
    static final String URL_PATTERN = "http(s)?://.*";
    public static final String MIME = "application/vnd.net.pbforge.bookmark-v1+json";
    public static final String LIST_MIME = "application/vnd.net.pbforge.bookmark-list-v1+json";

    // Note: Using @NonNull here makes it harder to work with it in the context of reactive-mongo-repo.
    // The class representation used for creating and getting entities is the same. We need id to be null
    // when we create the entity.
    // However, on the specification level this is still a required property.
    @Id
    @NotNull
    @Schema(required = true, description = "The id of the bookmark")
    String id;

    @Size(min = 3, max = 32)
    @Schema(required = true, description = "The name of the bookmark")
    @NonNull
    // Secondary  key
    @Indexed(unique = true)
    String name;

    @Pattern(regexp = URL_PATTERN)
    @Schema(required = true, description = "The URL of the bookmark")
    @NonNull
    String url;

    @Size(max = 256)
    @Schema(description = "The description provided for a bookmark")
    @NonNull
    @Default
    String description = "";

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookmarkV1Builder {
        public BookmarkV1Builder name(@NonNull String name) {
            Preconditions.checkArgument(name.length() <= 32 && name.length() >= 3,
                    "Name must be non null and between 3 and 32 characters long.");
            this.name = name;
            return this;
        }

        public BookmarkV1Builder url(@NonNull String url) {
            Preconditions.checkArgument(url.matches(URL_PATTERN), "Url must match the patter " + URL_PATTERN);
            this.url = url;
            return this;
        }
    }
}
