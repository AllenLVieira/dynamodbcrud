package br.com.allen.dynamodbcrud.service;

import br.com.allen.dynamodbcrud.model.entity.Product;

public interface ProductService {
  Product getProductById(String productId);
}
