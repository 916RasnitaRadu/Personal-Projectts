package com.example.A2.service;

import com.example.A2.domain.Product;
import com.example.A2.domain.dto.ProductRequest;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ProductWithReviewDTO;
import com.example.A2.domain.dto.ReviewRequest;
import com.example.A2.domain.mappers.ProductMapper;
import com.example.A2.repository.ProductRepository;
import com.example.A2.repository.specification.ProductSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public Optional<ProductWithReviewDTO> get(Long id) {
        if (productRepository.existsById(id))
        {
            Optional<Product> product = productRepository.findById(id);
            if (product.isEmpty()) return null;

            ProductWithReviewDTO productDTO = new ProductWithReviewDTO();
            productDTO.setId(product.get().getId());
            productDTO.setName(product.get().getName());
            productDTO.setPrice(product.get().getPrice());
            productDTO.setColor(product.get().getColor());
            productDTO.setDescription(product.get().getDescription());
            productDTO.setCategory(product.get().getCategory());
            productDTO.setReviewList(
                    product.get().getReviewsList().stream().map(review -> new ReviewRequest(review.getId(),
                            review.getCustomer().getId(), review.getProduct().getId(), review.getReviewText(), review.getCreatedAt(), review.getNumberLikes())).collect(Collectors.toList()));
            return Optional.of(productDTO);
        }
        return Optional.empty();
    }

    public ProductResponse addService(ProductRequest productRequest) {
        Product product = productMapper.map(productRequest);
        productRepository.save(product);
        return productMapper.map(product);
    }


    public Page<ProductResponse> getAll(Integer pageNumber, Integer pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize)).map(ProductResponse::fromProduct);
    }

    public void deleteService(Long id)
    {
        if (this.productRepository.existsById(id))
        {
            Product product = productRepository.findProductById(id);
            productRepository.delete(product);
        }
    }

    public ProductResponse updateService(ProductRequest entity) {
        Product product = productMapper.map(entity);

        productRepository.save(product);
        return productMapper.map(product);
    }

    public Page<ProductResponse> findProductsPriceHigherThanService(Double price, Integer pageNumber, Integer pageSize)
    {
        return productRepository.findAll(ProductSpecification.priceHigherThan(price),PageRequest.of(pageNumber,pageSize)).map(ProductResponse::fromProduct);
//        List<Product> result = new ArrayList<>();
//        for (Product p : productRepository.findAll())
//        {
//            if (p.getPrice() > price) result.add(p);
//        }
//        return productMapper.map(result);
    }
}
