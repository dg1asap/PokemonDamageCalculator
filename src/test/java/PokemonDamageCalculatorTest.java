import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PokemonDamageCalculatorTest {

    @Test
    void calculate(String attackType, String[] defTypes) {
        assertEquals(true, true);
        String fireDmgRateAgainstGrass = PokemonDamageRateCalculator.calculate("fire", "grass");
        String fightingDmgRate = PokemonDamageRateCalculator.calculate("fighting", "ice", "rock");
        String psychicDmgRate = PokemonDamageRateCalculator.calculate("psychic", "poison", "dark");
        String waterDmgRate = PokemonDamageRateCalculator.calculate("water", "normal");
        String fireDmgRateAgainstRock = PokemonDamageRateCalculator.calculate("fire", "rock");

        assertEquals(fireDmgRateAgainstGrass, "2x");
        assertEquals(fightingDmgRate,"4x");
        assertEquals(psychicDmgRate,"0x");
        assertEquals(waterDmgRate,"1x");
        assertEquals(fireDmgRateAgainstRock,"0.5x");
    }

}
