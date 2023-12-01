package boardgame;

public class Board {

	private Integer rows;
	private Integer columns;
	private Piece[][] pieces; //matriz de peças 
	
	public Board(Integer rows, Integer columns) {
		if (rows < 1 || columns < 1) { //programação defensiva, antes de criar o tabuleiro mensgem de erro de ocorrer 
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column"); 
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];//instaciada por linhas e colunas informadas
	}
//programação defensiva, retira o set de linha e colunas para não permitir alteração nelas
	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) { //metodo que retorna na linha e coluna 
		if (!positionExists(row, column)) { //programação defensiva, se essa posição não exite é lançada a mensagem de exceção
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	public Piece piece(Position position) { //metodo que retorna a posição
		if (!positionExists(position)) { //programação defensiva, se essa posição não exite é lançada a mensagem de exceção
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {//pega a posição dada a matriz, e insere a peça que será informada 
		if (thereIsAPiece(position)) { //se exite uma peça nessa posição, nao pode colocar outra, por isso a msg de exceção
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; //peça não é mais nula
	}
	
	public Piece removePiece(Position position) { //remover uma peça do tabuleiro
		if (!positionExists(position)) { //programação defensiva
			throw new BoardException("Position not on the noard");
		}
		if (piece(position) == null) { //testa se ha alguma peça na posição
			return null;
		}
		Piece aux = piece(position);//aux recebe a peça na posição
		aux.position = null; //a peça recebe o valor nulo para ser retirado do tabuleiro
		pieces[position.getRow()][position.getColumn()] = null; //a mtriz de peças recebe nulo para peça selecionada
		return aux;//retorna a peça excluida
	}
	
	public boolean positionExists(int row, int column) { //ha momentos que será mais facil testar por linha e coluna doque com posição
		return row >= 0 && row < rows && column >= 0 && column < columns; //para saber se uma posição existe
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(),position.getColumn()); 
	}
	
	public boolean thereIsAPiece(Position position) { //para saber se existe uma peça na posição desejada
		if (!positionExists(position)) { //programação defensiva, se essa posição não exite é lançada a mensagem de exceção
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; //se for diferente de nulo tem uma peça na posição
	}
	
	
}
