package sustenappnewslettersapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sustenappnewslettersapi.dto.NewsletterDto;
import sustenappnewslettersapi.exception.ResponseBody;
import sustenappnewslettersapi.service.NewsletterService;

@RestController
@RequiredArgsConstructor
public class NewsletterController {
    private final NewsletterService newsletterService;

    @PostMapping("/newsletter")
    public ResponseEntity<ResponseBody> save(@RequestBody NewsletterDto newsletter) {
        return ResponseEntity.status(HttpStatus.OK).body(newsletterService.sendNewsletter(newsletter));
    }
}
