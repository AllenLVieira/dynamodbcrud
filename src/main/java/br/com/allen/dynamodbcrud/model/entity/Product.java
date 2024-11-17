package br.com.allen.dynamodbcrud.model.entity;

import br.com.allen.dynamodbcrud.model.dto.ProductRequest;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class Product {
  private String id;
  private String name;
  private BigDecimal price;

  public static Product fromDTO(ProductRequest dto) {
    Product product = new Product();
    product.setId(UUID.randomUUID().toString());
    product.setName(dto.getName());
    product.setPrice(dto.getPrice());
    return product;
  }

  @DynamoDbPartitionKey
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
