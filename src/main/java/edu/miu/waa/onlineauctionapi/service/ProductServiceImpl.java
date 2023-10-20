package edu.miu.waa.onlineauctionapi.service;

import edu.miu.waa.onlineauctionapi.common.ProductStatus;
import edu.miu.waa.onlineauctionapi.model.Product;
import edu.miu.waa.onlineauctionapi.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  @Override
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Page<Product> getActiveProducts(Pageable pageable) {
    return productRepository.findByStatusOrderByIdAsc("release", pageable);
  }

  @Override
  public Page<Product> findActiveProductByStatusAndName(String name, Pageable pageable) {
    return productRepository.findByStatusAndNameContainsOrderByIdAsc(
        ProductStatus.RELEASE.getName(), name, pageable);
  }

  @Override
  public Product getProduct(long id) {
    return productRepository.findById(id).orElse(null);
  }

  @Override
  public Optional<Product> findById(long id) {
    return productRepository.findById(id);
  }

  @Override
  public void delete(Product product) {
    productRepository.delete(product);
  }

  @Override
  public List<Product> getSellerProducts(String owner) {
    return productRepository.findByOwner(owner);
  }
}
