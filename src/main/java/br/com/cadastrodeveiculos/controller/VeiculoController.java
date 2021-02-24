package br.com.cadastrodeveiculos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cadastrodeveiculos.dto.VeiculoDecadasDto;
import br.com.cadastrodeveiculos.dto.VeiculoDto;
import br.com.cadastrodeveiculos.dto.VeiculoResponseDTO;
import br.com.cadastrodeveiculos.entity.VeiculoEntity;
import br.com.cadastrodeveiculos.service.VeiculoService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
	
	@Autowired
	VeiculoService veiculoService;

	@GetMapping()
	@ApiOperation("Listar todos os veículos")
	public ResponseEntity<List<VeiculoResponseDTO>> getAllVeiculos() {
		
		List<VeiculoResponseDTO> veiculosList = veiculoService.listar();
		
		return ResponseEntity.status(HttpStatus.OK).body(veiculosList);
	}
	
	@GetMapping("/{id}")
	@ApiOperation("Consultar veículo por id")
	public ResponseEntity<VeiculoResponseDTO> getVeiculosById (@PathVariable Long id) {
		
		VeiculoResponseDTO veiculoResponseDTO = veiculoService.getVeiculoById(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(veiculoResponseDTO);
		
	}
	
	@GetMapping("/find/nao_vendidos")
	@ApiOperation("Consulta de veículos que não foram vendidos ")
	public ResponseEntity<List<VeiculoResponseDTO>> getVeiculosNaoVendidos() {
		
		List<VeiculoResponseDTO> veiculosList = veiculoService.listarVeiculosNaoVendidos();
		
		return ResponseEntity.status(HttpStatus.OK).body(veiculosList);
		
	}
	
	@GetMapping("/find/decada")
	@ApiOperation("Consulta de veículos por decadas ")
	public ResponseEntity<List<VeiculoDecadasDto>> getVeiculosPorDecada() {
		
		List<VeiculoDecadasDto> veiculosList = veiculoService.listarVeiculosPorDecada();
		
		return ResponseEntity.status(HttpStatus.OK).body(veiculosList);
		
	}
	
	@GetMapping("/find/ultima_semana")
	@ApiOperation("Consulta de veículos que foram registrados na ultima semana ")
	public ResponseEntity<List<VeiculoResponseDTO>> getVeiculosRegistradosUltimaSemana() {
		
		List<VeiculoResponseDTO> veiculosList = veiculoService.listarVeiculosRegistradosUltimaSemana();
		
		return ResponseEntity.status(HttpStatus.OK).body(veiculosList);
		
	}
	
	@GetMapping("/find/fabricante")
	@ApiOperation("Consulta de veículos por fabricante ")
	public ResponseEntity<List<VeiculoResponseDTO>> getVeiculosPorFabricante() {
		
		List<VeiculoResponseDTO> veiculosList = veiculoService.listarVeiculosPorFabricante();
		
		return ResponseEntity.status(HttpStatus.OK).body(veiculosList);
		
	}
	
	@PostMapping()
	@ApiOperation("Adicionar novo veículo")
	public ResponseEntity<String> adicionarVeiculo(@RequestBody VeiculoDto veiculoDto) {
		
		VeiculoEntity veiculoSalvo = veiculoService.salvarVeiculo(veiculoDto);
		
		if(veiculoSalvo != null) {
			return ResponseEntity.status(HttpStatus.OK).body("Veículo cadastrado com sucesso!");
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Marca do veículo incorreta");
	}
	
	@PutMapping("/{id}")
	@ApiOperation("Atulizar veículo")
	public ResponseEntity<VeiculoEntity> atualizarDadosVeiculo(@RequestBody VeiculoDto veiculoDto, @PathVariable Long id) {
		
		VeiculoEntity veiculoEntityAtualizado = veiculoService.atualizarVeiculo(veiculoDto, id);
		
		return ResponseEntity.status(HttpStatus.OK).body(veiculoEntityAtualizado);
	}
	
	@PatchMapping("/{id}")
	@ApiOperation("Atualização parcial de um veículo")
	public ResponseEntity<VeiculoEntity> atualizarDadosVeiculoPatch(@RequestBody VeiculoDto veiculoDto, @PathVariable Long id) {
		
		VeiculoEntity veiculoAtualizado = veiculoService.atualizarParcialmenteVeiculo(veiculoDto, id);
		
		if(veiculoAtualizado != null) {
			return ResponseEntity.status(HttpStatus.OK).body(veiculoAtualizado);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@DeleteMapping("/{id}")
	@ApiOperation("Deletar um veículo")
	public ResponseEntity<String> deleteVeiculo(@PathVariable Long id) {
		
		String status = veiculoService.deletarVeiculo(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(status);
		
	}
	
}
