package br.com.allen.dynamodbcrud.service.impl;

import br.com.allen.dynamodbcrud.model.entity.Product;
import br.com.allen.dynamodbcrud.service.ProductService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@Service
public class ProductServiceImpl implements ProductService {
  private final DynamoDbTemplate dynamoDbTemplate;

  public ProductServiceImpl(DynamoDbTemplate dynamoDbTemplate) {
    this.dynamoDbTemplate = dynamoDbTemplate;
  }

  @Override
  public Product getProductById(String productId) {
    return dynamoDbTemplate.load(Key.builder().partitionValue(productId).build(), Product.class);
  }
}
