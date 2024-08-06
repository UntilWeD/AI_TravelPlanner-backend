package com.teamsix.firstteamproject.travelplan.repository;

import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
}
