package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position = null;
	}

	protected Board getBoard() { //set � excluido para n�o haver altera��o pelo tabuleiro
		return board;
	}//protected para somente classes do mesmo pacote poder acessar o tabuleiro de uma pe�a
	
	public abstract boolean[][] possibleMoves();
		
	public boolean possibleMove(Position position) {//recebe uma posi��o, ela retorna um valor verdadeiro ou falso, se � possivel essa pe�a se mover para essa dire��o
		return possibleMoves()[position.getRow()][position.getColumn()];//metodo retornando uma matriz
	}

	public boolean isThereAnyPossibleMove() {//chama o matodo que mostra a matriz booleana, ele varre a matriz pra ver se pelo menos uma posi��o � verdadeira
		boolean[][] mat = possibleMoves();
		for (int i=0; i<mat.length; i++) {
			for (int j=0; j<mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}
