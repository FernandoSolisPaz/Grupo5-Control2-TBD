package com.tbd.fifth.group.taskmanager.services;

import com.tbd.fifth.group.taskmanager.repositories.VerificationsRepository;
import org.springframework.stereotype.Repository;

@Repository
public class VerificationsService implements VerificationsRepository {
    @Override
    public Boolean validateInput(String input) {
        return !input.contains(";") && !input.contains("--") &&
                !input.contains("`") && !input.contains("'") &&
                !input.contains("\"") && !input.contains("\\");
    }
}
