package CRUDparecer.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import CRUDparecer.model.Aluno;
import CRUDparecer.service.AlunoService;

@ExtendWith(MockitoExtension.class)
public class TestAlunoController {

    @Mock
    private AlunoService alunoService;

    @InjectMocks
    private AlunoController alunoController;

    private Aluno alunoTeste;


    @Test
    void testGetAllAlunos() {
        when(alunoService.getAllAlunos()).thenReturn(Arrays.asList(alunoTeste));

        ResponseEntity<List<Aluno>> resposta = alunoController.getAllAlunos();

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
        assertEquals(1, resposta.getBody().size());
    }

    @Test
    void testGetAlunoById() {
        when(alunoService.getAlunoById(1)).thenReturn(alunoTeste);

        ResponseEntity<Aluno> resposta = alunoController.getAlunoById(1);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals("João Silva", resposta.getBody().getNome());
    }

    @Test
    void testGetAlunoByIdNotFound() {
        when(alunoService.getAlunoById(999)).thenReturn(null);

        ResponseEntity<Aluno> resposta = alunoController.getAlunoById(999);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void testGetAlunoByTurma() {
        when(alunoService.getAlunoByTurma("Turma A")).thenReturn(Arrays.asList(alunoTeste));

        ResponseEntity<List<Aluno>> resposta = alunoController.getAlunoByTurma("Turma A");

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertFalse(resposta.getBody().isEmpty());
    }

    @Test
    void testGetAlunoByTurmaNotFound() {
        when(alunoService.getAlunoByTurma("Turma B")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Aluno>> resposta = alunoController.getAlunoByTurma("Turma B");

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertTrue(resposta.getBody().isEmpty());
    }

    @Test
    void testCadastrarAluno() {
        when(alunoService.cadastrarAluno(alunoTeste)).thenReturn(alunoTeste);

        ResponseEntity<Aluno> resposta = alunoController.cadastrarAluno(alunoTeste);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void testCadastrarAlunoBadRequest() {
        when(alunoService.cadastrarAluno(null)).thenThrow(new IllegalArgumentException("Nome inválido"));

        ResponseEntity<Aluno> resposta = alunoController.cadastrarAluno(null);

        assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
    }

    @Test
    void testAtualizarAluno() {
        when(alunoService.atualizarAluno(1, alunoTeste)).thenReturn(alunoTeste);

        ResponseEntity<Aluno> resposta = alunoController.atualizarAluno(1, alunoTeste);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertNotNull(resposta.getBody());
    }

    @Test
    void testAtualizarAlunoNotFound() {
        when(alunoService.atualizarAluno(999, alunoTeste)).thenReturn(null);

        ResponseEntity<Aluno> resposta = alunoController.atualizarAluno(999, alunoTeste);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void testDeletarAluno() {
        when(alunoService.deletarAluno(1)).thenReturn(true);

        ResponseEntity<Void> resposta = alunoController.deletarAluno(1);

        assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
    }

    @Test
    void testDeletarAlunoNotFound() {
        when(alunoService.deletarAluno(999)).thenReturn(false);

        ResponseEntity<Void> resposta = alunoController.deletarAluno(999);

        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

}
