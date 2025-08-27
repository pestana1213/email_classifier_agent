package com.langchain.agent.Services;

import com.langchain.agent.Entities.Classifications;
import com.langchain.agent.Repos.ClassificationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClassificationService {

    private final ClassificationsRepo classificationsRepo;

    public ClassificationService(ClassificationsRepo classificationsRepo) {
        this.classificationsRepo = classificationsRepo;
    }

    public void save(Classifications classification) {
        this.classificationsRepo.save(classification);
    }

    public boolean isIdInDB(String messageId) {
        return this.classificationsRepo.findByMessageId(messageId).isPresent();
    }
}
