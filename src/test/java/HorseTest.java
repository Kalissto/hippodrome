import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseTest {
    double distance = 0.3;
    double speed = 0.8;

    @Test
    @DisplayName("1 Проверить, что при передаче в конструктор первым параметром null, будет выброшено IllegalArgumentException")
    void testExpectedException() {

        assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(null, 0, 0);
        });
    }

    @Test
    @DisplayName("2 Проверить, что при передаче в конструктор первым параметром null, выброшенное исключение будет содержать сообщение Name cannot be null.")
    void messageException() {

        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(null, 0, 0);
        });

        assertEquals("Name cannot be null.", thrown.getMessage());
    }

    @ParameterizedTest()
    @DisplayName("3 Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.), будет выброшено IllegalArgumentException")
    @ValueSource(strings = {" ", "", "\t", "\n"})
    void endsWithTest(String name) {
        assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(name, 0, 0);
        });
    }

    @ParameterizedTest()
    @DisplayName("4 Проверить, что при передаче в конструктор первым параметром пустой строки или строки содержащей только пробельные символы (пробел, табуляция и т.д.), выброшенное исключение будет содержать сообщение Name cannot be blank.")
    @ValueSource(strings = {" ", "", "\t", "\n"})
    void endsWithTest2(String name) {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse(name, 0, 0);
        });
        assertEquals("Name cannot be blank.", thrown.getMessage());
    }

    @Test
    @DisplayName("5 Проверить, что при передаче в конструктор вторым параметром отрицательного числа, будет выброшено IllegalArgumentException")
    void exeptionOtricat() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse("null", -1, 0);
        });
    }

    @Test
    @DisplayName("6 Проверить, что при передаче в конструктор вторым параметром отрицательного числа, выброшенное исключение будет содержать сообщение Speed cannot be negative.")
    void massageOtricat() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse("null", -1, 0);
        });
        assertEquals("Speed cannot be negative.", thrown.getMessage());
    }

    @Test
    @DisplayName("7 Проверить, что при передаче в конструктор третьим параметром отрицательного числа, будет выброшено IllegalArgumentException")
    void exeptionOtricat2() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse("null", 0, -1);
        });
    }

    @Test
    @DisplayName("8 Проверить, что при передаче в конструктор третьим параметром отрицательного числа, выброшенное исключение будет содержать сообщение Distance cannot be negative.")
    void massageOtricat2() {
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            Horse horse = new Horse("null", 0, -1);
        });
        assertEquals("Distance cannot be negative.", thrown.getMessage());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает строку, которая была передана первым параметром в конструктор")
    void getNameTest() {
        Horse horse = new Horse("a", 0, 0);
        assertEquals("a", horse.getName());
    }


    @Test
    @DisplayName("Проверить, что метод возвращает число, которое было передано вторым параметром в конструктор")
    void getSpeed() {
        Horse horse = new Horse("a", 1, 0);
        assertEquals(1, horse.getSpeed());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает число, которое было передано третьим параметром в конструктор")
    void getDistance() {
        Horse horse = new Horse("a", 0, 1);
        assertEquals(1, horse.getDistance());
    }

    @Test
    @DisplayName("Проверить, что метод возвращает ноль")
    void getDistance1() {
        Horse horse = new Horse("a", 0);
        assertEquals(0, horse.getSpeed());
    }

    @Test
    @DisplayName("Проверить, что метод вызывает внутри метод getRandomDouble с параметрами 0.2 и 0.9.")
    public void testGetRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(0.2);
            Horse horse = new Horse("a", speed, distance);
            horse.move();
            horseMockedStatic.verify(
                    () -> Horse.getRandomDouble(0.2, 0.9)
            );
        }
    }

    @ParameterizedTest
    @DisplayName("Проверить, что метод присваивает дистанции значение высчитанное по формуле: distance + 31 * getRandomDouble(0.2, 0.9)")
    @MethodSource("moveMethod")
    public void testDistance(Double d) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(d);
            Horse horse = new Horse("a", speed, distance);
            horse.move();
            double dist = distance + speed * d;
            assertEquals(dist, horse.getDistance());
        }
    }

    static Stream<Double> moveMethod() {
        return Stream.of(0.1, 20.2, 0.5);
    }
}
