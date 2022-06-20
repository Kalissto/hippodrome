import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HippodromeTest {
    @Test
    @DisplayName("1 Проверить, что при передаче в конструктор null, будет выброшено IllegalArgumentException")
    void testExpectedException() {
        assertThrows(IllegalArgumentException.class, () -> {
            Hippodrome hippodrome = new Hippodrome(null);
        });
    }

    @Test
    @DisplayName("2 Проверить, что при передаче в конструктор null, выброшенное исключение будет содержать сообщение Horses cannot be null.")
    void testExpectedException1() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Hippodrome hippodrome = new Hippodrome(null);
        });
        assertEquals("Horses cannot be null.", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает список, который содержит те же объекты и в той же последовательности, " +
            "что и список который был передан в конструктор. " +
            "При создании объекта Hippodrome передай в конструктор список из 30 разных лошадей;")
    void getHorsesTest() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(i, new Horse(String.valueOf(i), i + 0.1));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    @DisplayName("Проверить, что метод вызывает метод move у всех лошадей. При создании объекта Hippodrome передай в конструктор список из 50 моков лошадей и возпользуйся методом verify.")
    void testMove() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Horse horse = Mockito.mock(Horse.class);
            horses.add(i, horse);

        }
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse curHorse : horses) {
            Mockito.verify(curHorse).move();
        }
    }

    @Test
    void getWinner() {
        Horse actual;
        Horse two;
        List<Horse> horses = new ArrayList<>();
        horses.add(new Horse("a", 0, 1));
        horses.add(new Horse("б", 0, 3));
        horses.add(new Horse("в", 0, 5));

        actual = horses.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .get();

        two = horses.get(2);
        assertEquals(two, actual);
    }
}
