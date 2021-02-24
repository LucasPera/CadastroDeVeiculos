package br.com.cadastrodeveiculos.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.cadastrodeveiculos.dto.VeiculoDecadasDto;
import br.com.cadastrodeveiculos.dto.VeiculoDto;
import br.com.cadastrodeveiculos.dto.VeiculoResponseDTO;
import br.com.cadastrodeveiculos.entity.VeiculoEntity;

@Component
public class VeiculoConverter {
	
	public List<VeiculoResponseDTO> veiculoIterableToResponseList (Iterable<VeiculoEntity> veiculoEntity) {
		
		List<VeiculoResponseDTO> veiculoResponseDTOList = new ArrayList<>();
		
		for (VeiculoEntity veiculo : veiculoEntity) {
			
			VeiculoResponseDTO veiculoResponseDTO = this.veiculoEntityTOResponse(veiculo);
		
			veiculoResponseDTOList.add(veiculoResponseDTO);
		}
		
		return veiculoResponseDTOList;
	}
	
	public List<VeiculoResponseDTO> veiculoEntityListToResponseList (List<VeiculoEntity> veiculoEntity) {
		
		List<VeiculoResponseDTO> veiculoResponseDTOList = new ArrayList<>();
		
		for (VeiculoEntity veiculo : veiculoEntity) {
			
			VeiculoResponseDTO veiculoResponseDTO = this.veiculoEntityTOResponse(veiculo);
			
			veiculoResponseDTOList.add(veiculoResponseDTO);
		}
		
		return veiculoResponseDTOList;
	}
	
	public List<VeiculoDecadasDto> veiculoEntityListToDecadaList(List<VeiculoEntity> veiculoList) {
		
		List<VeiculoDecadasDto> veiculoDecadasDtoList = new ArrayList<>();
		
		veiculoList.forEach(veiculo -> {
			VeiculoDecadasDto veiculoDecadasDto = new VeiculoDecadasDto();		
			
			String decada =  veiculo.getAno().toString();
			decada = decada.substring(0, decada.length() - 1);
			decada = decada + "0";
			
			veiculoDecadasDto.setId(veiculo.getId());
			veiculoDecadasDto.setAno(veiculo.getAno());
			veiculoDecadasDto.setCreated(veiculo.getCreated());
			veiculoDecadasDto.setDecada(decada);
			veiculoDecadasDto.setDescricao(veiculo.getDescricao());
			veiculoDecadasDto.setMarca(veiculo.getMarca());
			veiculoDecadasDto.setUpdated(veiculo.getUpdated());
			veiculoDecadasDto.setVeiculo(veiculo.getVeiculo());
			veiculoDecadasDto.setVendido(veiculo.getVendido());
			
			veiculoDecadasDtoList.add(veiculoDecadasDto);
			
		});
		
		return veiculoDecadasDtoList;
		
	}

	public VeiculoResponseDTO veiculoEntityTOResponse(VeiculoEntity veiculoEntity) {
		
		VeiculoResponseDTO veiculoResponseDTO = new VeiculoResponseDTO();
		
		veiculoResponseDTO.setId(veiculoEntity.getId());
		veiculoResponseDTO.setAno(veiculoEntity.getAno());
		veiculoResponseDTO.setCreated(veiculoEntity.getCreated());
		veiculoResponseDTO.setDescricao(veiculoEntity.getDescricao());
		veiculoResponseDTO.setMarca(veiculoEntity.getMarca());
		veiculoResponseDTO.setUpdated(veiculoEntity.getUpdated());
		veiculoResponseDTO.setVeiculo(veiculoEntity.getVeiculo());
		veiculoResponseDTO.setVendido(veiculoEntity.getVendido());
		
		return veiculoResponseDTO;
	}

	public VeiculoEntity veiculoDtoToEntity(VeiculoDto veiculoDto) {
		
		VeiculoEntity veiculoEntity = new VeiculoEntity();
		
		veiculoEntity.setAno(veiculoDto.getAno());
		veiculoEntity.setDescricao(veiculoDto.getDescricao());
		veiculoEntity.setMarca(veiculoDto.getMarca());
		veiculoEntity.setVeiculo(veiculoDto.getVeiculo());
		veiculoEntity.setVendido(veiculoDto.getVendido());
		
		return veiculoEntity;
		
	}

}
