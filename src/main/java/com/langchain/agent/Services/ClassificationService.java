package com.langchain.agent.Services;

import com.langchain.agent.Entities.Classifications;
import com.langchain.agent.Repos.ClassificationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationService {

    @Autowired
    private ClassificationsRepo classificationsRepo;

    public void save(Classifications classification) {
        this.classificationsRepo.save(classification);
    }
}
