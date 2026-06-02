package CRUDparecer.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import CRUDparecer.model.Aluno;
import CRUDparecer.repository.AlunoRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestAlunoService {

	@Mock
	private AlunoRepository alunoRepository;

	@InjectMocks
	private AlunoService alunoService;

	private Aluno alunoTeste;

	@Test
    void testAtualizar_Sucesso() {
        Aluno dadosNovos = new Aluno(1, "Novo Nome", "Nova Turma");
        when(alunoRepository.findById(1)).thenReturn(Optional.of(alunoTeste));
        when(alunoRepository.save(any(Aluno.class))).thenAnswer(i -> i.getArguments()[0]);

        Aluno resultado = alunoService.atualizarAluno(1, dadosNovos);

        assertNotNull(resultado);
        assertEquals("Novo Nome", resultado.getNome()); 
        verify(alunoRepository).save(any(Aluno.class));
    }

	@Test
	void testFindById() {
		when(alunoRepository.findById(1)).thenReturn(Optional.of(alunoTeste));

		Aluno resultado = alunoService.getAlunoById(1);
        
		assertTrue(resultado != null);
		assertEquals("João Silva", resultado.getNome());
		assertEquals("Turma A", resultado.getTurma());
	}

	@Test
	void testFindByIdNotFound() {
		when(alunoRepository.findById(999)).thenReturn(Optional.empty());

		Aluno resultado = alunoService.getAlunoById(999);

		assertNull(resultado);
	}

	@Test
	void testFindByTurma() {
		when(alunoRepository.findByTurma("Turma A")).thenReturn(Arrays.asList(alunoTeste));

		List<Aluno> resultado = alunoService.getAlunoByTurma("Turma A");

		assertFalse(resultado.isEmpty());
		assertEquals("João Silva", resultado.get(0).getNome());
	}   
    
	@Test
	void testFindByTurmaNotFound() {
		when(alunoRepository.findByTurma("Turma B")).thenReturn(Collections.emptyList());

		List<Aluno> resultado = alunoService.getAlunoByTurma("Turma B");

		assertTrue(resultado.isEmpty());
	}

	@Test
	void testCadastrar() {
		when(alunoRepository.save(alunoTeste)).thenReturn(alunoTeste);

		Aluno resultado = alunoService.cadastrarAluno(alunoTeste);

		assertNotNull(resultado);
		assertEquals(1, resultado.getId());
		verify(alunoRepository, times(1)).save(alunoTeste);
	}

	@Test
	void testCadastrarInvalido() {
		Aluno invalida = new Aluno(null, "Turma A");

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(invalida);
		});
		assertNotNull(exception);
	}

	@Test
	void testCadastrarNomeVazio() {
		Aluno invalida = new Aluno("", "Turma A");


		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(invalida);
		});
		assertNotNull(exception);
	}

	@Test
	void testCadastrarTurmaNula() {
		Aluno invalida = new Aluno("João Silva", null);


		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.cadastrarAluno(invalida);
		});
		assertNotNull(exception);
	}

	@Test
	void testAtualizar() {
		Aluno atualizada = new Aluno(1, "Praia", "Turma A");
		when(alunoRepository.findById(1)).thenReturn(Optional.of(alunoTeste));
		when(alunoRepository.save(alunoTeste)).thenReturn(alunoTeste);

		Aluno resultado = alunoService.atualizarAluno(1, atualizada);

		assertNotNull(resultado);
		verify(alunoRepository, times(1)).save(alunoTeste);
	}

	@Test
	void testAtualizarNaoEncontrada() {
		Aluno atualizada = new Aluno(1, "Praia", "Turma A");
		when(alunoRepository.findById(999)).thenReturn(Optional.empty());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			alunoService.atualizarAluno(999, atualizada);
		});
		assertNotNull(exception);
	}

	@Test
	void testDeleteById() {
		when(alunoRepository.existsById(1)).thenReturn(true);
		alunoService.deletarAluno(1);

		verify(alunoRepository, times(1)).deleteById(1);
	}
}