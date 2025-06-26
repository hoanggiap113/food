package com.food.services.impl;

import com.food.commons.ProductType;
import com.food.customexceptions.DataNotFoundException;
import com.food.model.entities.Category;
import com.food.model.entities.Product;
import com.food.model.entities.ProductInventory;
import com.food.request.ProductPresentRequestDTO;
import com.food.request.ProductRequestDTO;
import com.food.repositories.ProductInventoryRepository;
import com.food.response.ProductPresentResponse;
import com.food.response.ProductResponseDTO;
import com.food.repositories.CategoriesRepository;
import com.food.repositories.ProductRepository;
import com.food.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoriesRepository categoriesRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final ModelMapper modelMapper;
    @Override
    public List<ProductResponseDTO> getAll() {
        List<Product> products = productRepository.findAll();
        List<ProductResponseDTO> listProduct = new ArrayList<>();
        for (Product item : products) {
            ProductResponseDTO productResponseDTO = new ProductResponseDTO();
            ProductInventory productInventory = productInventoryRepository.findCurrentByProductId(item.getId());
            if (productInventory != null) {
                productResponseDTO.setId(item.getId());
                productResponseDTO.setName(item.getName());
                productResponseDTO.setPrice(productInventory.getPrice());
                productResponseDTO.setQuantity(productInventory.getQuantity());
                productResponseDTO.setStatus(productInventory.getStatus() != null
                        ? productInventory.getStatus().toString()
                        : "UNKNOWN");
            } else {
                continue;
            }

            if (item.getCategory() != null) {
                productResponseDTO.setCategory_name(item.getCategory().getName());
            } else {
                productResponseDTO.setCategory_name("Không rõ");
            }
            productResponseDTO.setType(item.getType());
            productResponseDTO.setDescription(item.getDescription());
            productResponseDTO.setImage_url(item.getImageUrl());



            listProduct.add(productResponseDTO);
        }
        return listProduct;
    }

    @Override
    public ProductRequestDTO saveProduct(ProductRequestDTO body) throws Exception {
        Category category = categoriesRepository.findById(body.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Category not found"));
        List<ProductType> typeEnums = body.getType().stream()
                .map(String::toUpperCase)
                .map(ProductType::valueOf)
                .collect(Collectors.toList());

        Product product = new Product();
        product.setName(body.getName());
        product.setDescription(body.getDescription());
        product.setCategory(category);
        product.setImageUrl(body.getImage_url());
        product.setType(body.getType().stream().collect(Collectors.joining(",")));
        product.setCreatedAt(LocalDateTime.now());
        productRepository.save(product);

        ProductInventory productInventory = new ProductInventory();
        productInventory.setProduct(product);
        productInventory.setPrice(body.getPrice());
        productInventory.setQuantity(body.getQuantity());
        productInventory.setStatus(body.getStatus().toString());
        productInventory.setIsCurrent(true);
        productInventory.setStartDate(LocalDateTime.now());
        productInventoryRepository.save(productInventory);

        return body;
    }

    @Override
    public void updateProduct(ProductRequestDTO body, Long id) throws Exception {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        if(existingProduct != null) {
            Category existingCategory = categoriesRepository.findById(body.getCategoryId()).orElseThrow(() ->
                    new DataNotFoundException("Category not found with id=" + body.getCategoryId()));
            existingProduct.setName(body.getName());
            existingProduct.setDescription(body.getDescription());
            existingProduct.setImageUrl(body.getImage_url());
            existingProduct.setType(body.getType().stream().collect(Collectors.joining(",")));
            existingProduct.setCategory(existingCategory);
            existingProduct.setUpdatedAt(LocalDateTime.now());
        productRepository.save(existingProduct);
        }

        ProductInventory existingProductInventory = productInventoryRepository.findCurrentByProductId(id);
        existingProductInventory.setIsCurrent(false);
        productInventoryRepository.save(existingProductInventory);

        ProductInventory productInventory = new ProductInventory();
        productInventory.setProduct(existingProduct);
        productInventory.setPrice(body.getPrice());
        productInventory.setQuantity(body.getQuantity());
        productInventory.setStatus(body.getStatus().toString());
        productInventory.setIsCurrent(true);
        productInventory.setStartDate(LocalDateTime.now());
        productInventoryRepository.save(productInventory);

    }

    //Xóa mềm(bằng cách set bảng giá đang hoạt động = false
    @Override
    public void deleteProduct(List<Long> ids) throws Exception {
        for (Long id : ids) {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm với ID: " + id));
            ProductInventory inventory = productInventoryRepository.findCurrentByProductId(id);
            inventory.setIsCurrent(false);
            productInventoryRepository.save(inventory);
        }
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        ProductInventory productInventory = productInventoryRepository.findCurrentByProductId(id);
        ProductResponseDTO productResponseDTO = new ProductResponseDTO().from(product, productInventory);
        return productResponseDTO;
    }

}
