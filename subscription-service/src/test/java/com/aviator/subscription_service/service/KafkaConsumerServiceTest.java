package com.aviator.subscription_service.service;

import com.aviator.subscription_service.dto.CategoryEvent;
import com.aviator.subscription_service.model.CategoryMirror;
import com.aviator.subscription_service.repository.CategoryMirrorRepository;
import com.aviator.subscription_service.service.impl.KafkaConsumerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceTest {
    @Mock
    CategoryMirrorService categoryMirrorService;

    @Mock
    CategoryMirrorRepository categoryMirrorRepository;

    @InjectMocks
    KafkaConsumerService kafkaConsumerService;

    @Test
    @DisplayName("should listen and upsert category mirror")
    void shouldSuccessfullyUpsertCategoryPayload(){
        CategoryEvent categoryEvent = CategoryEvent.builder()
                .id(UUID.randomUUID().toString())
                .title("Hypersonic")
                .slug("hypersonic")
                .eventType("UPSERT")
                .build();
        kafkaConsumerService.listen(categoryEvent);


        verify(categoryMirrorRepository, times(1)).save(any(CategoryMirror.class));
    }

    @Test
    @DisplayName("should listen and Delete category mirror")
    void shouldSuccessfullyDeleteCategoryPayload(){
        CategoryEvent categoryEvent = CategoryEvent.builder()
                .id(UUID.randomUUID().toString())
                .title("Hypersonic")
                .slug("hypersonic")
                .eventType("DELETE")
                .build();
        kafkaConsumerService.listen(categoryEvent);

        verify(categoryMirrorRepository, times(1)).deleteById(any(UUID.class));
    }
}
