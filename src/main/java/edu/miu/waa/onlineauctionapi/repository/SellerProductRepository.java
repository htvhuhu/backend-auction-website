package edu.miu.waa.onlineauctionapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import edu.miu.waa.onlineauctionapi.model.SellerProduct;


public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {
}
