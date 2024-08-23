package com.teamsix.firstteamproject.travelplan.repository;

import com.teamsix.firstteamproject.travelplan.entity.TravelPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 모든 객체가 적절하게 업로드 되려면 하위 객체부터 영속 상태여야 한다.
 */
@Repository
public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long>, TravelPlanRepositoryCustom {
    List<TravelPlan> findByUser_Id(Long userId);
}
