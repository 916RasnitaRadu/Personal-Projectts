package com.example.A2.api;

import com.example.A2.domain.Product;
import com.example.A2.domain.dto.ProductRequest;
import com.example.A2.domain.dto.ProductResponse;
import com.example.A2.domain.dto.ProductWithReviewDTO;
import com.example.A2.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/product")
@RestController
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithReviewDTO> findProductById(@PathVariable Long id)
    {
        Optional<ProductWithReviewDTO> productDTO = service.get(id);
        if (productDTO.isEmpty())
        {
            return ResponseEntity.notFound().build();

        }

        return ResponseEntity.ok(productDTO.get());
    }

    @GetMapping(path = "/all")
    public @ResponseBody Page<ProductResponse> getAllProducts(@RequestParam Integer pageNumber, @RequestParam
                                                              @Min(value=4, message = "Page size should be at least 4")
                                                              @Max(value=10, message = "Page size should be at most 10" )
                                                              Integer pageSize)
    {
        return service.getAll(pageNumber, pageSize);
    }



    @PostMapping("/")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody @Valid ProductRequest product)
    {
        ProductResponse addedProduct = service.addService(product);
        return new ResponseEntity<ProductResponse>(addedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteProduct(@PathVariable Long id)
    {
        service.deleteService(id);
    }

    @PutMapping(path = "/")
    public ProductResponse update(@RequestBody @Valid ProductRequest newProduct) {
        return service.updateService(newProduct);
    }

    @GetMapping(path = "/filter/{price}")
    public Page<ProductResponse> findProductsPriceHigherThan(@PathVariable double price, @RequestParam Integer pageNumber, @RequestParam
    @Min(value=4, message = "Page size should be at least 4")
    @Max(value=10, message = "Page size should be at most 10" )
    Integer pageSize)
    {
        return service.findProductsPriceHigherThanService(price,pageNumber,pageSize);
    }
}
