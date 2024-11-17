package br.com.allen.dynamodbcrud.controller;

import br.com.allen.dynamodbcrud.model.entity.Product;
import br.com.allen.dynamodbcrud.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/{productId}")
  public ResponseEntity<Product> getProductById(@PathVariable String productId) {
    Product product = productService.getProductById(productId);
    return product == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(product);
  }
}
