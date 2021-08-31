package net.pbforge.bookmarkmanager;

import net.pbforge.bookmarkmanager.entity.BookmarkCreationV1;
import net.pbforge.bookmarkmanager.entity.BookmarkV1;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookmarkManagerApplicationTests {
    @LocalServerPort
    public Integer localPort;
    public String url;
    public WebClient client;

    @Test
    void setup() {
        url = "http://127.0.0.1:" + localPort;
        client = WebClient.create(url);
    }

    @Test
    void testBookmarkCreation() {
        BookmarkV1 bookmarkCreationV1 = client.put().bodyValue(BookmarkCreationV1.builder()
                .name("Test")
                .url("http://localhost:8080")
                .description("Some description"))
                .header("Content-Type", BookmarkCreationV1.MIME)
                .retrieve().bodyToMono(BookmarkV1.class).block();

    }
}
