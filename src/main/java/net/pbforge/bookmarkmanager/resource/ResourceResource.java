package net.pbforge.bookmarkmanager.resource;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ResourceResource {
    private Resource indexHtml = new ClassPathResource("/static/index.html");

    // Note: We use vue in history mode. We must list all paths we
    // want to handled by the SPA here.
    @GetMapping("/")
    public Resource serveIndex() {
        // TODO Make it cacheable to improve performance
        return indexHtml;
    }
}
