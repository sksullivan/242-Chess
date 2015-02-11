package test.com.sks.chess; 

import com.sks.chess.GameLogic.Board;
import com.sks.chess.GameLogic.GamePiece.Pawn;
import com.sks.chess.GameLogic.GamePiece.Rook;
import javafx.util.Pair;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import static org.junit.Assert.*;

public class GenericGamePieceTest {

    @Test
    public void testToString() {
        Rook rook = new Rook(0,0,false,null);
        assertEquals(rook.toString(),"Rook");
    }
}
