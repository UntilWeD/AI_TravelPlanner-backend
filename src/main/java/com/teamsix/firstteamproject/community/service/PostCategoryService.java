package com.teamsix.firstteamproject.community.service;

import com.teamsix.firstteamproject.community.dto.PostCategoryDTO;
import com.teamsix.firstteamproject.community.entity.PostCategory;
import com.teamsix.firstteamproject.community.repository.PostCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    public PostCategoryService(PostCategoryRepository postCategoryRepository) {
        this.postCategoryRepository = postCategoryRepository;
    }

    public PostCategoryDTO savePostCategory(PostCategoryDTO dto) {
        PostCategory postCategory = dto.toEntity();

        return postCategoryRepository.save(postCategory).toDTO();
    }

    public PostCategoryDTO getPostCategory(Long categoryId) {
        return postCategoryRepository.findById(categoryId).get().toDTO();
    }

    public List<PostCategoryDTO> getAllPostCategory() {
        return postCategoryRepository.findAll().stream()
                .map(PostCategory::toDTO).collect(Collectors.toList());
    }


    public PostCategoryDTO updatePostCategory(Long categoryId, PostCategoryDTO dto) {
        PostCategory postCategory = postCategoryRepository.findById(categoryId).get();
        postCategory.updatePostCategory(dto);
        return postCategory.toDTO();
    }

    public String deletePostCategory(Long categoryId) {

        // 카테고리 삭제시 옮김 처리


        return null;
    }
}
