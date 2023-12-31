package edu.miu.waa.onlineauctionapi.service;

import edu.miu.waa.onlineauctionapi.common.ProductStatus;
import edu.miu.waa.onlineauctionapi.model.Product;
import edu.miu.waa.onlineauctionapi.repository.BidRepository;
import edu.miu.waa.onlineauctionapi.repository.ProductRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  private final BidRepository bidRepository;

  @Override
  public Product saveProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Page<Product> findActiveProductByStatusAndName(String name, Pageable pageable) {
    return productRepository.findByStatusAndNameContainsAndBidDueDateAfterOrderByIdAsc(
        ProductStatus.RELEASE.getName(), name, addDays(0), pageable);
  }

  private static LocalDateTime addDays(int days)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date());
    cal.add(Calendar.DATE, days); //minus number would decrement the days

    Date input = cal.getTime();
    LocalDateTime dateTime = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    System.out.println("LocalDateTime" + dateTime);
    return dateTime;
  }

  @Override
  public Product getProduct(long id) {
    return productRepository.findById(id).orElse(null);
  }

  @Override
  public Optional<Product> findById(long id) {
      Optional<Product> productOptional = productRepository.findById(id);
  
      productOptional.ifPresent(product -> {
          long bidCount = bidRepository.countBidsByProductId(id);
          product.setBidCount(bidCount);
      });
  
      return productOptional;
  }
  
  @Override
  public void delete(Product product) {
    productRepository.delete(product);
  }

  @Override
  public List<Product> getSellerProducts(String owner) {
    List<Object[]> results = productRepository.findProductsByOwnerWithBidCount(owner);
        return results.stream().map(result -> {
            Product product = (Product) result[0];
            long bidCount = (long) result[1];
            product.setBidCount(bidCount);
            return product;
        }).collect(Collectors.toList());
  }
}
