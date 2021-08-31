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
import net.pbforge.bookmarkmanager.entity.BookmarkV1.BookmarkV1Builder;
import org.springframework.data.annotation.Id;


@Data
@With
@Builder(toBuilder = true)
@JsonDeserialize(builder = BookmarkV1Builder.class)
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookmarkV1 {
    public static final String MIME = "application/vnd.net.pbforge.bookmark-v1";
    public static final String LIST_MIME = "application/vnd.net.pbforge.bookmark-list-v1";

    @Id
    String id;
    @NonNull
    String name;
    @NonNull
    String url;
    @NonNull
    @Default
    String description = "";

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookmarkV1Builder {
    }
}
