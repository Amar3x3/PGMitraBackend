package com.PGmitra.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PGmitra.app.Entity.Feedback;

public interface FeedbackRepo extends JpaRepository<Feedback, Long>{

    
} 
