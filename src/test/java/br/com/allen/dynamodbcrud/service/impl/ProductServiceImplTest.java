package br.com.allen.dynamodbcrud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.allen.dynamodbcrud.model.dto.ProductRequest;
import br.com.allen.dynamodbcrud.model.dto.ProductResponse;
import br.com.allen.dynamodbcrud.model.entity.Product;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

@ContextConfiguration(classes = {ProductServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductServiceImplTest {
  @MockBean
  private DynamoDbTemplate dynamoDbTemplate;

  @Autowired
  private ProductServiceImpl productServiceImpl;

  /**
   * Test {@link ProductServiceImpl#getProductById(String)}.
   *
   * <ul>
   *   <li>Given {@link DynamoDbTemplate} {@link DynamoDbTemplate#load(Key, Class)}
   * return {@code
   *       null}.
   *   <li>When {@code 42}.
   *   <li>Then return {@code null}.
   * </ul>
   *
   * <p>
   * Method under test: {@link ProductServiceImpl#getProductById(String)}
   */
  @Test
  @DisplayName("Test getProductById(String); given DynamoDbTemplate load(Key, Class) return 'null'; when '42'; then return 'null'")
  void testGetProductById_givenDynamoDbTemplateLoadReturnNull_when42_thenReturnNull() {
    // Arrange
    when(dynamoDbTemplate.load(Mockito.<Key>any(), Mockito.<Class<Product>>any())).thenReturn(null);

    // Act
    ProductResponse actualProductById = productServiceImpl.getProductById("42");

    // Assert
    verify(dynamoDbTemplate).load(isA(Key.class), isA(Class.class));
    assertNull(actualProductById);
  }

  /**
   * Test {@link ProductServiceImpl#getProductById(String)}.
   *
   * <ul>
   *   <li>Given {@link DynamoDbTemplate} {@link DynamoDbTemplate#load(Key, Class)}
   * return {@link Product#Product()}.
   *   <li>Then return Id is {@code null}.
   * </ul>
   *
   * <p>
   * Method under test: {@link ProductServiceImpl#getProductById(String)}
   */
  @Test
  @DisplayName("Test getProductById(String); given DynamoDbTemplate load(Key, Class) return Product(); then return Id is 'null'")
  void testGetProductById_givenDynamoDbTemplateLoadReturnProduct_thenReturnIdIsNull() {
    // Arrange
    when(dynamoDbTemplate.load(Mockito.<Key>any(), Mockito.<Class<Product>>any())).thenReturn(new Product());

    // Act
    ProductResponse actualProductById = productServiceImpl.getProductById("42");

    // Assert
    verify(dynamoDbTemplate).load(isA(Key.class), isA(Class.class));
    assertNull(actualProductById.getId());
    assertNull(actualProductById.getName());
    assertNull(actualProductById.getPrice());
  }

  /**
   * Test {@link ProductServiceImpl#getProductById(String)}.
   *
   * <ul>
   *   <li>Given {@link Product} {@link Product#getId()} return {@code 42}.
   *   <li>When {@code 42}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>
   * Method under test: {@link ProductServiceImpl#getProductById(String)}
   */
  @Test
  @DisplayName("Test getProductById(String); given Product getId() return '42'; when '42'; then return Id is '42'")
  void testGetProductById_givenProductGetIdReturn42_when42_thenReturnIdIs42() {
    // Arrange
    Product product = mock(Product.class);
    when(product.getId()).thenReturn("42");
    when(product.getName()).thenReturn("Name");
    when(product.getPrice()).thenReturn(new BigDecimal("2.3"));
    when(dynamoDbTemplate.load(Mockito.<Key>any(), Mockito.<Class<Product>>any())).thenReturn(product);

    // Act
    ProductResponse actualProductById = productServiceImpl.getProductById("42");

    // Assert
    verify(product).getId();
    verify(product).getName();
    verify(product).getPrice();
    verify(dynamoDbTemplate).load(isA(Key.class), isA(Class.class));
    assertEquals("42", actualProductById.getId());
    assertEquals("Name", actualProductById.getName());
    BigDecimal expectedPrice = new BigDecimal("2.3");
    assertEquals(expectedPrice, actualProductById.getPrice());
  }

  /**
   * Test {@link ProductServiceImpl#getProductById(String)}.
   *
   * <ul>
   *   <li>Given {@link Product} {@link Product#getId()} return {@code 42}.
   *   <li>When {@code null}.
   *   <li>Then return Id is {@code 42}.
   * </ul>
   *
   * <p>
   * Method under test: {@link ProductServiceImpl#getProductById(String)}
   */
  @Test
  @DisplayName("Test getProductById(String); given Product getId() return '42'; when 'null'; then return Id is '42'")
  void testGetProductById_givenProductGetIdReturn42_whenNull_thenReturnIdIs42() {
    // Arrange
    Product product = mock(Product.class);
    when(product.getId()).thenReturn("42");
    when(product.getName()).thenReturn("Name");
    when(product.getPrice()).thenReturn(new BigDecimal("2.3"));
    when(dynamoDbTemplate.load(Mockito.<Key>any(), Mockito.<Class<Product>>any())).thenReturn(product);

    // Act
    ProductResponse actualProductById = productServiceImpl.getProductById(null);

    // Assert
    verify(product).getId();
    verify(product).getName();
    verify(product).getPrice();
    verify(dynamoDbTemplate).load(isA(Key.class), isA(Class.class));
    assertEquals("42", actualProductById.getId());
    assertEquals("Name", actualProductById.getName());
    BigDecimal expectedPrice = new BigDecimal("2.3");
    assertEquals(expectedPrice, actualProductById.getPrice());
  }

  /**
   * Test {@link ProductServiceImpl#addProduct(ProductRequest)}.
   *
   * <ul>
   *   <li>When {@link ProductRequest#ProductRequest()}.
   *   <li>Then calls {@link DynamoDbTemplate#save(Object)}.
   * </ul>
   *
   * <p>
   * Method under test: {@link ProductServiceImpl#addProduct(ProductRequest)}
   */
  @Test
  @DisplayName("Test addProduct(ProductRequest); when ProductRequest(); then calls save(Object)")
  void testAddProduct_whenProductRequest_thenCallsSave() {
    // Arrange
    when(dynamoDbTemplate.save(Mockito.<Product>any())).thenReturn(new Product());

    // Act
    productServiceImpl.addProduct(new ProductRequest());

    // Assert
    verify(dynamoDbTemplate).save(isA(Product.class));
  }

  /**
   * Test {@link ProductServiceImpl#deleteProductById(String)}.
   *
   * <p>
   * Method under test: {@link ProductServiceImpl#deleteProductById(String)}
   */
  @Test
  @DisplayName("Test deleteProductById(String)")
  void testDeleteProductById() {
    // Arrange
    doNothing().when(dynamoDbTemplate).delete(Mockito.<Key>any(), Mockito.<Class<Object>>any());

    // Act
    productServiceImpl.deleteProductById("42");

    // Assert
    verify(dynamoDbTemplate).delete(isA(Key.class), isA(Class.class));
  }

  /**
   * Test {@link ProductServiceImpl#updateProductById(String, ProductRequest)}.
   * <ul>
   *   <li>Given {@link DynamoDbTemplate} {@link DynamoDbTemplate#load(Key, Class)}
   * return {@code null}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProductServiceImpl#updateProductById(String, ProductRequest)}
   */
  @Test
  @DisplayName("Test updateProductById(String, ProductRequest); given DynamoDbTemplate load(Key, Class) return 'null'; when '42'; then return 'null'")
  void testUpdateProductById_givenDynamoDbTemplateLoadReturnNull_when42_thenReturnNull() {
    // Arrange
    when(dynamoDbTemplate.save(Mockito.<Product>any())).thenReturn(new Product());

    // Act
    ProductResponse actualUpdateProductByIdResult = productServiceImpl.updateProductById("42", new ProductRequest());

    // Assert
    verify(dynamoDbTemplate).load(isA(Key.class), isA(Class.class));
    assertNull(actualUpdateProductByIdResult);
  }

  /**
   * Test {@link ProductServiceImpl#updateProductById(String, ProductRequest)}.
   * <ul>
   *   <li>Given {@link DynamoDbTemplate} {@link DynamoDbTemplate#load(Key, Class)}
   * return {@link Product#Product()}.</li>
   *   <li>Then return Id is {@code null}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProductServiceImpl#updateProductById(String, ProductRequest)}
   */
  @Test
  @DisplayName("Test updateProductById(String, ProductRequest); given DynamoDbTemplate load(Key, Class) return Product(); then return Id is 'null'")
  void testUpdateProductById_givenDynamoDbTemplateLoadReturnProduct_thenReturnIdIsNull() {
    // Arrange
    when(dynamoDbTemplate.save(Mockito.<Product>any())).thenReturn(new Product());
    when(dynamoDbTemplate.load(Mockito.<Key>any(), Mockito.<Class<Product>>any())).thenReturn(new Product());

    // Act
    ProductResponse actualUpdateProductByIdResult = productServiceImpl.updateProductById("42", new ProductRequest());

    // Assert
    verify(dynamoDbTemplate, atLeast(1)).load(isA(Key.class), isA(Class.class));
    verify(dynamoDbTemplate).save(isA(Product.class));
    assertNull(actualUpdateProductByIdResult.getId());
    assertNull(actualUpdateProductByIdResult.getName());
    assertNull(actualUpdateProductByIdResult.getPrice());
  }

  /**
   * Test {@link ProductServiceImpl#updateProductById(String, ProductRequest)}.
   * <ul>
   *   <li>Given {@link Product} {@link Product#getId()} return {@code 42}.</li>
   *   <li>When {@code 42}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProductServiceImpl#updateProductById(String, ProductRequest)}
   */
  @Test
  @DisplayName("Test updateProductById(String, ProductRequest); given Product getId() return '42'; when '42'; then return Id is '42'")
  void testUpdateProductById_givenProductGetIdReturn42_when42_thenReturnIdIs42() {
    // Arrange
    Product product = mock(Product.class);
    when(product.getId()).thenReturn("42");
    when(product.getName()).thenReturn("Name");
    when(product.getPrice()).thenReturn(new BigDecimal("2.3"));
    when(dynamoDbTemplate.save(Mockito.<Product>any())).thenReturn(new Product());
    when(dynamoDbTemplate.load(Mockito.<Key>any(), Mockito.<Class<Product>>any())).thenReturn(product);

    // Act
    ProductResponse actualUpdateProductByIdResult = productServiceImpl.updateProductById("42", new ProductRequest());

    // Assert
    verify(product, atLeast(1)).getId();
    verify(product, atLeast(1)).getName();
    verify(product).getPrice();
    verify(dynamoDbTemplate, atLeast(1)).load(isA(Key.class), isA(Class.class));
    verify(dynamoDbTemplate, atLeast(1)).save(isA(Product.class));
    assertEquals("42", actualUpdateProductByIdResult.getId());
    assertEquals("Name", actualUpdateProductByIdResult.getName());
    BigDecimal expectedPrice = new BigDecimal("2.3");
    assertEquals(expectedPrice, actualUpdateProductByIdResult.getPrice());
  }

  /**
   * Test {@link ProductServiceImpl#updateProductById(String, ProductRequest)}.
   * <ul>
   *   <li>Given {@link Product} {@link Product#getId()} return {@code 42}.</li>
   *   <li>When {@code null}.</li>
   *   <li>Then return Id is {@code 42}.</li>
   * </ul>
   * <p>
   * Method under test:
   * {@link ProductServiceImpl#updateProductById(String, ProductRequest)}
   */
  @Test
  @DisplayName("Test updateProductById(String, ProductRequest); given Product getId() return '42'; when 'null'; then return Id is '42'")
  void testUpdateProductById_givenProductGetIdReturn42_whenNull_thenReturnIdIs42() {
    // Arrange
    Product product = mock(Product.class);
    when(product.getId()).thenReturn("42");
    when(product.getName()).thenReturn("Name");
    when(product.getPrice()).thenReturn(new BigDecimal("2.3"));
    when(dynamoDbTemplate.save(Mockito.<Product>any())).thenReturn(new Product());
    when(dynamoDbTemplate.load(Mockito.<Key>any(), Mockito.<Class<Product>>any())).thenReturn(product);

    // Act
    ProductResponse actualUpdateProductByIdResult = productServiceImpl.updateProductById(null, new ProductRequest());

    // Assert
    verify(product, atLeast(1)).getId();
    verify(product, atLeast(1)).getName();
    verify(product).getPrice();
    verify(dynamoDbTemplate, atLeast(1)).load(isA(Key.class), isA(Class.class));
    verify(dynamoDbTemplate, atLeast(1)).save(isA(Product.class));
    assertEquals("42", actualUpdateProductByIdResult.getId());
    assertEquals("Name", actualUpdateProductByIdResult.getName());
    BigDecimal expectedPrice = new BigDecimal("2.3");
    assertEquals(expectedPrice, actualUpdateProductByIdResult.getPrice());
  }

  /**
   * Test {@link ProductServiceImpl#getAllProducts()}.
   *
   * <p>
   * Method under test: {@link ProductServiceImpl#getAllProducts()}
   */
  @Test
  @DisplayName("Test getAllProducts()")
  void testGetAllProducts() {
    // Arrange
    PageIterable<Product> pageIterable = mock(PageIterable.class);

    ArrayList<Page<Product>> pageList = new ArrayList<>();
    when(pageIterable.iterator()).thenReturn(pageList.iterator());
    when(dynamoDbTemplate.scanAll(Mockito.<Class<Product>>any())).thenReturn(pageIterable);

    // Act
    List<Product> actualAllProducts = productServiceImpl.getAllProducts();

    // Assert
    verify(dynamoDbTemplate).scanAll(isA(Class.class));
    verify(pageIterable).iterator();
    assertTrue(actualAllProducts.isEmpty());
  }
}
