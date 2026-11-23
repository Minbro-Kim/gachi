package com.gachi.binarycontent.repository;

import com.gachi.binarycontent.entity.BinaryContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BinaryContentRepository extends JpaRepository<BinaryContent, UUID> {
    /// storageKey 존재 확인
    boolean existsByStorageKey(String storageKey);

    /// storageKey로 BinaryContent 조회
    Optional<BinaryContent> findByStorageKey(String storageKey);
}
