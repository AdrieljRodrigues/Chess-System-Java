package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

	public Rook(Board board, Color color) {//repassa achamada para a super classe
		super(board, color);
	}
	
	@Override
	public String toString() {
		return "R"; //onde estiver essa peça, ela ira aparecer no tabuleir por essa letra
	}
	
	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		
		Position p = new Position(0, 0);
		
		//above
		p.setValues(position.getRow() - 1, position.getColumn());//pega a posição da propria peça
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //enquanto existir linha vazias ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
				p.setRow(p.getRow() - 1);
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se houver um oponente na frente ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
			}
			
		//left
		p.setValues(position.getRow() , position.getColumn() - 1);//pega a posição da propria peça
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //enquanto existir linha vazias ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
				p.setColumn(p.getColumn() - 1);
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se houver um oponente na frente ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
			}
				
		//rigth
		p.setValues(position.getRow() , position.getColumn() + 1);//pega a posição da propria peça
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //enquanto existir linha vazias ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
				p.setColumn(p.getColumn() + 1);
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se houver um oponente na frente ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
			}
					
		//below
		p.setValues(position.getRow() + 1, position.getColumn());//pega a posição da propria peça
			while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) { //enquanto existir linha vazias ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
				p.setRow(p.getRow() + 1);
			}
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) { //se houver um oponente na frente ela pode ocupar
				mat[p.getRow()][p.getColumn()] = true;
			}
		
		return mat;
	}

}
