package net.pbforge.bookmarkmanager.service;

import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pbforge.bookmarkmanager.entity.BookmarkCreationV1;
import net.pbforge.bookmarkmanager.entity.BookmarkV1;
import net.pbforge.bookmarkmanager.dao.BookmarkDao;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookmarkService {
    BookmarkDao bookmarkDao;

    public Flux<BookmarkV1> getBookmarks() {
        return bookmarkDao.findAll();
    }

    public Mono<BookmarkV1> getBookmark(@NonNull String id) {
        return bookmarkDao.findById(id);
    }

    public Mono<Void> deleteBookmark(@NonNull String id) {
        return bookmarkDao.deleteById(id);
    }

    public Mono<BookmarkV1> createBookmark(@NonNull BookmarkCreationV1 bookmarkCreation) {
        return bookmarkDao.save(bookmarkCreation.toBookmark());
    }

    public Mono<BookmarkV1> updateBookmark(@NonNull BookmarkV1 bookmark) {
        Preconditions.checkArgument(bookmark.getId() != null, "No Id specified for bookmark");
        return bookmarkDao.save(bookmark);
    }
}
