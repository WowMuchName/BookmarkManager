package net.pbforge.bookmarkmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NonNull;
import lombok.With;
import lombok.experimental.FieldDefaults;
import net.pbforge.bookmarkmanager.entity.BookmarkCreationV1.BookmarkCreationV1Builder;

@Data
@With
@Builder(toBuilder = true)
@JsonDeserialize(builder = BookmarkCreationV1Builder.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookmarkCreationV1 {
    @NonNull
    String name;
    @NonNull
    String url;
    @NonNull
    @Default
    String description = "";

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookmarkCreationV1Builder {
    }

    public BookmarkV1 toBookmark() {
        return BookmarkV1.builder()
                .description(description)
                .name(name)
                .url(url)
                .build();
    }
}
