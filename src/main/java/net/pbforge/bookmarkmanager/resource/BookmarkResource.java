package net.pbforge.bookmarkmanager.resource;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pbforge.bookmarkmanager.entity.BookmarkCreationV1;
import net.pbforge.bookmarkmanager.entity.BookmarkV1;
import net.pbforge.bookmarkmanager.service.BookmarkService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController()
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("bookmark")
public class BookmarkResource {
    BookmarkService bookmarkService;

    @GetMapping(produces = BookmarkV1.LIST_MIME)
    public Flux<BookmarkV1> list() {
        return bookmarkService.getBookmarks();
    }

    @GetMapping(value = "{id}", produces = BookmarkV1.MIME)
    public Mono<BookmarkV1> get(@PathVariable("id") String id) {
        return bookmarkService.getBookmark(id);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return bookmarkService.deleteBookmark(id);
    }

    @PostMapping(consumes = BookmarkV1.MIME, produces = BookmarkV1.MIME)
    public Mono<BookmarkV1> update(@RequestBody BookmarkV1 bookmark) {
        return bookmarkService.updateBookmark(bookmark);
    }

    @PutMapping(consumes = BookmarkCreationV1.MIME, produces = BookmarkV1.MIME)
    public Mono<BookmarkV1> create(@RequestBody BookmarkCreationV1 bookmark) {
        return bookmarkService.createBookmark(bookmark);
    }
}
