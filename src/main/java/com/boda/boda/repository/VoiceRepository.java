package com.boda.boda.repository;

import com.boda.boda.entity.Voice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoiceRepository extends JpaRepository<Voice,Long> {

    public Optional<Voice> findById(long id);
    public Page<Voice> findAll(Pageable pageable);
    public Optional<Voice> findById(Long id);

}
