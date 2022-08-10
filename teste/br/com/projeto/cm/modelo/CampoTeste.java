package br.com.projeto.cm.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.projeto.cm.excecao.ExplosaoException;
import br.com.projeto.cm.modelo.Campo;

public class CampoTeste {

	// O teste e realizado por campo e coluna 
	// da mesma forma que o excel 
	Campo campo = new Campo(3,3);
	
	@Test
	void testeVizinhoRealDistancia1() {
		Campo vizinho = new Campo(2,2);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testeVizinhoRealDistancia2() {
		Campo vizinho = new Campo(2,3);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testeVizinhoRealDistancia3() {
		Campo vizinho = new Campo(2,4);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testeVizinhoRealDistancia4() {
		Campo vizinho = new Campo(3,2);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testeVizinhoRealDistancia5() {
		Campo vizinho = new Campo(3,4);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testeVizinhoRealDistancia6() {
		Campo vizinho = new Campo(4,2);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testeVizinhoRealDistancia7() {
		Campo vizinho = new Campo(4,3);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testeVizinhoRealDistancia8() {
		Campo vizinho = new Campo(4,4);
		assertTrue(campo.adicionarVizinho(vizinho));
	}
	
	@Test
	void testNaoVizinho () {
		Campo vizinho = new Campo(1,1);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	
	@Test
	void testValorPadraoDoAtributoMarcado() {
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testAlternarMarcacao() {
		campo.alternarMarcacao();
		assertTrue(campo.isMarcado());
	}
	
	@Test
	void testAlternarMarcacaoDuasChamadas() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		assertFalse(campo.isMarcado());
	}
	
	@Test
	void testAbrirNaoMinadoNaoMarcado() {
		
		assertTrue(campo.abrir());
	}
	
	@Test
	void testAbrirNaoMinadoMarcado() {
		campo.alternarMarcacao();
		assertFalse(campo.abrir());
	}
	
	@Test
	void testAbrirMinadoMarcado() {
		campo.alternarMarcacao();
		campo.minar();
		assertFalse(campo.abrir());
	}
	
	void testAbrirMinadoNaoMarcado() {
		campo.minar();
		assertThrows(ExplosaoException.class, () -> {
			assertFalse(campo.abrir());
		});
		
	}
	
	void testAbrirComVizinho() {
		Campo vizinho1 = new Campo(2, 2);
		Campo vizinhoDoVizinho1 = new Campo(1, 1);
		vizinho1.adicionarVizinho(vizinhoDoVizinho1);
		
		campo.adicionarVizinho(vizinho1);
		
		campo.abrir();
		
		assertFalse(vizinho1.isAberto() && vizinhoDoVizinho1.isAberto());
		
		
	}
	
	void testAbrirComVizinho2() {
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 1);
		campo12.minar();
		
		Campo campo22 = new Campo(2, 2);
		campo22.adicionarVizinho(campo11);
		campo22.adicionarVizinho(campo12);
		
		campo.adicionarVizinho(campo22);
		campo.abrir();
		
		assertFalse(campo.isAberto() && campo.isFechado());
		
		
	}
	
	
	
	

	
}
