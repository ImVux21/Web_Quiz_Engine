package engine.controller;

import engine.entity.Answer;
import engine.entity.Completion;
import engine.entity.Quiz;
import engine.entity.User;
import engine.repository.CompletionRepository;
import engine.service.CompletionService;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class QuizController {
    QuizService quizService;
    CompletionService completionService;
    CompletionRepository completionRepository;

    @Autowired
    public QuizController(QuizService quizService, CompletionService completionService,
                          CompletionRepository completionRepository) {
        this.quizService = quizService;
        this.completionService = completionService;
        this.completionRepository = completionRepository;
    }

    @GetMapping(value = "/quizzes", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Quiz> getQuizzes(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int pageSize) {
        return quizService.getQuizzes(page, pageSize);
    }

    @GetMapping(value = "/quizzes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Quiz getQuizById(@PathVariable int id) {
        return quizService.getQuizByID(id);
    }

    @PostMapping(value = "/quizzes", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Quiz addQuizzes(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal User authenticatedUser) {
        quiz.setCreatedBy(authenticatedUser);
        return quizService.addQuiz(quiz);
    }

    @PostMapping(value = "/quizzes/{id}/solve",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Answer solveQuiz(@PathVariable int id, @RequestBody Map<String, List<Integer>> answer, @AuthenticationPrincipal User authenticatedUser) {
        Quiz quiz = quizService.getQuizByID(id);

        if (quiz.isCorrectAnswer(answer.get("answer"))) {
            Completion completion = new Completion(authenticatedUser, quiz);
            completionService.addCompletion(completion);

            return new Answer(true, "Congratulations, you're right!");
        }
        return new Answer(false, "Wrong answer! Please, try again.");
    }

    @DeleteMapping(value = "/quizzes/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Quiz deleteQuiz(@PathVariable int id, @AuthenticationPrincipal User authenticatedUser) {
        Quiz quiz = quizService.getQuizByID(id);

        if (quiz.isCreatedBy(authenticatedUser.getEmail())) {
            quizService.removeQuiz(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "/quizzes/completed", produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Completion> getAllCompletions(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int pageSize,
                                              @RequestParam(defaultValue = "completedAt") String sortBy) {
        return completionService.getAllCompletions(page, pageSize, sortBy);
    }

}