package betting3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import betting3.entity.Bet;

public interface BetRepository extends JpaRepository<Bet, Long>{
	
	List<Bet> findAllBetsByOrderByDateDesc();
	List<Bet> findAllBetsByOrderByIdAsc();


}
