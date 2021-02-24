package br.com.cadastrodeveiculos.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cadastrodeveiculos.entity.VeiculoEntity;

@Repository
public interface VeiculoRepository extends CrudRepository<VeiculoEntity, Long>  {
	
	List<VeiculoEntity> findByVendido(Boolean vendido);
	
	@Query("SELECT v FROM VeiculoEntity v order by ano")
	List<VeiculoEntity> findAllOrderByAno();
	
	@Query("SELECT v FROM VeiculoEntity v where v.created BETWEEN :dataInicial and :dataFinal")
	List<VeiculoEntity> findByLastWeek(@Param("dataInicial") LocalDateTime dataInicial, @Param("dataFinal") LocalDateTime dataFinal );
	
	@Query("SELECT v FROm VeiculoEntity v order by v.marca")
	List<VeiculoEntity> findAllByMarca();

}
