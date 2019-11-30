# Advent of Code

![AWS CodeBuild Status][2]

I'm a little late to the game, but here's my solutions to the yearly [Advent of Code][1].
I'm purposefully not looking at existing solutions on the net (because that wouldn't
be much fun, right?)

## How to run it

All of the solutions are isolated as their own maven module, atm.
The `aoc-cli` module contains all of the modules with a simple wrapper
to trigger the daily solution and any sub problem if it exists.

```
usage: aoc-cli
    --day <day>           Run a specific 'Advent of Code' problem for a
                          day
 -h,--help                Prints out this help menu.
 -l,--list                List all of the 'Advent of Code' days.
    --problem <problem>   Run a specific 'Advent of Code' sub problem for
                          a day
 -t,--test                Flag to use the test input.
    --year <year>         Run a specific 'Advent of Code' sub problem for
                          a year
```

[1]: https://adventofcode.com/
[2]: https://codebuild.us-east-1.amazonaws.com/badges?uuid=eyJlbmNyeXB0ZWREYXRhIjoiU00rUEVxOVMxTHFwY1dLcHZwU3VyTWNCdWxkanFBUTVWR24vNGZRZEtSSHhwYkxKbm1Ob3BtZ0NtVDJ3RDZoVVdFSGtzdlpvRWpiNTlEQ24zaCtET0dVPSIsIml2UGFyYW1ldGVyU3BlYyI6Iko5YWN6L2Y3dWxpUVAzTDQiLCJtYXRlcmlhbFNldFNlcmlhbCI6MX0%3D&branch=master
