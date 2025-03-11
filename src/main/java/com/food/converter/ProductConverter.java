package com.food.converter;
import com.food.DTO.ProductDTO;
import com.food.model.entities.ProductEntity;
import com.food.model.response.ProductResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductConverter {
    public List<ProductDTO> EntityToDTO(List<ProductEntity> productEntities) {
        List<ProductDTO> lists = new ArrayList<>();
        for(ProductEntity item: productEntities) {
            ProductDTO itemDTO = new ProductDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setDescription(item.getDescription());
            itemDTO.setImage(item.getImageUrl());
            itemDTO.setCreated_at(item.getCreatedAt());
            itemDTO.setUpdated_at(item.getUpdatedAt());
            lists.add(itemDTO);
        }
        return lists;
    }
    public List<ProductResponse> DTOtoResponseList(List<ProductDTO> productDTOs) {
        List<ProductResponse> lists = new ArrayList<>();
        for(ProductDTO item: productDTOs) {
            ProductResponse itemResponse = new ProductResponse();
            itemResponse.setId(item.getId());
            itemResponse.setName(item.getName());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setDescription(item.getDescription());
        }
        return lists;
    }
}
