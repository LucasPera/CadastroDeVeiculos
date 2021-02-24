package br.com.cadastrodeveiculos.service;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cadastrodeveiculos.converter.VeiculoConverter;
import br.com.cadastrodeveiculos.dto.VeiculoDecadasDto;
import br.com.cadastrodeveiculos.dto.VeiculoDto;
import br.com.cadastrodeveiculos.dto.VeiculoResponseDTO;
import br.com.cadastrodeveiculos.entity.VeiculoEntity;
import br.com.cadastrodeveiculos.repository.VeiculoRepository;

@Service
public class VeiculoService {
	
	@Autowired
	VeiculoRepository veiculoRepository;
	
	@Autowired
	VeiculoConverter veiculoConverter;
	
	public List<VeiculoResponseDTO> listar(){
		
		Iterable<VeiculoEntity> veiculoIterable = veiculoRepository.findAll();
		
		return veiculoConverter.veiculoIterableToResponseList(veiculoIterable);
		
	}
	
	public List<VeiculoResponseDTO> listarVeiculosNaoVendidos() {
		
		List<VeiculoEntity> veiculoList = veiculoRepository.findByVendido(false);
		
		return veiculoConverter.veiculoEntityListToResponseList(veiculoList);
	}

	public List<VeiculoDecadasDto> listarVeiculosPorDecada() {
		
		List<VeiculoEntity> veiculoList = veiculoRepository.findAllOrderByAno();
		
		return veiculoConverter.veiculoEntityListToDecadaList(veiculoList);
	}

	public List<VeiculoResponseDTO> listarVeiculosRegistradosUltimaSemana() {
		
		LocalDateTime dataInicial = LocalDateTime.now();
		
		dataInicial = dataInicial.minusWeeks(1L);
		
		List<VeiculoEntity> veiculos = veiculoRepository.findByLastWeek(dataInicial, LocalDateTime.now());
		
		return veiculoConverter.veiculoEntityListToResponseList(veiculos);
		
	}

	public List<VeiculoResponseDTO> listarVeiculosPorFabricante() {
		
		List<VeiculoEntity> veiculoList = veiculoRepository.findAllByMarca();
		
		return veiculoConverter.veiculoEntityListToResponseList(veiculoList);
	}

	public VeiculoResponseDTO getVeiculoById(Long id) {
		
		Optional<VeiculoEntity> veiculoEntity = veiculoRepository.findById(id);
		VeiculoResponseDTO veiculoResponseDTO = new VeiculoResponseDTO();
		
		
		if(veiculoEntity.isPresent()) {
			veiculoResponseDTO = veiculoConverter.veiculoEntityTOResponse(veiculoEntity.get());
		}
		
		return veiculoResponseDTO;
		
	}
	
	public VeiculoEntity salvarVeiculo(VeiculoDto veiculoDto) {
		
		VeiculoEntity veiculoSalvo = null;
		
		if(veiculoDto != null) {
			List<String> marcas = this.listarMarcasCorretas();
			
			if(marcas.contains(veiculoDto.getMarca())) {
				VeiculoEntity veiculoEntity = veiculoConverter.veiculoDtoToEntity(veiculoDto);
				veiculoEntity.setCreated(LocalDateTime.now());
				veiculoSalvo = veiculoRepository.save(veiculoEntity);
			}
			
		}
		
		return veiculoSalvo;
	}
	
	public String deletarVeiculo(Long id) {
		
		veiculoRepository.deleteById(id);
		
		return ("veiculo deletado");
		
	}
	
	public VeiculoEntity atualizarVeiculo(VeiculoDto veiculoDto, Long id) {
		
		Optional<VeiculoEntity> veiculoParaAtulizar = veiculoRepository.findById(id);
		
		if(veiculoParaAtulizar.isPresent()) {
			veiculoParaAtulizar.get().setAno(veiculoDto.getAno());
			veiculoParaAtulizar.get().setDescricao(veiculoDto.getDescricao());
			veiculoParaAtulizar.get().setMarca(veiculoDto.getMarca());
			veiculoParaAtulizar.get().setUpdated(LocalDateTime.now());
			veiculoParaAtulizar.get().setVeiculo(veiculoDto.getVeiculo());
			veiculoParaAtulizar.get().setVendido(veiculoDto.getVendido());
			
			return veiculoRepository.save(veiculoParaAtulizar.get());
		}
		
		return null;
		
	}
	
	public VeiculoEntity atualizarParcialmenteVeiculo(VeiculoDto veiculoDto, Long id) {
		
		Optional<VeiculoEntity> veiculoParaAtulizar = veiculoRepository.findById(id);
		
		if(veiculoParaAtulizar.isPresent()) {
			
			if(veiculoDto.getAno() != null ) {
				veiculoParaAtulizar.get().setAno(veiculoDto.getAno());
			}
			
			if(veiculoDto.getDescricao() != null) {
				veiculoParaAtulizar.get().setDescricao(veiculoDto.getDescricao());
			}
			
			if(veiculoDto.getMarca() != null) {
				veiculoParaAtulizar.get().setMarca(veiculoDto.getMarca());
			}
			
			if(veiculoDto.getVeiculo() != null) {
				veiculoParaAtulizar.get().setVeiculo(veiculoDto.getVeiculo());
			}
			
			if(veiculoDto.getVendido() != null) {
				veiculoParaAtulizar.get().setVendido(veiculoDto.getVendido());
			}
			
			veiculoParaAtulizar.get().setUpdated(LocalDateTime.now());
			
			return veiculoRepository.save(veiculoParaAtulizar.get());
			
		}
		
		return null;
	}
	
	private List<String> listarMarcasCorretas() {
		
		List<String> marcaList = new ArrayList<>();
		
		try {
			Scanner in = new Scanner(new FileReader("MarcasPermitidas.txt"));
			
			while (in.hasNextLine()) {
			    String line = in.nextLine();
			    System.out.println(line);
			    marcaList.add(line);
			}
			
			in.close(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return marcaList;
		
	}
}
