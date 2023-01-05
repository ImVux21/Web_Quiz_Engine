package engine.repository;

import engine.entity.Completion;
import engine.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CompletionRepository extends JpaRepository<Completion, Long>, PagingAndSortingRepository<Completion, Long> {
    Page<Completion> findCompletionsByCompletedBy(User completedBy, Pageable pageable);
}