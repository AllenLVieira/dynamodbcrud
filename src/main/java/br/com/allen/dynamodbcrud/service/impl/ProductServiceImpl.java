package br.com.allen.dynamodbcrud.service.impl;

import br.com.allen.dynamodbcrud.model.dto.ProductRequest;
import br.com.allen.dynamodbcrud.model.dto.ProductResponse;
import br.com.allen.dynamodbcrud.model.entity.Product;
import br.com.allen.dynamodbcrud.service.ProductService;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@Service
public class ProductServiceImpl implements ProductService {
  private final DynamoDbTemplate dynamoDbTemplate;

  public ProductServiceImpl(DynamoDbTemplate dynamoDbTemplate) {
    this.dynamoDbTemplate = dynamoDbTemplate;
  }

  @Override
  public ProductResponse getProductById(String productId) {
    Product product =
        dynamoDbTemplate.load(Key.builder().partitionValue(productId).build(), Product.class);

    if (product == null) {
      return null;
    }

    return new ProductResponse(product.getId(), product.getName(), product.getPrice());
  }

  @Override
  public void addProduct(ProductRequest dto) {
    Product product = Product.fromDTO(dto);
    dynamoDbTemplate.save(product);
  }

  @Override
  public void deleteProductById(String productId) {
    Key key = Key.builder().partitionValue(productId).build();

    dynamoDbTemplate.delete(key, Product.class);
  }

  @Override
  public void updateProductById(String productId, ProductRequest productRequest) {}

  @Override
  public List<Product> getAllProducts() {
    PageIterable<Product> productPageIterable = dynamoDbTemplate.scanAll(Product.class);
    List<Product> allProducts = new ArrayList<>();

    for (Page<Product> page : productPageIterable) {
      allProducts.addAll(page.items());
    }

    return allProducts;
  }
}
