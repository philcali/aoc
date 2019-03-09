package me.philcali.aoc.day3.year2018;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final List<ClaimData> claims;

    public Cell() {
        this.claims = new ArrayList<>();
    }

    public void addClaim(final ClaimData claim) {
        if (!claims.isEmpty()) {
            claim.setOverlaps(true);
            claims.get(0).setOverlaps(true);
        }
        claims.add(0, claim);
    }

    public List<ClaimData> getClaims() {
        return claims;
    }
}
