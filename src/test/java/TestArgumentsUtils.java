/**
 * Created by Alex on 23.12.2018.
 */


import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class TestArgumentsUtils {
    String[] args1 = {"-e"};
    String[] args2 = {"-d"};
    String[] args3 = {"-h"};
    String[] args4 = {"-v"};
    String[] args5 = {"-e", "-p", "/somePath/file.fil", "-c", "/somePath/file.cip", };
    String[] args6 = {"-d", "-p", "/somePath/file.fil", "-c", "/somePath/file.cip", };
    String[] args7 = {"-p", "/somePath/file.fil", "-c", "/somePath/file.cip", };
    String[] args8 = {"-e", "-p", "/somePath/file.fil", "-k", "PASSWORD"};
    String[] args9 = {"-e", "-c", "/somePath/file.cip", "-k", "PASSWORD"};
    String[] argsA = {"-d", "-p", "/somePath/file.fil", "-k", "PASSWORD"};
    String[] argsB = {"-d", "-c", "/somePath/file.cip", "-k", "PASSWORD"};
    String[] argsC = {"-c", "/somePath/file.cip", "-p", "/somePath/file.fil", "-k", "PASSWORD"};
    String[] argsD = {"-d", "-c", "/somePath/file.cip", "-p", "/somePath/file.fil", "-k", "PASSWORD"};


    @Test
    public void testGetOperation() {
        Operation operation = null;
        assertTrue(ArgumentsUtils.getOperation(args1)==Operation.ENCRYPT);
        assertTrue(ArgumentsUtils.getOperation(args2)==Operation.DECRYPT);
        assertTrue(ArgumentsUtils.getOperation(args5)==Operation.ENCRYPT);
        assertTrue(ArgumentsUtils.getOperation(args6)==Operation.DECRYPT);
        assertTrue(ArgumentsUtils.getOperation(args7)==Operation.ENCRYPT);
        assertTrue(ArgumentsUtils.getOperation(args8)==Operation.ENCRYPT);
        assertTrue(ArgumentsUtils.getOperation(args9)==Operation.ENCRYPT);
        assertTrue(ArgumentsUtils.getOperation(argsA)==Operation.DECRYPT);
        assertTrue(ArgumentsUtils.getOperation(argsB)==Operation.DECRYPT);
        assertTrue(ArgumentsUtils.getOperation(argsC)==Operation.ENCRYPT);
        assertTrue(ArgumentsUtils.getOperation(argsD)==Operation.DECRYPT);
    }

    public void testGetPlainPath() {
        boolean result = ArgumentsUtils.getPlainPath(args1) == null;
        assertTrue(result);

        result = ArgumentsUtils.getPlainPath(args2)==null;
        assertTrue(result);

        result = ArgumentsUtils.getPlainPath(args8)==null;
        assertTrue(result);

        result = ArgumentsUtils.getPlainPath(argsA)==null;
        assertTrue(result);

        assertEquals("/somePath/file.cip",ArgumentsUtils.getPlainPath(args5));
        assertEquals("/somePath/file.cip",ArgumentsUtils.getPlainPath(args6));
        assertEquals("/somePath/file.cip",ArgumentsUtils.getPlainPath(args7));
        assertEquals("/somePath/file.cip",ArgumentsUtils.getPlainPath(args9));
        assertEquals("/somePath/file.cip",ArgumentsUtils.getPlainPath(argsB));
        assertEquals("/somePath/file.cip",ArgumentsUtils.getPlainPath(argsC));
        assertEquals("/somePath/file.cip",ArgumentsUtils.getPlainPath(argsD));
    }


    public void testGetCipherPath() {
        boolean result = ArgumentsUtils.getCipherPath(args1) == null;
        assertTrue(result);

        result = ArgumentsUtils.getCipherPath(args2)==null;
        assertTrue(result);

        result = ArgumentsUtils.getCipherPath(args9)==null;
        assertTrue(result);

        result = ArgumentsUtils.getCipherPath(argsB)==null;
        assertTrue(result);

        result = ArgumentsUtils.getCipherPath(args2)==null;
        assertTrue(result);

        assertEquals("/somePath/file.fil",ArgumentsUtils.getCipherPath(args5));
        assertEquals("/somePath/file.fil",ArgumentsUtils.getCipherPath(args6));
        assertEquals("/somePath/file.fil",ArgumentsUtils.getCipherPath(args7));
        assertEquals("/somePath/file.fil",ArgumentsUtils.getCipherPath(args8));
        assertEquals("/somePath/file.fil",ArgumentsUtils.getCipherPath(argsA));
        assertEquals("/somePath/file.fil",ArgumentsUtils.getCipherPath(argsC));
        assertEquals("/somePath/file.fil",ArgumentsUtils.getCipherPath(argsD));
    }

    public void testIsHelpKey() {
        assertFalse(ArgumentsUtils.isHelpKey(args1));
        assertFalse(ArgumentsUtils.isHelpKey(args2));
        assertTrue(ArgumentsUtils.isHelpKey(args3));
        assertFalse(ArgumentsUtils.isHelpKey(args4));
    }



    public void testIsVersionKey() {
        assertFalse(ArgumentsUtils.isVersionKey(args1));
        assertFalse(ArgumentsUtils.isVersionKey(args2));
        assertFalse(ArgumentsUtils.isVersionKey(args3));
        assertTrue(ArgumentsUtils.isVersionKey(args4));
    }




}
