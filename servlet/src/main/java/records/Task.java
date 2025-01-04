package records;

import java.util.Collections;
import java.util.List;

public record Task(Long id, String name, String description, boolean completed, List<String> parameters) {

    public Task(Long id, String name, String description, boolean completed, List<String> parameters) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Task name cannot be null or empty");
        }

        this.id = id;
        this.name = name;
        this.description = description;
        this.completed = completed;
        this.parameters = parameters == null
                ? Collections.emptyList()
                : Collections.unmodifiableList(parameters);
    }

}
