package antiSpamFilterTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import org.junit.Test;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;

public class AntiSpamFilterAutomaticConfigurationTest {

	@Test
	public void testRun() {
		HashMap<String, Integer> rulesMap = new HashMap<String, Integer>();
		rulesMap.put("BAYES_00",0);
		rulesMap.put("FREEMAIL_FROM",1);

		AntiSpamFilterAutomaticConfiguration k = new AntiSpamFilterAutomaticConfiguration();
		try {
			k.run("src/antiSpamFilterTests/spam.log","src/antiSpamFilterTests/ham.log",rulesMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
