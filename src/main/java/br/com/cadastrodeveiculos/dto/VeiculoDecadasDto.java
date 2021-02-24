package br.com.cadastrodeveiculos.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class VeiculoDecadasDto {
	
	private String decada;
	private Long id;
	private String veiculo;
	private String marca;
	private Integer ano;
	private String descricao;
	private Boolean vendido;
	private LocalDateTime created;
	private LocalDateTime updated;
}
