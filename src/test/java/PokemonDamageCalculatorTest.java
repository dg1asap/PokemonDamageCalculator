import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PokemonDamageCalculatorTest {

    @Test
    void testCalculateDamageRateAgainstType() {
        PokemonDamageCalculator calculator = new PokemonDamageCalculator();

        String fireDmgRateAgainstGrass = calculator.calculateDamageRateAgainstType("fire", "grass");
        String fightingDmgRate = calculator.calculateDamageRateAgainstType("fighting", "ice", "rock");
        String psychicDmgRate = calculator.calculateDamageRateAgainstType("psychic", "poison", "dark");
        String waterDmgRate = calculator.calculateDamageRateAgainstType("water", "normal");
        String fireDmgRateAgainstRock = calculator.calculateDamageRateAgainstType("fire", "rock");

        assertEquals("2.0x", fireDmgRateAgainstGrass);
        assertEquals("4.0x", fightingDmgRate);
        assertEquals("0.0x", psychicDmgRate);
        assertEquals("1.0x", waterDmgRate);
        assertEquals("0.5x", fireDmgRateAgainstRock);
    }




}
