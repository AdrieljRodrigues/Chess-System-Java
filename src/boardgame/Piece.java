package boardgame;

public class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() { //set � excluido para n�o haver altera��o pelo tabuleiro
		return board;
	}//protected para somente classes do mesmo pacote poder acessar o tabuleiro de uma pe�a
	
	
	
	
}
