package com.aviator.subscription_service.controller;

import com.aviator.subscription_service.dto.CategoryIdResponseDTO;
import com.aviator.subscription_service.dto.CategoryMirrorDto;
import com.aviator.subscription_service.dto.SubscriptionCountResponseDTO;
import com.aviator.subscription_service.service.CategoryMirrorService;
import com.aviator.subscription_service.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final CategoryMirrorService categoryMirrorService;

    //get subscribed categories
    /**
     * Returns List of Subscribed Categories (Allowed only for Logged In User).
     * 
     * @param userId: String @PathVariable
     * @return List<CategoryIdResponseDTO>
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<CategoryIdResponseDTO>> getAllSubscribedCategories(@PathVariable String userId){
        List<CategoryIdResponseDTO> categoryIdResponseDTOS = subscriptionService.getAllSubsribedCategoriesForUser(userId);
        return ResponseEntity.ok().body(categoryIdResponseDTOS);
    }

    //get subscribed user of a category
    //exposed to Admin only
    @GetMapping("/{categoryId}")
    public ResponseEntity<SubscriptionCountResponseDTO> getAllSubscribedUserCountPerCategory(@PathVariable String categoryId){
        SubscriptionCountResponseDTO subscriptionDTO  = subscriptionService.getSubscriptionCountPerCategory(categoryId);
        return ResponseEntity.ok().body(subscriptionDTO);
    }

    //post subscription request for Audience user for a category
    @PostMapping("/{categoryId}")
    public ResponseEntity<String> subscribeCategory(@PathVariable String categoryId){
        String response = subscriptionService.subscribeCategory(categoryId);
        return ResponseEntity.ok().body(response);
    }


    //post unSubscription request for Audience user only if already subscribed
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> unSubscribeCategory(@PathVariable String categoryId){
        String response = subscriptionService.unSubscribeCategory(categoryId);
        return ResponseEntity.ok().body(response);
    }

    //ONLY FOR ADMIN
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryMirrorDto>> getAllCategoryMirrors(){
        List<CategoryMirrorDto> categoryMirrorDtos = categoryMirrorService.getAllCategories();
        return ResponseEntity.ok().body(categoryMirrorDtos);
    }
}
