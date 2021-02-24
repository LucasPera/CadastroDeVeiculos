package br.com.cadastrodeveiculos.dto;

import lombok.Data;

@Data
public class VeiculoDto {
	
	private String veiculo;
	private String marca;
	private Integer ano;
	private String descricao;
	private Boolean vendido;

}
