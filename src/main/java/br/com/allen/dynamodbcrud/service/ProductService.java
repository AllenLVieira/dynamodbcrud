package br.com.allen.dynamodbcrud.service;

import br.com.allen.dynamodbcrud.model.dto.ProductRequest;
import br.com.allen.dynamodbcrud.model.dto.ProductResponse;
import br.com.allen.dynamodbcrud.model.entity.Product;
import java.util.List;

public interface ProductService {
  ProductResponse getProductById(String productId);

  void addProduct(ProductRequest dto);

  void deleteProductById(String productId);

  ProductResponse updateProductById(String productId, ProductRequest productRequest);

  List<Product> getAllProducts();
}
