package com.langchain.agent.Repos;

import com.langchain.agent.Entities.Classifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClassificationsRepo extends JpaRepository<Classifications, UUID> {
}
