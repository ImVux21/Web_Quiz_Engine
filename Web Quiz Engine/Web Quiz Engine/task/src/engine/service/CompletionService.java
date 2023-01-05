package engine.service;

import engine.entity.Completion;
import engine.entity.User;
import engine.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CompletionService {
    CompletionRepository completionRepository;

    @Autowired
    public CompletionService(CompletionRepository completionRepository) {
        this.completionRepository = completionRepository;
    }

    public void addCompletion(Completion completion) {
        completionRepository.save(completion);
    }

    public Page<Completion> getAllCompletions(int pageNo, int pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return completionRepository.findCompletionsByCompletedBy(user, paging);
    }
}