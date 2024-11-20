package br.com.allen.dynamodbcrud.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import br.com.allen.dynamodbcrud.model.dto.ProductRequest;
import br.com.allen.dynamodbcrud.model.dto.ProductResponse;
import br.com.allen.dynamodbcrud.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ProductController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class ProductControllerTest {
  @Autowired private ProductController productController;

  @MockBean private ProductService productService;

  /**
   * Test {@link ProductController#getProductById(String)}.
   *
   * <p>Method under test: {@link ProductController#getProductById(String)}
   */
  @Test
  @DisplayName("Test getProductById(String)")
  void testGetProductById() throws Exception {
    // Arrange
    ProductResponse.ProductResponseBuilder nameResult =
        ProductResponse.builder().id("42").name("Name");
    ProductResponse buildResult = nameResult.price(new BigDecimal("2.3")).build();
    when(productService.getProductById(Mockito.<String>any())).thenReturn(buildResult);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/api/products/{productId}", "42");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(
            MockMvcResultMatchers.content()
                .string("{\"id\":\"42\",\"name\":\"Name\",\"price\":2.3}"));
  }

  /**
   * Test {@link ProductController#getAllProducts()}.
   *
   * <p>Method under test: {@link ProductController#getAllProducts()}
   */
  @Test
  @DisplayName("Test getAllProducts()")
  void testGetAllProducts() throws Exception {
    // Arrange
    when(productService.getAllProducts()).thenReturn(new ArrayList<>());
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products");

    // Act and Assert
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  /**
   * Test {@link ProductController#addProduct(ProductRequest, HttpServletResponse)}.
   *
   * <p>Method under test: {@link ProductController#addProduct(ProductRequest, HttpServletResponse)}
   */
  @Test
  @DisplayName("Test addProduct(ProductRequest, HttpServletResponse)")
  void testAddProduct() throws Exception {
    // Arrange
    doNothing().when(productService).addProduct(Mockito.<ProductRequest>any());

    ProductRequest productRequest = new ProductRequest();
    productRequest.setName("Name");
    productRequest.setPrice(new BigDecimal("2.3"));
    String content = (new ObjectMapper()).writeValueAsString(productRequest);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  /**
   * Test {@link ProductController#updateProduct(String, ProductRequest)}.
   *
   * <p>Method under test: {@link ProductController#updateProduct(String, ProductRequest)}
   */
  @Test
  @DisplayName("Test updateProduct(String, ProductRequest)")
  void testUpdateProduct() throws Exception {
    // Arrange
    ProductResponse.ProductResponseBuilder nameResult =
        ProductResponse.builder().id("42").name("Name");
    ProductResponse buildResult = nameResult.price(new BigDecimal("2.3")).build();
    when(productService.updateProductById(Mockito.<String>any(), Mockito.<ProductRequest>any()))
        .thenReturn(buildResult);

    ProductRequest productRequest = new ProductRequest();
    productRequest.setName("Name");
    productRequest.setPrice(new BigDecimal("2.3"));
    String content = (new ObjectMapper()).writeValueAsString(productRequest);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/api/products/{productId}", "42")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);

    // Act and Assert
    MockMvcBuilders.standaloneSetup(productController)
        .build()
        .perform(requestBuilder)
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(
            MockMvcResultMatchers.content()
                .string("{\"id\":\"42\",\"name\":\"Name\",\"price\":2.3}"));
  }

  /**
   * Test {@link ProductController#deleteById(String)}.
   *
   * <p>Method under test: {@link ProductController#deleteById(String)}
   */
  @Test
  @DisplayName("Test deleteById(String)")
  void testDeleteById() throws Exception {
    // Arrange
    doNothing().when(productService).deleteProductById(Mockito.<String>any());
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.delete("/api/products/{productId}", "42");

    // Act
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(productController).build().perform(requestBuilder);

    // Assert
    actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
