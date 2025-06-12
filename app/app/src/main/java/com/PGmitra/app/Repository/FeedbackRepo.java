package com.PGmitra.app.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PGmitra.app.Entity.Feedback;
import com.PGmitra.app.Entity.Owner;

import java.util.List;


public interface FeedbackRepo extends JpaRepository<Feedback, Long>{
    List<Feedback> findByOwner(Owner owner);
    
} 
