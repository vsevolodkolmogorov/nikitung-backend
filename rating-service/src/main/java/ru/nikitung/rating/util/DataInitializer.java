package ru.nikitung.rating.util;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.nikitung.rating.model.RatingCategory;
import ru.nikitung.rating.repository.RatingCategoryRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RatingCategoryRepository repository;

    @Override
    public void run(ApplicationArguments args) {
        createRoleIfNotExists(new RatingCategory("purity", "Чистота", 0));
        createRoleIfNotExists(new RatingCategory("logistics", "Логистика", 1));
        createRoleIfNotExists(new RatingCategory("vibe", "Атмосфера", 2));
        createRoleIfNotExists(new RatingCategory("temperature", "Температура", 3));
        createRoleIfNotExists(new RatingCategory("impression", "Впечатление", 4));
    }

    private void createRoleIfNotExists(RatingCategory ratingCategory) {
        repository.findByCode(ratingCategory.getCode()).orElseGet(() -> {
            RatingCategory category = new RatingCategory();
            category.setCode(ratingCategory.getCode());
            category.setDisplayName(ratingCategory.getDisplayName());
            category.setDisplayOrder(ratingCategory.getDisplayOrder());
            return repository.save(category);
        });
    }
}
