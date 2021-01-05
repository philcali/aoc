package me.philcali.aoc.day4.year2020;

import com.google.auto.service.AutoService;
import me.philcali.aoc.common.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Year(2020) @Day(4) @Problem(1)
@AutoService(DailyEvent.class)
public class ProblemOne implements DailyInputEvent, AnnotatedDailyEvent {
    private static final List<String> PASSPORT_CODES = Arrays.asList(
            "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid"
    );

    @Override
    public void run() {
        final PassportScannerData.Builder scannerBuilder = PassportScannerData.builder();
        PASSPORT_CODES.forEach(code -> {
            scannerBuilder.putCodes(code, value -> true);
        });
        List<Map<String, String>> passports = scannerBuilder.build().scanBatchFile(readLines());
        System.out.println("The answer is: " + passports.size());
    }
}
