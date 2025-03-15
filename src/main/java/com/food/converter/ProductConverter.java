package com.food.converter;
import com.food.DTO.ProductDTO;
import com.food.model.builders.ProductBuilder;
import com.food.model.entities.ProductEntity;
import com.food.model.response.ProductResponse;
import com.food.utils.UtilsMap;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ProductConverter {
    public List<ProductDTO> EntityToDTO(List<ProductEntity> productEntities) {
        List<ProductDTO> lists = new ArrayList<>();
        for (ProductEntity item : productEntities) {
            ProductDTO itemDTO = new ProductDTO();
            itemDTO.setId(item.getId());
            itemDTO.setName(item.getName());
            itemDTO.setPrice(item.getPrice());
            itemDTO.setDescription(item.getDescription());
            itemDTO.setImage(item.getImageUrl());
            lists.add(itemDTO);
        }
        return lists;
    }

    public List<ProductResponse> DTOtoResponseList(List<ProductDTO> productDTOs) {
        List<ProductResponse> lists = new ArrayList<>();
        for (ProductDTO item : productDTOs) {
            ProductResponse itemResponse = new ProductResponse();
            itemResponse.setId(item.getId());
            itemResponse.setName(item.getName());
            itemResponse.setPrice(item.getPrice());
            itemResponse.setDescription(item.getDescription());
            lists.add(itemResponse);
        }
        return lists;
    }

    public ProductResponse EntitytoResponse(ProductEntity productEntity) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(productEntity.getId());
        productResponse.setName(productEntity.getName());
        productResponse.setPrice(productEntity.getPrice());
        productResponse.setDescription(productEntity.getDescription());
        String category_name = productEntity.getCategoryEntity().getName();
        productResponse.setCategory_name(category_name);
        return productResponse;
    }
    public ProductBuilder toProductBuilder(Map<String, Object> params) {
        ProductBuilder productBuilder = new ProductBuilder.Builder()
                .setName(UtilsMap.getObject(params, "name", String.class))
                .setDescription(UtilsMap.getObject(params, "description", String.class))
                .setPrice(UtilsMap.getObject(params, "price", Double.class))
                .setImageUrl(UtilsMap.getObject(params, "imageUrl", String.class))
                .setCreatedAt(null)
                .setUpdatedAt(null)
                .setCategory_id(UtilsMap.getObject(params,"category_id",Long.class))
                .build();
            return productBuilder;

    }
}
