package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final FilmService filmService;

    @PostMapping
    public Review addReview(@Valid @RequestBody Review review) throws ValidationException {
        Review addedReview = filmService.addReview(review);
        log.info("review with id = {} has been added", addedReview.getReviewId());
        return addedReview;
    }

    @PutMapping
    public Review updateReview(@Valid @RequestBody Review review) throws ValidationException {
        Review updatedReview = filmService.updateReview(review);
        log.info("review with id = {} has been updated", updatedReview.getReviewId());
        return updatedReview;
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Integer reviewId) throws ValidationException {
        filmService.deleteReview(reviewId);
        log.info("review with id = {} has been deleted", reviewId);
    }

    @GetMapping("/{id}")
    public Review getReviewById(@PathVariable("id") Integer reviewId) throws ValidationException {
        Review review = filmService.getReviewById(reviewId);
        log.info("review with id = {} has been found", reviewId);
        return review;
    }

    @GetMapping
    public List<Review> getReviewsForFilm(@RequestParam(required = false) Integer filmId,
                                          @RequestParam(defaultValue = "10") Integer count) throws ValidationException {
        List<Review> reviews = filmService.getReviewsForFilm(filmId, count);
        if (!reviews.isEmpty()) {
            log.info("amount of reviews is {}, id of the most useful review is {}", reviews.size(), reviews.get(0).getReviewId());
        }
        return reviews;
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable("id") Integer reviewId, @PathVariable Integer userId) throws ValidationException {
        filmService.addReviewLike(reviewId, userId, true);
        log.info("like is added");
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable("id") Integer reviewId, @PathVariable Integer userId) throws ValidationException {
        filmService.deleteReviewLike(reviewId, userId);
        log.info("like is deleted");
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable("id") Integer reviewId, @PathVariable Integer userId) throws ValidationException {
        filmService.addReviewLike(reviewId, userId, false);
        log.info("dislike is added");
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void deleteDislike(@PathVariable("id") Integer reviewId, @PathVariable Integer userId) throws ValidationException {
        filmService.deleteReviewLike(reviewId, userId);
        log.info("dislike is deleted");
    }
}