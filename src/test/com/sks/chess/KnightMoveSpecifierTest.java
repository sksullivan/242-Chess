package test.com.sks.chess;

import com.sks.chess.GameLogic.MoveSpecifier.KnightMoveSpecifier;
import javafx.util.Pair;
import org.junit.Before;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class KnightMoveSpecifierTest {
    Pair<Integer,Integer> origin;

    @Before
    public void init() {
        origin = new Pair<Integer, Integer>(5,5);
    }

    @org.junit.Test
     public void testSequentiallyGenerateMovesFromOrigin() throws Exception {
        KnightMoveSpecifier specifier = new KnightMoveSpecifier();
        ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer, Integer>>();
        Pair<Integer,Integer> generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(origin);;

        while (generatedMove != null) {
            generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(origin);
            moves.add(generatedMove);
        }

        assertTrue(moves.contains(new Pair<Integer,Integer>(4,7)));
    }

    @org.junit.Test
    public void testSequentiallyGenerateMovesFromOrigin2() throws Exception {
        KnightMoveSpecifier specifier = new KnightMoveSpecifier();
        ArrayList<Pair<Integer,Integer>> moves = new ArrayList<Pair<Integer, Integer>>();
        Pair<Integer,Integer> generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(origin);;

        while (generatedMove != null) {
            generatedMove = specifier.sequentiallyGenerateMovesFromOrigin(origin);
            moves.add(generatedMove);
        }

        assertTrue(moves.contains(new Pair<Integer,Integer>(6,7)));
    }
}