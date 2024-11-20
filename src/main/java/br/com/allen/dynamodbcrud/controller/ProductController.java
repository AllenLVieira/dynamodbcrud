package br.com.allen.dynamodbcrud.controller;

import br.com.allen.dynamodbcrud.model.dto.ProductRequest;
import br.com.allen.dynamodbcrud.model.dto.ProductResponse;
import br.com.allen.dynamodbcrud.model.entity.Product;
import br.com.allen.dynamodbcrud.service.ProductService;
import java.util.List;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponse> getProductById(@PathVariable String productId) {
    ProductResponse response = productService.getProductById(productId);
    return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  @PostMapping
  public void addProduct(@RequestBody ProductRequest request, HttpServletResponse response) {
    productService.addProduct(request);
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteById(@PathVariable String productId) {
    productService.deleteProductById(productId);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductResponse> updateProduct(
      @PathVariable String productId, @RequestBody ProductRequest request) {
    ProductResponse response = productService.updateProductById(productId, request);
    return response == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(response);
  }
}
