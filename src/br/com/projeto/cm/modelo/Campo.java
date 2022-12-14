package br.com.projeto.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto;
	private boolean marcado;
	private boolean minado;

	private List<Campo> vizinhos = new ArrayList<Campo>();
	private List<CampoObservador> observadores = new ArrayList<CampoObservador>();

	public Campo(int linha, int coluna) {

		this.linha = linha;
		this.coluna = coluna;

	}

	public void registrarObservador(CampoObservador observador) {
		observadores.add(observador);
	}

	private void notificarEvento(CampoEvento evento) {

		observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
	}

	boolean adicionarVizinho(Campo vizinho) {

		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}

	}

	public void alternarMarcacao() {

		if (!aberto) {
			marcado = !marcado;

			if (marcado) {
				notificarEvento(CampoEvento.MARCAR);
			} else {
				notificarEvento(CampoEvento.DESMARCAR);
			}
		}
	}

	public boolean abrir() {

		if (!aberto && !marcado) {

			if (minado) {
				notificarEvento(CampoEvento.EXPLODIR);
				return true;
			}

			setAberto(true);

			if (vizinhancaSegura()) {

				vizinhos.forEach(v -> v.abrir());
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		minado = true;
	}

	public boolean isMarcado() {
		return marcado;
	}

	public boolean isAberto() {

		return aberto;
	}

	void setAberto(boolean aberto) {
		this.aberto = aberto;

		if (aberto) {
			notificarEvento(CampoEvento.ABRIR);
		}
	}

	public boolean isMinado() {
		return minado;
	}

	public boolean isFechado() {

		return !isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	public boolean objetivoAlcancado() {
		boolean desvendado = aberto && !minado;
		boolean protegido = minado && marcado;

		return desvendado || protegido;
	}

	public int minasNaVizinhanca() {
		return (int) vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
		notificarEvento(CampoEvento.REINICIAR);
	}

	public String toString() {

		if (marcado) {
			return "X";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhanca() > 0) {
			return Long.toString(minasNaVizinhanca());
		}
		if (aberto) {
			return " ";
		} else {
			return "?";
		}

	}

}
