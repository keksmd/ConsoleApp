package test;

import com.alexkekiy.server.commands.Add;
import com.alexkekiy.server.util.CommandExtractor;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class CommandExtractorTest {
    @Test
    public void test1() throws InvocationTargetException, IllegalAccessException {
        assert CommandExtractor.extractCommand("add").equals(new Add());

    }
}
