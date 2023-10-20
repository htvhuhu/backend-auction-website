package edu.miu.waa.onlineauctionapi.repository;

import edu.miu.waa.onlineauctionapi.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
  public int countByProductId(long id);
}
