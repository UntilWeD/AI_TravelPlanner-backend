package com.teamsix.firstteamproject.community.repository;

import com.teamsix.firstteamproject.community.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {


}
