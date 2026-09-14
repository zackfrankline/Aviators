package com.aviator.subscription_service.service;

import com.aviator.subscription_service.dto.CategoryEvent;
import com.aviator.subscription_service.dto.CategoryMirrorDto;
import com.aviator.subscription_service.mapper.CategoryMirrorMapper;
import com.aviator.subscription_service.model.CategoryMirror;
import com.aviator.subscription_service.repository.CategoryMirrorRepository;
import com.aviator.subscription_service.service.impl.CategoryMirrorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) // Enables Mockito
class CategoryMirrorServiceTest {

    @Mock
    private CategoryMirrorRepository categoryMirrorRepository; //fakes a dependency here in case for database


    @InjectMocks
    private CategoryMirrorServiceImpl categoryMirrorService; // Inject Mock dependency // here we have CategoryMirrorService Implementation

    @Test
    @DisplayName("Should Upsert New Category")
    void shouldSuccessfullyUpsertNewCategory(){

        // set the dummy data
        CategoryEvent incomingEvent = CategoryEvent.builder()
                .id("22412350-0000-0000-0000-000000000005")
                .title("hypersonic")
                .eventType("UPSERT")
                .build();

        //acts as actual method
        categoryMirrorService.upsertCategory(incomingEvent);

        //asserts the that the Categoryupserted was done once for entity type as CategoryMirror
        verify(categoryMirrorRepository, times(1)).save(any(CategoryMirror.class));
    }

    @Test
    @DisplayName("Should Delete Category")
    void shouldSuccessfullyDeleteCategory(){
        CategoryEvent categoryEvent = CategoryEvent.builder()
                .id("22412350-0000-0000-0000-000000000002")
                .title("SuperSonic")
                .slug("SuperSonic")
                .build();

        categoryMirrorService.deleteCategory(categoryEvent);

        verify(categoryMirrorRepository, times(1)).deleteById(any(UUID.class));

    }

    @Test
    @DisplayName("Should get All Categories")
    void shouldSuccessfullyGetAllCategories(){
        List<CategoryMirror> mockCategories = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            mockCategories.add(CategoryMirror.builder()
                    .id(UUID.fromString("22412350-0000-0000-0000-00000000000"+ i))
                    .name("test"+ i)
                    .slug("test"+i)
                    .createdAt(LocalDateTime.now())
                    .build()
            );
        }
        when(categoryMirrorRepository.findAll()).thenReturn(mockCategories);

        List<CategoryMirrorDto> getAllCategories = categoryMirrorService.getAllCategories();

        //asert that 10 records are returned
        assertEquals(10, getAllCategories.size(), "10 Categories list should be returned");

        for(int i = 0; i < 10; i++){
            CategoryMirror resultCategory = CategoryMirrorMapper.toModel(getAllCategories.get(i));
            CategoryMirror mockCategory = mockCategories.get(i);
            assertEquals(resultCategory.getId(), mockCategory.getId());
            assertEquals(resultCategory.getName(), mockCategory.getName());
            assertEquals(resultCategory.getSlug(), mockCategory.getSlug());
        }
    }

}
