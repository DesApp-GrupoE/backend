package architecture;

import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

public class LoggingTest extends BaseArchitectureTest {

    @Test
    void noneClassShouldCallMethodsSystemOutOrSystemErrOrPrintStackTrace() {
        NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS.check(classes);
    }


}
