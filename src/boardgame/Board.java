package boardgame;

public class Board {

	private Integer rows;
	private Integer columns;
	private Piece[][] pieces; //matriz de pe�as 
	
	public Board(Integer rows, Integer columns) {
		if (rows < 1 || columns < 1) { //programa��o defensiva, antes de criar o tabuleiro mensgem de erro de ocorrer 
			throw new BoardException("Error creating board: there must be at least 1 row and 1 column"); 
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];//instaciada por linhas e colunas informadas
	}
//programa��o defensiva, retira o set de linha e colunas para n�o permitir altera��o nelas
	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public Piece piece(int row, int column) { //metodo que retorna na linha e coluna 
		if (!positionExists(row, column)) { //programa��o defensiva, se essa posi��o n�o exite � lan�ada a mensagem de exce��o
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];
	}
	public Piece piece(Position position) { //metodo que retorna a posi��o
		if (!positionExists(position)) { //programa��o defensiva, se essa posi��o n�o exite � lan�ada a mensagem de exce��o
			throw new BoardException("Position not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePiece(Piece piece, Position position) {//pega a posi��o dada a matriz, e insere a pe�a que ser� informada 
		if (thereIsAPiece(position)) { //se exite uma pe�a nessa posi��o, nao pode colocar outra, por isso a msg de exce��o
			throw new BoardException("There is already a piece on position " + position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; //pe�a n�o � mais nula
	}
	
	public Piece removePiece(Position position) { //remover uma pe�a do tabuleiro
		if (!positionExists(position)) { //programa��o defensiva
			throw new BoardException("Position not on the noard");
		}
		if (piece(position) == null) { //testa se ha alguma pe�a na posi��o
			return null;
		}
		Piece aux = piece(position);//aux recebe a pe�a na posi��o
		aux.position = null; //a pe�a recebe o valor nulo para ser retirado do tabuleiro
		pieces[position.getRow()][position.getColumn()] = null; //a mtriz de pe�as recebe nulo para pe�a selecionada
		return aux;//retorna a pe�a excluida
	}
	
	public boolean positionExists(int row, int column) { //ha momentos que ser� mais facil testar por linha e coluna doque com posi��o
		return row >= 0 && row < rows && column >= 0 && column < columns; //para saber se uma posi��o existe
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(),position.getColumn()); 
	}
	
	public boolean thereIsAPiece(Position position) { //para saber se existe uma pe�a na posi��o desejada
		if (!positionExists(position)) { //programa��o defensiva, se essa posi��o n�o exite � lan�ada a mensagem de exce��o
			throw new BoardException("Position not on the board");
		}
		return piece(position) != null; //se for diferente de nulo tem uma pe�a na posi��o
	}
	
	
}
