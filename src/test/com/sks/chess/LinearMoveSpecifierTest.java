package test.com.sks.chess;

import com.sks.chess.InvalidMoveDirectionCombinationException;
import com.sks.chess.LinearMoveSpecifier;
import com.sun.javafx.scene.traversal.Direction;
import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;

import static org.junit.Assert.*;

public class LinearMoveSpecifierTest {

    Pair<Integer,Integer> geometricOrigin;

    @Before
    public void init() {
        geometricOrigin = new Pair<Integer,Integer>(0,0);
    }

    @Test(expected = InvalidMoveDirectionCombinationException.class)
    public void testInvalidConstructor() throws InvalidMoveDirectionCombinationException {
        LinearMoveSpecifier specifier = new LinearMoveSpecifier(Direction.DOWN,Direction.UP,1);
    }

    @Test
    public void testSequentiallyGenerateMovesFromOriginAdjacent() throws Exception {
        LinearMoveSpecifier specifier = new LinearMoveSpecifier(Direction.DOWN,1);
        Pair<Integer,Integer> destination = specifier.sequentiallyGenerateMovesFromOrigin(geometricOrigin);
        Pair<Integer,Integer> emptyDestination = specifier.sequentiallyGenerateMovesFromOrigin(geometricOrigin);
        assertEquals(new Pair<Integer, Integer>(0,1),destination);
        assertNull(emptyDestination);
    }

    @Test
    public void testSequentiallyGenerateMovesFromOriginN() throws Exception {
        LinearMoveSpecifier specifier = new LinearMoveSpecifier(Direction.RIGHT,Direction.DOWN,-1);
        for (int i = 1; i < 10; i++) {
            Pair<Integer,Integer> intermediateDestination = specifier.sequentiallyGenerateMovesFromOrigin(geometricOrigin);
            assertEquals(new Pair<Integer,Integer>(i,i),intermediateDestination);
        }
    }
}